package com.sezgk.tractor.census.parser;

import java.util.List;
import java.util.Map;
import com.sezgk.tractor.census.CensusTract;
import com.sezgk.tractor.census.TractBoundary;

/**
 * Provides a top level API for census tract related parsing needs.
 * 
 * @author Ennis Golaszewski
 * 
 */
public class ParsingService
{
    private static final String tractDataPath = "src/main/resources/tract_data/census_tracts_list_24.txt";
    private static final String coordDataPath = "src/main/resources/kml_data/cb_2013_24_tract_500k.kml";

    public List<CensusTract> parseTracts()
    {
        CensusTractParser tractParser = new CensusTractParser();
        List<CensusTract> tracts = tractParser.parse(tractDataPath);

        TractCoordinateParser coordParser = new TractCoordinateParser();
        Map<String, List<TractBoundary>> boundaryMap = coordParser.parse(coordDataPath);

        for (CensusTract t : tracts)
        {
            appendBoundaries(t, boundaryMap);
        }

        return tracts;
    }

    /**
     * Appends a census tract's boundary coordinates to it.
     * 
     * @param t, the tract.
     * @param boundaryMap, the map of boundaries. The tract will have its boundaries in this map. If not, an error has
     *            occurred somewhere.
     */
    public void appendBoundaries(CensusTract t, Map<String, List<TractBoundary>> boundaryMap)
    {
        String id = t.getGeoId().toString();

        /* TODO Beware, some tracts literally have 0 pop. */
        if (boundaryMap.containsKey(id))
        {
            List<TractBoundary> boundaries = boundaryMap.get(id);

            for (TractBoundary b : boundaries)
            {
                t.addBoundary(b);
            }
        }
    }
}
