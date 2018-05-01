package gash.router.server.electionRaft;

import gash.router.redis.RedisDatabaseServer;
import gash.router.server.edges.EdgeInfo;
import pipe.work.Work.WorkMessage;

public class Candidate implements ClassNode {

	private boolean voteAsked = false;
	private int voteNum = 1; 
	private int activeNodeNum = 1;
	private RaftHandler raft_handler;
	
	public Candidate(RaftHandler handler) {
		this.raft_handler = handler;
	}
	
	public synchronized RaftHandler getHandler() {
		return this.raft_handler;
	}
		
	@Override
	public synchronized void init() {
		System.out.println("Init . .");
	}

	@Override
	public synchronized void run() {
		
		try {
			if (this.raft_handler.getNodeMode() == 2) {
				
				if (this.raft_handler.getTimeout() <= 0) {
					this.raft_handler.setRandomTimeout();
					this.raft_handler.decreaseTerm();

					System.out.println("Node " + this.raft_handler.getNodeId() + " - " + "Timeout Go back to the Follower !");
					this.raft_handler.setNodeState(this.raft_handler.follower, 1);
					voteAsked = false;
					return;
				}
				
				//update total count for active nodes
				activeNodeNum = 1;
				for (EdgeInfo ei:this.raft_handler.getEdgeMonitor().getOutboundEdges().getMap().values()) {
					if (ei.isActive() && ei.getChannel().isActive()) {
						activeNodeNum++;
					}
				}
				
				//Nobody in the network, voted for itself to become leader
				if (activeNodeNum == 1) {
					this.raft_handler.setLearderId(this.raft_handler.getNodeId());
					this.raft_handler.setRandomTimeout();
					this.raft_handler.increaseTerm();

					System.out.println("Node " + this.raft_handler.getNodeId() + " - " +  "Become The Leader in the term " + this.raft_handler.getTerm());
					voteAsked = false;
					this.raft_handler.setNodeState(this.raft_handler.leader, 3);
					udpateRedis();
					return;
					
				} else {
					// Vote request to all follower
					if (!voteAsked) {
						System.out.println("The candidate node " + this.raft_handler.getNodeId() + " send a request vote to all of followers");
						this.raft_handler.increaseTerm();

						System.out.println("The active nodes = " + activeNodeNum);
						voteNum = 1;
						System.out.println("The candidate node " + this.raft_handler.getNodeId() + " has voted for its own");
						
						for (EdgeInfo ei:this.raft_handler.getEdgeMonitor().getOutboundEdges().getMap().values()) {			
							if (ei.getChannel().isActive() && ei.isActive() ) {	

								ei.getChannel().writeAndFlush(MessageUtil.candidateAskToVote(raft_handler));
								System.out.println("The Candidate has sended a vote request to the node " + ei.getRef());
							}
						}
						voteAsked = true;
					}
				}
			}
			Thread.sleep(300);
			
			this.raft_handler.setTimeout(this.raft_handler.getTimeout() - (int)(System.currentTimeMillis() - this.raft_handler.getTimerStart()));
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public synchronized void candidateRespondVote(WorkMessage wm) {
		
	}

	@Override
	public void followerVote(WorkMessage wm) {

		if (this.raft_handler.getNodeMode() == 2) {
			System.out.println("Node " + this.raft_handler.getNodeId() + " - " + "Get vote from the node "+  wm.getAVote().getVoterID() + " voted for the node");
			voteNum++;
			
			System.out.println("Node " + this.raft_handler.getNodeId() + " - " + "The current voted = " + voteNum + "/" + activeNodeNum + " the active nodes, required " + (1+(activeNodeNum/2)) + " votes to become LEADER");
			// Accepted the votes with more then a half of it good.
			if (voteNum >= (activeNodeNum / 2)) {
				this.raft_handler.setRandomTimeout();

				System.out.println("The Node " + this.raft_handler.getNodeId() + " - " +  " become LEADER in term " + this.raft_handler.getTerm());
				this.raft_handler.setLearderId(this.raft_handler.getNodeId());
				this.raft_handler.setNodeState(this.raft_handler.leader, 3);
				udpateRedis();

				voteAsked = false;
			}
		}
	}

	public synchronized void udpateRedis() {
		//Update redis the leader node
		RedisDatabaseServer.getInstance().getjedis().select(0);
		String host = raft_handler.getHost();

		int commandPort = raft_handler.getServerState().getConf().getCommandPort();
		RedisDatabaseServer.getInstance().getjedis().set(String.valueOf(gash.router.routingConfiguration.RoutingConf.clusterId), host +":" + commandPort);
		System.out.println("- Redis updated -");
	}
	
	@Override
	public synchronized void leaderRespondHeartBeat(WorkMessage wm) {

		if (this.raft_handler.getNodeMode() == 2) {
			this.raft_handler.setRandomTimeout();
			System.out.println("Node " + this.raft_handler.getNodeId() + " - " + "Heartbeat from the Leader: "+ wm.getLeader().getLeaderId());
			
			this.raft_handler.setLastKnownBeat(System.currentTimeMillis());
			this.raft_handler.setLearderId(wm.getLeader().getLeaderId());
			this.raft_handler.setTerm(wm.getLeader().getLeaderTerm());

			this.raft_handler.setNodeState(this.raft_handler.follower, 1);

			voteAsked = false;
		}
	}
}
