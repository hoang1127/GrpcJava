package gash.router.server.raft;

import pipe.work.Work.WorkMessage;

public interface ClassNode {
	
	public void init();
	public void run();

	public void followerVote(WorkMessage wm);
	public void candidateRespondVote(WorkMessage wm);
	public void leaderRespondHeartBeat(WorkMessage wm);
	
}
