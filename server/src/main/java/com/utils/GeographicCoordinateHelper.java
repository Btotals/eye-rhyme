package com.utils;


public class GeographicCoordinateHelper {
	private static Double EARTH_RADIUS = new Double(6371);
	
	public static Double[] getGeographicRange(Double longtitude, Double latitude, Integer distance) {
		Double dlng = 2 * Math.asin(Math.sin(distance / (2 * EARTH_RADIUS)) / Math.cos(latitude));
		dlng = Math.abs(Math.toDegrees(dlng));
		Double dlat = distance / EARTH_RADIUS;
		dlat = Math.abs(Math.toDegrees(dlat));
		Double []res = {longtitude + dlng, longtitude - dlng, latitude + dlat, latitude - dlat};
		return res;
	}
}
