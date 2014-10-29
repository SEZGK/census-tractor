package com.sezgk.tractor.census;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a census tract consistent with those compiled by census.gov.
 * 
 * @author Ennis Golaszewski
 */
public class CensusTract
{
    private GeoID geoId;
    private long population;
    private MapCoordinate position;
    private int democrats = 0;
    private int republicans = 0;
    private int independents = 0;

    /* Some tracts have multiple boundaries.. I do not know why. -EGolaszewski */
    private List<TractBoundary> boundaries = new ArrayList<TractBoundary>();

    private static String toStringF = "[geoid = %s,  pop = %s, lat = %s, long = %s]";

    /**
     * Creates a new CensusTract instance.
     * 
     * @param geoId, the fully concatenated geographic code. 
     * @param population, the number of people within the census tract.
     * @param latitude, the latitude coordinate of the census tract center.
     * @param longitude, the longitude coordinate of the census tract center.
     */
    public CensusTract(GeoID geoId, long population, BigDecimal latitude, BigDecimal longitude)
    {
        this.geoId = geoId;
        this.population = population;
        this.position = new MapCoordinate(latitude, longitude);
    }

    @Override
    public String toString()
    {
        return String.format(toStringF, geoId, population, position.getLatitude(), position.getLongitude());
    }

    public void addBoundary(TractBoundary b)
    {
        boundaries.add(b);
    }

    /**
     * Returns a view of the boundaries attached to this census tract.
     * 
     * @return an unmodifiable list of boundaries.
     */
    public List<TractBoundary> getBoundaries()
    {
        return Collections.unmodifiableList(boundaries);
    }

    public GeoID getGeoId()
    {
        return geoId;
    }

    public long getPopulation()
    {
        return population;
    }

    public MapCoordinate getPosition()
    {
        return position;
    }
    
    public void addPrecinct (VotingPrecinct v)
    {
    	democrats += v.getDemocrats();
    	republicans += v.getRepublicans();
    	independents += v.getIndependents();
    }
}
