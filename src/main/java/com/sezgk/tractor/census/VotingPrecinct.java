package com.sezgk.tractor.census;

public class VotingPrecinct {
	
	private int democrats = 0;
    private int republicans = 0;
    private int independents = 0;
    
    
	public VotingPrecinct(int tractID, int democrats, int republicans, int independents) {
		this.democrats += democrats;
		this.republicans += republicans;
		this.independents += independents;
		// TODO Auto-generated constructor stub
	}

	public int getDemocrats() {

		return democrats;
	}
	
	public int getRepublicans() {
	
		return republicans;
	}
	
	public int getIndependents() {
	
		return independents;
	}
    
    

}
