package com.sezgk.tractor.census;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



public class TractGroupingService {
	

	public static List<CongressionalDistrict> createDistricts(List<CensusTract> tracts) {
		
		List<CongressionalDistrict> districts = new ArrayList<CongressionalDistrict>();
		
		int numDistricts = 8;
		int statePopulation = 0;
		
		int districtPop = 0;
		int districtCounter=0;
		
		for (int i=0; i<tracts.size(); i++)
		{
			statePopulation+=tracts.get(i).getPopulation();
			
		}
		int targetPop = statePopulation/numDistricts;
				
		for (districtCounter=0; districtCounter<numDistricts; districtCounter++)
		{
			districts.add(new CongressionalDistrict());
			while ((districtPop + tracts.get(0).getPopulation()) < targetPop && tracts.size()>0)
			{
				districtPop+=districts.get(districtCounter).addTract(tracts.get(0));
				tracts.remove(0);
				
			}
			//sortDistance(tracts);
			districtPop=0;
		}
		
		return districts;
		
	}

	private static void sortDistance(List<CensusTract> tracts) {
		
	}
	
	
	
	
	

}
