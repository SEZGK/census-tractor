package com.sezgk.tractor.census.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.sezgk.tractor.census.CensusTract;
import com.sezgk.tractor.census.GeoID;

/**
 * Reader for the tab-delimeted gazetteer census tract files obtained from census.gov.
 * 
 * @author Ennis Golaszewski
 */
public class CensusTractParser
{
    private String currentLine = "";
    private String delimeter = "\t";
    private BufferedReader bReader = null;

    private static final String notFoundErrorF = "File %s could not be found.";
    private static final String badFileErrorF = "File %s could not be read.";
    private static final String badLineErrorF = "Encountered malformed data line %d.";
    private static final String badFieldErrorF = "Encountered malformed field in line %d.";
    private static final String badIdErrorF = "Could not parse geographic ID out of input %s at line %d.";

    /* Provides the indices for the elements in each row of the file */
    private static int geoIdIndex = 1;
    private static int popIndex = 2;
    private static int latIndex = 8;
    private static int longIndex = 9;

    /* Provides indices for geoID components. */
    private static int stateCodeIndex = 0;
    private static int countyCodeIndex = 2;
    private static int tractNumIndex = 5;
    private static int tractNumEndIndex = 11;

    /**
     * Parses the census tract file at the provided path.
     * 
     * @param path, the path of the file to parse. Must not be null.
     * @return a list of census tracts parsed out of the file.
     * @throws CensusTractParserException, thrown if there is an exception encountered during parsing.
     */
    public List<CensusTract> parse(String path) throws CensusTractParserException
    {
        if (path == null)
        {
            throw new IllegalArgumentException("The input path cannot be null.");
        }

        List<CensusTract> tracts;

        try
        {
            tracts = doParse(path);
        }
        catch (FileNotFoundException e)
        {
            String msg = String.format(notFoundErrorF, path);
            throw new CensusTractParserException(msg, e);
        }
        catch (IOException e)
        {
            String msg = String.format(badFileErrorF, path);
            throw new CensusTractParserException(msg, e);
        }
        finally
        {
            if (bReader != null)
            {
                closeReader();
                bReader = null;
            }
        }

        return tracts;
    }

    /**
     * Does the actual parsing work while sending up any errors encounterd.
     * 
     * @param path, the path of the file to parse.
     * @return tracts, a list of census tracts parsed out of the file.
     * @throws FileNotFoundException, thrown if the file is not found.
     * @throws IOException, thrown if the file cannot be read properly.
     */
    private List<CensusTract> doParse(String path) throws FileNotFoundException, IOException
    {
        List<CensusTract> tracts = new ArrayList<CensusTract>();
        bReader = new BufferedReader(new FileReader(path));
        int lineNum = 0;

        while (bReader.ready())
        {
            currentLine = bReader.readLine();
            lineNum++;
            String[] elements = currentLine.split(delimeter);
            CensusTract newTract = parseTract(elements, lineNum);
            tracts.add(newTract);
        }

        return tracts;
    }

    /**
     * Parses a census tract object out of the pieces obtained by splitting a line of the input file.
     * 
     * @param elements, the components of an input file line.
     * @return a census tract built out of those components.
     * @throws CensusTractParserException, if there is an error encountered parsing a line.
     */
    private CensusTract parseTract(String[] elements, int lineNum) throws CensusTractParserException
    {
        try
        {
            /*
             * For some confounding reason, the census tract data splits the first 5 and the last 8 digits of a geoID by
             * putting a tab in the middle. We need to capture each half independently..
             */
            GeoID id = parseGeoID(elements[geoIdIndex].trim(), lineNum);
            long population = Long.parseLong(elements[popIndex].trim());
            BigDecimal latitude = new BigDecimal(elements[latIndex].trim());
            BigDecimal longitude = new BigDecimal(elements[longIndex].trim());
            CensusTract newTract = new CensusTract(id, population, latitude, longitude);
            return newTract;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            String msg = String.format(badLineErrorF, lineNum);
            throw new CensusTractParserException(msg, e);
        }
        catch (NumberFormatException e)
        {
            String msg = String.format(badFieldErrorF, lineNum);
            throw new CensusTractParserException(msg, e);
        }
    }

    /**
     * Parses a GeoID object out of the fully concatenated geographic ID for a given tract.
     * 
     * @param geoIdField, the string field containing the ID string.
     * @param lineNum, used for identifying the line if an error occurs.
     * @return a GeoID object representing the string field.
     */
    private GeoID parseGeoID(String geoIdField, int lineNum)
    {
        try
        {
            String stateCode = geoIdField.substring(stateCodeIndex, countyCodeIndex);
            String countyCode = geoIdField.substring(countyCodeIndex, tractNumIndex);
            String tractNumber = geoIdField.substring(tractNumIndex, tractNumEndIndex);
            return new GeoID(Integer.parseInt(stateCode), Integer.parseInt(countyCode), Integer.parseInt(tractNumber));
        }
        /* Possible exceptions: NumberFormat and IndexOutOfBounds. We want to treat them the same. */
        catch (Exception e)
        {
            String msg = String.format(badIdErrorF, geoIdField, lineNum);
            throw new CensusTractParserException(msg, e);
        }
    }

    /**
     * Closes the buffered reader if it is open. If the bReader is set to null, this will do nothing.
     */
    private void closeReader()
    {
        try
        {
            if (bReader != null)
            {
                bReader.close();
            }
        }
        /* This only happens if we've already closed the reader and we call this function again.. */
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
