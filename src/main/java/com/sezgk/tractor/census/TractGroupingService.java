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
 * 
 */
public class TractGroupingService
{
    /**
     * Takes in a list of census tracts and groups them into congressional districts.
     * 
     * @param tracts, the list of tracts to be grouped.
     * @return a list of congressional districts, together containing all of the tracts.
     */
    public static List<CongressionalDistrict> createDistricts(List<CensusTract> tracts)
    {
        // Define the starting NW corner
        BigDecimal testLat = new BigDecimal(39.721077);
        BigDecimal testLong = new BigDecimal(-79.476661);
        MapCoordinate testCoord = new MapCoordinate(testLat, testLong);

        List<CongressionalDistrict> districts = new ArrayList<CongressionalDistrict>();
        Quicksort sorter = new Quicksort(tracts, testCoord);
        tracts = sorter.getSortedCensusList();

        // Maryland-specific number of districts
        int numDistricts = 8;

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
            tracts = new Quicksort(tracts, tracts.get(0).getPosition()).getSortedCensusList();
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

        /*
        int sum = 0;

        for (int i = 0; i < districts.size(); i++)
        {
            sum += districts.get(i).getDistrictPop();
        }

        System.out.println(sum);
        */
        return districts;
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
