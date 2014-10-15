package com.sezgk.tractor.census.parser;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.sezgk.tractor.census.MapCoordinate;
import com.sezgk.tractor.census.TractBoundary;

/**
 * Parser for geoid->coordinate pairings from the census tract kml files obtained at census.gov. Specifically goes
 * through such a KML file and extracts as many of these pairings as can be found.
 * 
 * IMPORTANT NOTE: In order for the file to parse correctly, one needs to modify 38.357077 to be 38.35707700000. When I
 * figure out why this is, I will have unlocked all of the secrets of the universe. I suspect it may be a java issue.
 * -EGolaszewski
 * 
 * @author Ennis Golaszewski
 * 
 */
public class TractCoordinateParser
{
    /* Map to store mappings of geoid->coordinates for later retrieval. */
    private Map<Long, List<TractBoundary>> boundaryMap = null;

    /*
     * Because the SAX characters() function will sometimes return XML elements in two parts, we'll need a place to
     * capture the input.
     */
    private StringBuilder charBuffer = new StringBuilder();

    private static final String configErrMsg = "Error encountered during parser initalization.";
    private static final String saxErrMsg = "Error encountered during parsing.";
    private static final String ioErrMsg = "Error encountered while opening or reading kml file.";
    private static final String coordErrF = "Malformed data encounted while evaluating coordinates at line %d, column %d.";

    /**
     * Parses the *.kml file on the given path for geoid->coordinate field pairs. Because it is possible for a tract to
     * be composed of more than one geographic polygon, it can have multiple boundaries associated with it.
     * 
     * @param path, the path of the file.
     * @return a mapping of geoid->coordinates for each found entry.
     * @throws TractCoordinateParserException, if an exception is encountered during any step.
     */
    public Map<Long, List<TractBoundary>> parse(String path) throws TractCoordinateParserException
    {
        try
        {
            boundaryMap = new HashMap<Long, List<TractBoundary>>();

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            DefaultHandler handler = new TractCoordinateHandler();
            parser.parse(path, handler);

            /*
             * The boundary map will be populated by the work of the handler during parsing. See TractCoordinateHandler.
             */
            return boundaryMap;
        }
        catch (ParserConfigurationException e)
        {
            throw new TractCoordinateParserException(configErrMsg, e);
        }
        catch (SAXException e)
        {
            throw new TractCoordinateParserException(saxErrMsg, e);
        }
        catch (IOException e)
        {
            throw new TractCoordinateParserException(ioErrMsg, e);
        }
    }

    /**
     * Implementation of the default handler in order to parse out geoid->coordinate information for census tracts from
     * the appropriate kml file.
     * 
     * @author Ennis Golaszewski
     * 
     */
    private class TractCoordinateHandler extends DefaultHandler
    {
        /*
         * This is a SAX object that allows us to report the location of an error if we run into one.
         */
        private Locator locator = null;

        /*
         * Keeps track of the last ID parsed. This is crucial for matching coordinates found after an ID to their ID in
         * the boundary map.
         */
        private Long lastID = null;

        private boolean coordinates = false;
        private boolean geoid = false;

        @Override
        public void setDocumentLocator(Locator locator)
        {
            this.locator = locator;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
        {
            /*
             * We will use the GEOID field to delimit census tract entries in the kml file. This is relevant where
             * multigeometry is involved, since a single census tract can actually have multiple coordinate entries.
             * These will need to be parsed separately.
             */
            if (qName.equals("SimpleData") && attributes.getValue(0).equals("GEOID"))
            {
                geoid = true;
            }
            else if (qName.equals("coordinates"))
            {
                coordinates = true;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException
        {
            if (geoid)
            {
                geoid = false;
                processIDElement();
            }
            else if (coordinates)
            {
                coordinates = false;
                processCoordinateElement();
            }
        }

        @Override
        public void characters(char ch[], int start, int length) throws SAXException
        {
            if (geoid || coordinates)
            {
                charBuffer.append(ch, start, length);
            }
        }

        /**
         * Pops a coordinate string off of the buffer and converts it into a boundary tract. This tract is then paired
         * to the last read GEOID.
         * 
         * @throws SAXException, if an error is encountered during the conversion.
         */
        private void processCoordinateElement() throws SAXException
        {
            MapCoordinate[] coordinates = parseCoordinates(charBuffer.toString());
            TractBoundary boundary = new TractBoundary(coordinates);
            matchBoundaryToLastID(boundary);
            clearBuffer(charBuffer);
        }

        /**
         * Pops a GEOID string off of the buffer and creates a key in the boundary map based on it.
         * 
         * @throws SAXException, if an error is encountered during the conversion or the insertion into the map.
         */
        private void processIDElement() throws SAXException
        {
            Long id = extractID(charBuffer.toString());
            insertID(id);
            lastID = id;
            clearBuffer(charBuffer);
        }

        /**
         * Clears a string builder by setting its length to zero. This is the idiomatic way to perform this task.
         * 
         * @param b, the string builder (buffer) to clear.
         */
        private void clearBuffer(StringBuilder b)
        {
            b.setLength(0);
        }

        /**
         * Inserts the provided ID into the boundary map. If the ID is a duplicate, an exception will be thrown.
         * 
         * @param geoID, the ID to insert. Should already exist in the map.
         * @throws SAXException, if the ID already exists in the map. Duplicate IDs are not permitted.
         */
        private void insertID(Long geoID) throws SAXException
        {
            if (boundaryMap.containsKey(geoID))
            {
                String msg = String.format("Encountered repeated geoID at line %d.", locator.getLineNumber());
                throw new SAXException(msg);
            }

            boundaryMap.put(geoID, new ArrayList<TractBoundary>());
        }

        /**
         * Matches the input boundary to the last ID that has been read in. This method takes advantage of the fact that
         * we know a census tract's boundary coordinates will always appear after it's geographic ID, but before the
         * next tract's geo ID. If this is ever not the case, we will have a problem.
         * 
         * @param boundary, the tract boundary to insert.
         * @throws SAXException, will be thrown if the last ID is null or the map has no entry for it.
         */
        private void matchBoundaryToLastID(TractBoundary boundary) throws SAXException
        {
            /*
             * There 100% has to be a legit last ID that we can write to in the map.
             */
            if (lastID == null || !boundaryMap.containsKey(lastID))
            {
                // TODO msg;
                throw new SAXException();
            }

            boundaryMap.get(lastID).add(boundary);
        }

        /**
         * Extracts the geographic ID number from its representing string. If the string is empty or does not contain a
         * valid number, an exception will be thrown.
         * 
         * @param idString, the string to extract the id from.
         * @return the extracted geographic id.
         * @throws SAXException, if the input string is not valid for extraction.
         */
        private Long extractID(String idString) throws SAXException
        {
            try
            {
                Long geoID = Long.parseLong(idString);
                return geoID;
            }
            catch (NumberFormatException e)
            {
                String msg = String.format("Could not extract id from string %s at line %d.", idString,
                        locator.getLineNumber());
                throw new SAXException(msg, e);
            }
        }

        /**
         * Parses a string representing a space-delimited list of coordinate tuples. The resulting map coordinate
         * objects are put into an array and returned.
         * 
         * @param coordinates, the string of coordinate tuples.
         * @return an array of map coordinate objects.
         */
        private MapCoordinate[] parseCoordinates(String coordinates)
        {
            List<String> elements = splitCoordinateLine(coordinates);
            MapCoordinate[] coordArray = new MapCoordinate[elements.size()];

            int i = 0;
            for (String s : elements)
            {
                MapCoordinate m = parseSingleCoordinate(s);
                coordArray[i] = m;
                i++;
            }

            return coordArray;
        }

        /**
         * Splits a line of coordinates into individual coordinate elements.
         * 
         * This is currently standing in for string.split() due to some weird issues encountered while splitting huge
         * lines.
         * 
         * @param line, the line to split.
         * @return an array of invidiual coordinate tuple strings.
         */
        private List<String> splitCoordinateLine(String line)
        {
            List<String> elements = new ArrayList<String>();
            StringBuilder buffer = new StringBuilder();

            for (Character c : line.toCharArray())
            {
                if (!Character.isWhitespace(c))
                {
                    buffer.append(c);
                }
                else
                {
                    elements.add(buffer.toString());
                    buffer.setLength(0);
                }
            }

            return elements;
        }

        /**
         * Parses a single latitude and longitude coordinate out of the input coordinate string tuple. The tuple in
         * question is comma delimited.
         * 
         * @param coordinate, the tuple to parse.
         * @return a map coordinate object.
         * @throws TractCoordinateParserException, if malformed data is encountered.
         */
        private MapCoordinate parseSingleCoordinate(String coordinate) throws TractCoordinateParserException
        {
            /*
             * Contains longitude, latitude, and optional altitude. In that order.
             */
            String[] elements = coordinate.split("\\,");

            try
            {
                BigDecimal longitude = new BigDecimal(elements[0]);
                BigDecimal latitude = new BigDecimal(elements[1]);
                return new MapCoordinate(latitude, longitude);
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                String msg = String.format(coordErrF, locator.getLineNumber(), locator.getColumnNumber());
                throw new TractCoordinateParserException(msg, e);
            }
            catch (NumberFormatException e)
            {
                String msg = String.format(coordErrF, locator.getLineNumber(), locator.getColumnNumber());
                throw new TractCoordinateParserException(msg, e);
            }
        }

    }
}
