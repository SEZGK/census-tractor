package com.sezgk.tractor.census.parser;

import java.util.List;
import com.sezgk.tractor.census.CensusTract;

/**
 * Provides a top level API for census tract related parsing needs.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class ParsingService
{
    private static final String tractDataPath = "src/main/resources/tract_data/national_census_tracts_2013.txt";

    public List<CensusTract> parseTracts(int stateCode)
    {
        CensusTractParser tractParser = new CensusTractParser();
        List<CensusTract> tracts = tractParser.parse(tractDataPath);

        for (CensusTract t : tracts)
        {
            System.out.println(t);
        }

        return null;
    }

    public static void main(String[] args)
    {
        ParsingService p = new ParsingService();
        p.parseTracts(0);
    }
}
