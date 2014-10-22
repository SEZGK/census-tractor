package com.sezgk.tractor.census;

import java.math.BigDecimal;

/**
 * Represents a point on a map via the point's latitude and longitude.
 * 
 * NOTE: This is designed as an immutable object! Making it mutable would introduce numerous OOP violations into our
 * code base.
 * 
 * @author Ennis Golaszewski
 * @author Scott Gillis
 */
public class MapCoordinate
{
    /*
     * The choice of BigDecimal here is justified as some of the coordinate values are insanely huge. There is no
     * primitive datatype in Java that seems to be able to hold all of them reliably without causing weird truncation or
     * rounding issues.
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

    @Override
    public boolean equals(Object o)
    {
        if (o == null || !(o instanceof MapCoordinate))
        {
            return false;
        }

        MapCoordinate other = (MapCoordinate) o;

        if (latitude.compareTo(other.latitude) != 0 || longitude.compareTo(other.longitude) != 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public BigDecimal getLatitude()
    {
        return latitude;
    }

    public BigDecimal getLongitude()
    {
        return longitude;
    }

    /**
     * Returns the square root of the input BigDecimal value. This is done by utilizing doubleValue() of BigDecimal.
     * 
     * @author Scott Gillis
     * @param value, the BigDecimal value to take the square root of.
     * @return the square root of the value.
     */
    private static BigDecimal sqrt(BigDecimal value)
    {

        BigDecimal x = new BigDecimal(Math.sqrt(value.doubleValue()));
        return x.add(new BigDecimal(value.subtract(x.multiply(x)).doubleValue() / (x.doubleValue() * 2.0)));
    }

    /**
     * Returns the distance from this MapCoordinate point to the input coordinate. This distance is calculated using the
     * distance formula.
     * 
     * @author Scott Gillis
     * @param compareCoord, the coordinate to get the distance to.
     * @return the distance from this cooordinate to the compare coordinate.
     */
    public BigDecimal getDistance(MapCoordinate compareCoord)
    {
        if (this.equals(compareCoord))
        {
            return new BigDecimal(0);
        }

        BigDecimal callingLat = this.getLatitude();
        BigDecimal callingLong = this.getLongitude();

        BigDecimal compareLat = compareCoord.getLatitude();
        BigDecimal compareLong = compareCoord.getLongitude();

        BigDecimal xCoords = compareLat.subtract(callingLat);
        BigDecimal yCoords = compareLong.subtract(callingLong);

        BigDecimal product = xCoords.pow(2).add(yCoords.pow(2));
        BigDecimal distance = sqrt(product);

        return distance;
    }
}
