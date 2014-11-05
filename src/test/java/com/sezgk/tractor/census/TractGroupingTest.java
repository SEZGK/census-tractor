package com.sezgk.tractor.census;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import com.sezgk.tractor.census.parser.CensusTractParser;

public class TractGroupingTest
{

    @Test
    public void districtsTest()
    {

        CensusTractParser parser = new CensusTractParser();

        List<CongressionalDistrict> districts = new ArrayList<CongressionalDistrict>();
        List<CensusTract> tracts = new ArrayList<CensusTract>();
        tracts = parser.parse("src/main/resources/tract_data/census_tracts_list_24.txt");

        districts = TractGroupingService.createDistricts(tracts, 8);

        for (int i = 0; i < districts.size(); i++)
        {
            System.out.println(districts.get(i).getDistrictPop());
            System.out.println(districts.get(i).getSize());
        }
    }

}
