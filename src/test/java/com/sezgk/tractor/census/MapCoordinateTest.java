package com.sezgk.tractor.census;

import java.math.BigDecimal;

import org.junit.Test;

public class MapCoordinateTest {

	@Test
	public void findDistance() {
		
		BigDecimal testLat = new BigDecimal(39.721077);
		BigDecimal testLong = new BigDecimal(-79.476661);
		
		MapCoordinate testCoord = new MapCoordinate(testLat, testLong);
		
		//BigDecimal testDist = testCoord.getDistance(new MapCoordinate(new BigDecimal(30.0), new BigDecimal(70.0)));
		BigDecimal testDist = testCoord.getDistance(new MapCoordinate(new BigDecimal(30.0), new BigDecimal(-70.0)));
		System.out.println(testDist);
		
		
		
	}
}
