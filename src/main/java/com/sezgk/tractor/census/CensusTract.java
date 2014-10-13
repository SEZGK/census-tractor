package com.sezgk.tractor.census;

import java.math.BigDecimal;

/**
 * Represents a census tract consistent with those compiled by census.gov.
 * 
 * @author Ennis Golaszewski
 */
public class CensusTract
{
    private long geoId;
    private long population;
    private MapCoordinate position;

    private static String toStringF = "[geoid = %s,  pop = %s, lat = %s, long = %s]";

    /**
     * Creates a new CensusTract instance.
     * 
     * @param geoId, the fully concatenated geographic code. The format is ssccctttttt where s = state fips code, c =
     *            county fips code, and t = census tract number.
     * @param population, the number of people within the census tract.
     * @param latitude, the latitude coordinate of the census tract center.
     * @param longitude, the longitude coordinate of the census tract center.
     */
    public CensusTract(long geoId, long population, BigDecimal latitude, BigDecimal longitude)
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

    public long getGeoId()
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
}
