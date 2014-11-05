package com.sezgk.tractor.census;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.math.MathContext;

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
    private MapCoordinate center = new MapCoordinate(BigDecimal.ZERO, BigDecimal.ZERO);

    /**
     * Creates a new congressional district object.
     */
    public CongressionalDistrict()
    {
        censusTracts = new ArrayList<CensusTract>();
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
        center = new MapCoordinate(center.getLatitude().multiply(BigDecimal.valueOf(censusTracts.size() - 1))
                .add(tract.getPosition().getLatitude())
                .divide(BigDecimal.valueOf(censusTracts.size()), new MathContext(12, RoundingMode.HALF_DOWN))
                .setScale(12, RoundingMode.HALF_DOWN), center.getLongitude()
                .multiply(BigDecimal.valueOf(censusTracts.size() - 1)).add(tract.getPosition().getLongitude())
                .divide(BigDecimal.valueOf(censusTracts.size()), new MathContext(12, RoundingMode.HALF_DOWN))
                .setScale(12, RoundingMode.HALF_DOWN));
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

    public MapCoordinate getCenter()
    {
        return center;
    }
}
