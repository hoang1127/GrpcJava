package gash.router.client;

import io.netty.channel.Channel;
import pipe.work.Work.WorkMessage;


public class WorkWorker extends Thread{
    private WorkConnection connection;
    private boolean isRunning = true;

    public WorkWorker(WorkConnection _connection) {
        this.connection = _connection;

        if (connection.outbound == null)
            throw new RuntimeException("connection worker detected null queue");
    }

    @Override
    public void run() {
        System.out.println("--> starting WorkWorker thread");
        System.out.flush();

        Channel ch = connection.connect();
        if (ch == null || !ch.isOpen() || !ch.isActive()) {
            WorkConnection.logger.error("connection missing, no outbound communication");
            return;
        }

        while (true) {
            if (!isRunning && connection.outbound.size() == 0)
                break;

            try {
                // Set block as long as the message is not enqueued and the channel is not active
                WorkMessage msg = connection.outbound.take();
                System.out.println("--> Channel: WorkWorker is going to write message. ");
                if (ch.isWritable()) {
                    if (!connection.write(msg)) {
                        connection.outbound.putFirst(msg);
                    }

                    System.out.flush();
                } else {
                    System.out.println("--> channel not writable- tossing out msg!");

                }

                System.out.flush();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                break;
            } catch (Exception e) {
                WorkConnection.logger.error("Unexpected communcation is failed", e);
                break;
            }
        }

        if (!isRunning) {
            WorkConnection.logger.info("connection queue is closing");
        }
    }
}
