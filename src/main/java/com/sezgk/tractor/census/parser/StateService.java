package com.sezgk.tractor.census.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import com.sezgk.tractor.census.MapCoordinate;
import com.sezgk.tractor.census.StateData;

/**
 * The <code>StateService</code> class provides static points of entry to access state configurations.
 * 
 * @author Ennis Golaszewski
 */
public class StateService
{
    private static final String dataResourcePath = "/state_data/state_data.txt";
    private static final Map<String, StateData> dataMap;
    
    /* Element positions. */
    private static final int FIPS_CODE = 0;
    private static final int STATE_ABBR = 1;
    private static final int STATE_NM = 2;
    private static final int STATE_LAT = 3;
    private static final int STATE_LONG = 4;
    private static final int NUM_DISTRICTS = 5;

    static
    {
        dataMap = loadData();
    }

    public static StateData getData(String stateAbbr)
    {
        return dataMap.get(stateAbbr.toUpperCase());
    }

    /**
     * Loads the state data from the state data definition resource.
     */
    private static Map<String, StateData> loadData()
    {
        Map<String, StateData> dataMap = new HashMap<String, StateData>();
        BufferedReader r = null;

        try
        {
            InputStream iStream = StateService.class.getResourceAsStream(dataResourcePath);
            r = new BufferedReader(new InputStreamReader(iStream));

            while (r.ready())
            {
                String line = r.readLine();
                String[] elements = line.split(",");
                int fips = Integer.parseInt(elements[FIPS_CODE]);
                int nDistricts = Integer.parseInt(elements[NUM_DISTRICTS]);
                BigDecimal sLat = new BigDecimal(elements[STATE_LAT]);
                BigDecimal sLong = new BigDecimal(elements[STATE_LONG]);
                MapCoordinate seed = new MapCoordinate(sLat, sLong);
                String abbr = elements[STATE_ABBR];
                String name = elements[STATE_NM];
                StateData sData = new StateData(fips, nDistricts, abbr, name, seed);
                dataMap.put(abbr, sData);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (r != null)
            {
                try
                {
                    r.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return dataMap;
    }
}
