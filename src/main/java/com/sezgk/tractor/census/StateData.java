package com.sezgk.tractor.census;

/**
 * Represents the data for a single state in the United States.
 * 
 * @author Ennis Golaszewski
 */
public class StateData
{
    private int stateFIPS;
    private int numDistricts;
    private String stateAbbr;
    private String stateName;
    private MapCoordinate seedCoordinate;

    public StateData(int stateFIPS, int numDistricts, String stateAbbr, String stateName, MapCoordinate seedCoordinate)
    {
        this.stateFIPS = stateFIPS;
        this.numDistricts = numDistricts;
        this.stateAbbr = stateAbbr;
        this.stateName = stateName;
        this.seedCoordinate = seedCoordinate;
    }

    public int getStateFIPS()
    {
        return stateFIPS;
    }

    public int getNumDistricts()
    {
        return numDistricts;
    }

    public String getStateAbbr()
    {
        return stateAbbr;
    }

    public String getStateName()
    {
        return stateName;
    }

    public MapCoordinate getSeedCoordinate()
    {
        return seedCoordinate;
    }
}
