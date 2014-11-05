package com.sezgk.tractor.census;

public class VotingPrecinct
{

    private int democrats = 0;
    private int republicans = 0;
    private int independents = 0;
    private GeoID tractID;

    public VotingPrecinct(GeoID tractID, int democrats, int republicans, int independents)
    {
        this.democrats += democrats;
        this.republicans += republicans;
        this.independents += independents;
        this.tractID = tractID;
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

    public GeoID getTractID()
    {

        return tractID;
    }

}
