package com.sezgk.tractor.census.parser;

import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.sezgk.tractor.census.TractBoundary;
import com.sezgk.tractor.census.parser.TractCoordinateParser;

/**
 * Test cases for the TractCoordinateParser.
 * 
 * @author Ennis Golaszewski
 */
public class TractCoordinateParserTest
{
    private TractCoordinateParser parser;

    @Before
    public void setUp()
    {
        parser = new TractCoordinateParser();
    }

    @After
    public void tearDown()
    {
        parser = null;
    }

    @Test
    public void parse()
    {
        Map<Long, List<TractBoundary>> boundaryMap = parser.parse("src/test/resources/census_tract_kml_test_data.kml");
        
        assertTrue(boundaryMap.size() == 3);
        assertTrue(boundaryMap.get(24001000800L) != null);
        assertTrue(boundaryMap.get(24510280401L) != null);
        assertTrue(boundaryMap.get(24019970900L) != null);
    }
}
