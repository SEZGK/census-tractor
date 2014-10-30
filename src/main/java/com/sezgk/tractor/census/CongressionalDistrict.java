package com.sezgk.tractor.census;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a congressional district of a state.
 * 
 * @author SEZGK team
 */
public class CongressionalDistrict
{
    private List<CensusTract> censusTracts;
    private int districtPopulation = 0;
    private int democrats = 0;
    private int republicans = 0;
    private int independents = 0;
    private List<MapCoordinate> districtBoundary;

    /**
     * Creates a new congressional district object.
     */
    public CongressionalDistrict()
    {
	censusTracts = new ArrayList<CensusTract>();
	districtBoundary = new ArrayList<MapCoordinate>();
    }

    /**
     * Adds a census tract to this congressional district.
     * 
     * @param tract, the census tract to add.
     * @return the population of the tract being added.
     */
    public void addTract(CensusTract tract)
    {

	censusTracts.add(tract);
	districtPopulation += tract.getPopulation();
	democrats += tract.getDemocrats();
	republicans += tract.getRepublicans();
	independents += tract.getIndependents();
	
	for (int i=0; i<tract.getBoundaries().size(); i++)
	{
		for (int j=0; j<tract.getBoundaries().get(i).getCoordinates().size(); j++)
		{
			if (districtBoundary.contains(tract.getBoundaries().get(i).getCoordinates().get(j)) == false)
			{
				districtBoundary.add(tract.getBoundaries().get(i).getCoordinates().get(j));
			}
		}
		
	}
	
    }

    public List<CensusTract> getCensusTracts()
    {
	return censusTracts;
    }

    public int getDistrictPop()
    {
	return districtPopulation;
    }

    public int getSize()
    {
	return censusTracts.size();
    }
    
    public int getDemocrats()
    {
    	return democrats;
    }
    
    public int getRepublicans()
    {
    	return republicans;
    }
    
    public int getIndependents()
    {
    	return independents;
    }
    
    public int getBoundaryCount()
    {
    	return districtBoundary.size();
    }
    
}
