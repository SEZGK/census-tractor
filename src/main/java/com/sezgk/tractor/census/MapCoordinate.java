package com.sezgk.tractor.census;

import java.math.BigDecimal;

/**
 * Represents a point on a map via the point's latitude and longitude.
 * 
 * NOTE: This is designed as an immutable object! Making it mutable would
 * introduce numerous OOP violations into our code base.
 * 
 * @author Ennis Golaszewski
 */
public class MapCoordinate
{
    /*
     * The choice of BigDecimal here is justified as some of the coordinate
     * values are insanely huge. There is no primitive datatype in Java that
     * seems to be able to hold all of them reliably without causing weird
     * truncation or rounding issues.
     */
    private final BigDecimal latitude;
    private final BigDecimal longitude;

    private static final String toStringF = "(lat = %s, long = %s)";

    /**
     * Creates an immutable map coordinate object.
     * 
     * @param latitude
     * @param longitude
     */
    public MapCoordinate(BigDecimal latitude, BigDecimal longitude)
    {
	this.latitude = latitude;
	this.longitude = longitude;
    }

    @Override
    public String toString()
    {
	return String.format(toStringF, latitude, longitude);
    }

    public BigDecimal getLatitude()
    {
	return latitude;
    }

    public BigDecimal getLongitude()
    {
	return longitude;
    }
}
