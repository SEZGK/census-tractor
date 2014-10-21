package com.sezgk.tractor.census;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.sezgk.tractor.util.Quicksort;



public class TractGroupingService {
	

	public static List<CongressionalDistrict> createDistricts(List<CensusTract> tracts) {
		
		
		//Define the starting NW corner
		BigDecimal testLat = new BigDecimal(39.721077);
		BigDecimal testLong = new BigDecimal(-79.476661);
		MapCoordinate testCoord = new MapCoordinate(testLat, testLong);
		
		List<CongressionalDistrict> districts = new ArrayList<CongressionalDistrict>();
		Quicksort sorter = new Quicksort(tracts, testCoord);
		tracts=sorter.getSortedCensusList();
		
		//Maryland-specific number of districts
		int numDistricts = 8;
		
		int statePopulation = 0;
		int districtPop = 0;
		int districtCounter=0;
		
		for (int i=0; i<tracts.size(); i++)
		{
			statePopulation+=tracts.get(i).getPopulation();
			
		}
		
		System.out.println(statePopulation);
		
		//Target population for each district
		int targetPop = statePopulation/numDistricts;
				
		//Loop for each district: 
		//- add a district
		//- add tracts until the target population is reached. As each tract is added to a district,
		//		it is removed from the list of tracts
		//- sort the list by distance from the first tract in the new district
		for (districtCounter=0; districtCounter<numDistricts; districtCounter++)
		{
			districts.add(new CongressionalDistrict());
			while ((districtPop + tracts.get(0).getPopulation()) < targetPop && tracts.size()>0)
			{
				districtPop+=districts.get(districtCounter).addTract(tracts.get(0));
				tracts.remove(0);
				
			}
			tracts=sorter.getSortedCensusList();
			districtPop=0;
		}
		
		//Catch any missed tracts and add to the last district
		System.out.println(tracts.size());
		while (tracts.size()>0)
		{
			districtPop+=districts.get(numDistricts-1).addTract(tracts.get(0));
			tracts.remove(0);
		}
		
		int sum=0;
		for (int i=0; i<districts.size(); i++)
		{
			sum+=districts.get(i).getDistrictPop();
		}
		System.out.println(sum);
		return districts;
		
	}

}
