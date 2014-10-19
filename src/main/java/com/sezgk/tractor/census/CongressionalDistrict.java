package com.sezgk.tractor.census;

import java.util.ArrayList;
import java.util.List;

public class CongressionalDistrict {
	
	
	
	private List<CensusTract> censusTracts;
	
	
	public CongressionalDistrict() {
		
		censusTracts = new ArrayList<CensusTract>();
		
	}
	
	
	public void addTract(CensusTract tract) {
	
		censusTracts.add(tract);
		
	}
	
	
	

}
