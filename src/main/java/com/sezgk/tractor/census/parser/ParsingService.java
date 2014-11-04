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
    private static final String tractPathFormat = "src/main/resources/tract_data/census_tracts_list_%d.txt";
    private static final String coordPathFormat = "src/main/resources/kml_data/cb_2013_%d_tract_500k.kml";

    /**
     * Parses census tracts and their boundaries for the state with the given
     * state code.
     * 
     * @param stateCode, the code for the state to parse.
     * @return a list of census tract objects for that state.
     */
    public static List<CensusTract> parseTracts(int stateCode)
    {
	String tractDataPath = String.format(tractPathFormat, stateCode);
	String coordDataPath = String.format(coordPathFormat, stateCode);

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
     * TODO - see PoliticalParser.
     * 
     * @param stateCode
     * @param tracts
     * @return
     */
    public static List<CensusTract> parsePrecincts(int stateCode, List<CensusTract> tracts)
    {
	PoliticalParser p = new PoliticalParser();
	return p.parsePrecincts(stateCode, tracts);
    }

    /**
     * Appends a census tract's boundary coordinates to it.
     * 
     * @param t, the tract.
     * @param boundaryMap, the map of boundaries. The tract will have its
     *            boundaries in this map. If not, an error has occurred
     *            somewhere.
     */
    private static void appendBoundaries(CensusTract t, Map<String, List<TractBoundary>> boundaryMap)
    {
	String id = t.getGeoId().toString();

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
