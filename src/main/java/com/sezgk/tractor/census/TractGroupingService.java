package com.sezgk.tractor.census;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.sezgk.tractor.util.Quicksort;

/**
 * Implementation of the tract grouping algorithm. This algorithm takes census tracts and groups them into congressional
 * districts.
 * 
 * @author Gary Thompson
 * @author Ennis Golaszewski - variable number of district support.
 */
public class TractGroupingService
{
    /**
     * Takes in a list of census tracts and groups them into congressional districts.
     * 
     * @param tracts , the list of tracts to be grouped.
     * @param nDistricts , the number of districts to form.
     * @param seedCoordinate, the coordinate origin from which districts should begin being built.
     * @return a list of congressional districts, together containing all of the tracts.
     */
    public static List<CongressionalDistrict> createDistricts(List<CensusTract> tracts, int numDistricts, MapCoordinate seedCoordinate)
    {
        List<CongressionalDistrict> districts = new ArrayList<CongressionalDistrict>();
        Quicksort sorter = new Quicksort(tracts, seedCoordinate);
        tracts = sorter.getSortedCensusList();

        int statePopulation = calculateStatePopulation(tracts);
        int districtPop = 0;
        int districtCounter = 0;

        // Target population for each district
        int targetPop = statePopulation / numDistricts;

        // Loop for each district:
        // - add a district
        // - add tracts until the target population is reached. As each tract is added to a district,
        // it is removed from the list of tracts
        // - sort the list by distance from the first tract in the new district
        for (districtCounter = 0; districtCounter < numDistricts; districtCounter++)
        {
            districts.add(new CongressionalDistrict());
            while ((districtPop + tracts.get(0).getPopulation()) < targetPop && tracts.size() > 0)
            {
                CensusTract nextTract = tracts.get(0);
                districts.get(districtCounter).addTract(nextTract);
                districtPop += nextTract.getPopulation();
                tracts.remove(0);

            }
            tracts = new Quicksort(tracts, tracts.get(tracts.size()-1).getPosition()).getSortedCensusList();
            districtPop = 0;
        }

        // Catch any missed tracts and add to the last district
        while (tracts.size() > 0)
        {
            CensusTract missedTract = tracts.get(0);
            districtPop += missedTract.getPopulation();
            districts.get(numDistricts - 1).addTract(tracts.get(0));
            tracts.remove(0);
        }

        /*System.out.println("Starting to fix");
        districts = fix(districts);
        for (int i=0; i<districts.size(); i++)
        {
        	System.out.println(districts.get(i).getDistrictPop());
        }*/
        return districts;
    }

    private static List<CongressionalDistrict> fix(List<CongressionalDistrict> districts) 
    {
    	List<MapCoordinate> centers = new ArrayList<MapCoordinate>();
    	List<CongressionalDistrict> newDistricts = new ArrayList<CongressionalDistrict>();
    	
    	for (int i=0; i<districts.size(); i++)
    	{
    		centers.add(districts.get(i).getCenter());
    		newDistricts.add(new CongressionalDistrict());
    	}
    	
    	for (int j=0; j<districts.size(); j++)
    	{
    		for (int k=0; k<districts.get(j).getSize(); k++)
    		{
    			newDistricts.get(nearest(districts.get(j).getCensusTracts().get(k), centers)).addTract(districts.get(j).getCensusTracts().get(k));
    		}
    	}
    	
    	return newDistricts;
    }

	private static int nearest(CensusTract censusTract, List<MapCoordinate> centers) {
		
		BigDecimal currentMin = censusTract.getPosition().getDistance(centers.get(0));
		int districtCounter = 0;
		
		for (int i=0; i<centers.size(); i++)
		{
			if (censusTract.getPosition().getDistance(centers.get(districtCounter)).compareTo(censusTract.getPosition().getDistance(centers.get(i))) > 0)
			{
				currentMin = censusTract.getPosition().getDistance(centers.get(i));
				districtCounter = i;
			}
		}
		
		return districtCounter;
	}

	/**
     * Iterates through a list of tracts and sums up their population.
     * 
     * @param tracts, the list of tracts to iterate.
     * @return the sum of the populations in the tracts. Represents state population.
     */
    private static int calculateStatePopulation(List<CensusTract> tracts)
    {
        int population = 0;

        for (CensusTract t : tracts)
        {
            population += t.getPopulation();
        }

        return population;
    }

}
