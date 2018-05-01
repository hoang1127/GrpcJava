package gash.router.server.electionRaft;

import gash.router.server.edges.EdgeInfo;
import pipe.work.Work.WorkMessage;

public class Leader implements ClassNode {

	private RaftHandler handler;
	
	public Leader(RaftHandler handler) {
		this.handler = handler;
	}
	
	public synchronized RaftHandler getHandler() {
		return this.handler;
	}

	@Override
	public synchronized void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void run() {
		
		System.out.println("The Node " + this.handler.getNodeId() + " - " + "Leader Mode, term = " + this.handler.getTerm());
		try {
			if (this.handler.getNodeMode() == 3) {
		 		for (EdgeInfo ei:this.handler.getEdgeMonitor().getOutboundEdges().getMap().values()) {
		 			if (ei.isActive() && ei.getChannel().isActive()) {							
						//create hearbeat and send to follower
						ei.getChannel().writeAndFlush(MessageUtil.leaderSendHeartbeat(this.handler));
						System.out.println("Sending heartbeat to node "+ ei.getRef());
					}
				}
		 		//send heartbeat to all followers in cycle dt = 3sec
			}
	 		Thread.sleep(this.handler.getDt());
	 		return;
		 } catch(Exception e) {
			 e.printStackTrace();
		 }
	}

	@Override
	public synchronized void followerVote(WorkMessage wm) {
		// TODO Auto-generated method stub
	}

	@Override
	public synchronized void candidateRespondVote(WorkMessage wm) {
		// TODO Auto-generated method stub
	}

	@Override
	public synchronized void leaderRespondHeartBeat(WorkMessage wm) {
		// TODO Auto-generated method stub
	}
}
