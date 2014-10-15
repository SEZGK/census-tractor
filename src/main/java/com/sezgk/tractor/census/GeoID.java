package com.sezgk.tractor.census;

/**
 * Immutable, fully concatenated geographic ID for a census tract. It is composed of three parts with the following
 * structure:
 * 
 * [ss - ccc - tttttt] where s = state FIPS code, c = county FIPS code, and t = census tract number.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class GeoID
{
    private int stateCode;
    private int countyCode;
    private int tractNumber;

    private static final String toStringF = "%02d%03d%06d";

    /**
     * Creates a new, immutable GeoID object. This represents the fully concatenated geographic ID of a census tract.
     * 
     * @param stateCode, the state code component.
     * @param countyCode, the county code component.
     * @param tractNumber, the tract number.
     */
    public GeoID(int stateCode, int countyCode, int tractNumber)
    {
        this.stateCode = stateCode;
        this.countyCode = countyCode;
        this.tractNumber = tractNumber;
    }

    @Override
    public String toString()
    {
        return String.format(toStringF, stateCode, countyCode, tractNumber);
    }

    public int getStateCode()
    {
        return stateCode;
    }

    public int getCountyCode()
    {
        return countyCode;
    }

    public int getTractNumber()
    {
        return tractNumber;
    }

}
