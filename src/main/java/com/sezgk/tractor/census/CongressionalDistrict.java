package com.sezgk.tractor.census;

import java.util.ArrayList;
import java.util.List;

public class CongressionalDistrict {
	
	
	
	private List<CensusTract> censusTracts;
	private int districtPopulation = 0;
	
	public CongressionalDistrict() {
		
		censusTracts = new ArrayList<CensusTract>();
		
	}
	
	
	public int addTract(CensusTract tract) {
	
		censusTracts.add(tract);
		districtPopulation+=tract.getPopulation();
		return (int) tract.getPopulation();
	}
	
	public int getDistrictPop() {
		return districtPopulation;
	}
	
	public int getSize() {
		return censusTracts.size();
	}

}
