package com.sezgk.tractor.census;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a collection of map coordinate points that compose a geometric boundary for a census tract.
 * 
 * NOTE: This is designed as an immutable object! Making it mutable would introduce numerous OOP violations into our
 * code base.
 * 
 * @author Ennis Golaszewski
 */
public class TractBoundary
{
    private List<MapCoordinate> coordinates = null;

    /**
     * Creates a new immutable tract boundary object.
     * 
     * @param input, a list of coordinates that compose the boundary.
     */
    public TractBoundary(MapCoordinate[] input)
    {
        coordinates = new ArrayList<MapCoordinate>();

        for (MapCoordinate m : input)
        {
            coordinates.add(m);
        }
    }

    @Override
    public String toString()
    {
        return Arrays.toString(coordinates.toArray());
    }

    /**
     * Returns an immutable view of the coordinates for this boundary.
     * 
     * @return immutable view of boundary cordinates.
     */
    public List<MapCoordinate> getCoordinates()
    {
        return Collections.unmodifiableList(coordinates);
    }
}
