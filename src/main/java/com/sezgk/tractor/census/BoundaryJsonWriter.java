package com.sezgk.tractor.census;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sezgk.tractor.census.parser.TractCoordinateParser;

/**
 * Test harness for writing boundaries to JSON format.
 * 
 * @author Ennis Golaszewski
 */
public class BoundaryJsonWriter
{
    public static void main(String[] args)
    {
        TractCoordinateParser parser = new TractCoordinateParser();
        Map<Long, List<TractBoundary>> boundaryMap = parser
                .parse("src/main/resources/kml_data/cb_2013_24_tract_500k.kml");

        Gson gson = new GsonBuilder().create();

        List<TractBoundary> allBoundaries = new ArrayList<TractBoundary>();

        for (List<TractBoundary> boundaries : boundaryMap.values())
        {
            for (TractBoundary boundary : boundaries)
            {
                allBoundaries.add(boundary);
            }
        }

        String json = gson.toJson(allBoundaries);
        String output = String.format("var data = %s;", json);

        try
        {
            FileOutputStream outputStream = new FileOutputStream("Output.js");
            OutputStreamWriter outWriter = new OutputStreamWriter(outputStream);
            outWriter.append(output);
            outWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
