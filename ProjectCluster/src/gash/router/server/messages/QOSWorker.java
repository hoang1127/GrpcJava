package gash.router.server.messages;

import gash.router.server.edges.EdgeInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gash.router.server.electionRaft.RaftHandler;
import pipe.common.Common;
import pipe.work.Work;

public class QOSWorker implements Runnable{
    protected static Logger logger = LoggerFactory.getLogger("qosworker");
    protected static QOSWorker instance;
    private boolean forever;
    private QueueInterface queue;

    public QOSWorker() {
        this.forever = true;

        // Using SimpleQueue here;
        this.queue = new SimpleQueue();
        instance = this;
        init();
    }

    public static QOSWorker getInstance() {
        if (instance == null) {
            instance = new QOSWorker();
        }
        return instance;
    }

    public void shutdown() {
        forever = false;
    }

    public void init() {
        Thread cthread = new Thread(this);
        cthread.start();
    }

    @Override
    public void run() {
        while (forever) {
        	if (!queue.isEmpty()) {
        		//do work in queue
            	logger.info("Queue Size: " + queue.size());
        		Session task = queue.dequeue();
        		task.handleMessage();
        		
        	} else {
                //When queue is empty, ask for work stealing work
                //steals work from leader
        		try { Thread.sleep(2000); } catch(Exception e){ }
        		
                RaftHandler raftHandler = RaftHandler.getInstance();
        		int LearderId = raftHandler.getLearderId();
                EdgeInfo ei = raftHandler.getEdgeMonitor().getOutboundEdges().getNode(LearderId);
                if(ei != null && ei.getChannel() != null) {
                    //All edges for work is this node queue is empty
                    Common.Header.Builder hb = Common.Header.newBuilder();
                    hb.setNodeId(raftHandler.getServerState().getConf().getNodeId());
                    hb.setTime(System.currentTimeMillis());

                    Work.WorkState.Builder ws = Work.WorkState.newBuilder();
                    ws.setEnqueued(0);
                    ws.setProcessed(1);

                    Work.WorkMessage.Builder wm = Work.WorkMessage.newBuilder();
                    wm.setHeader(hb);
                    wm.setState(ws);
                    wm.setSecret(1234);
                    ei.getChannel().writeAndFlush(wm.build());               
                }                
        	}
        	try { Thread.sleep(500); } catch(Exception e){ }
        }
    }
    public QueueInterface getQueue() {
        return queue;
    }
}
