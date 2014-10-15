package com.sezgk.tractor.census.parser;

import static org.junit.Assert.assertTrue;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.sezgk.tractor.census.CensusTract;
import com.sezgk.tractor.census.parser.CensusTractParser;

/**
 * Test cases for the CensusTractParser.
 * 
 * @author Ennis Golaszewski
 */
public class CensusTractParserTest
{
    private CensusTractParser parser;

    @Before
    public void setUp()
    {
        parser = new CensusTractParser();
    }

    @After
    public void tearDown()
    {
        parser = null;
    }

    @Test
    public void parse()
    {
        List<CensusTract> tracts = parser.parse("src/test/resources/census_tract_parser_test_data.txt");
        assertTrue(tracts.size() == 10);
        assertTract(tracts.get(0), "24001000100", 3718, "39.6365143", "-78.5094487");
        assertTract(tracts.get(1), "24001000200", 4564, "39.6123087", "-78.7031084");
        assertTract(tracts.get(2), "24001000300", 2780, "39.7083649", "-78.7434027");
        assertTract(tracts.get(3), "24001000400", 3022, "39.684418", "-78.7270888");
        assertTract(tracts.get(4), "24001000500", 2734, "39.6562943", "-78.7293926");
        assertTract(tracts.get(5), "24001000600", 2965, "39.6409546", "-78.7398353");
        assertTract(tracts.get(6), "24001000700", 3387, "39.6329587", "-78.7526552");
        assertTract(tracts.get(7), "24001000800", 2213, "39.6288831", "-78.7630037");
        assertTract(tracts.get(8), "24001001000", 2547, "39.6576457", "-78.7684363");
        assertTract(tracts.get(9), "24001001100", 1493, "39.6510064", "-78.7722271");
    }

    /**
     * Asserts if the tract's attributes match the input attributes.
     * 
     * @param tract, the tract to assert against.
     * @param geoid, the expected tract geoid.
     * @param population, the expected tract population.
     * @param latitude, the expected latitude.
     * @param longitude, the expected longitude.
     */
    public void assertTract(CensusTract tract, String geoid, long population, String latitude, String longitude)
    {
        assertTrue(tract.getGeoId().toString().equals(geoid));
        assertTrue(tract.getPopulation() == population);
        assertTrue(tract.getPosition().getLatitude().compareTo(new BigDecimal(latitude)) == 0);
        assertTrue(tract.getPosition().getLongitude().compareTo(new BigDecimal(longitude)) == 0);
    }
}
