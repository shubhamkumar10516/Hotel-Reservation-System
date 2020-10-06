package com.bridgeLabz.hotelResevationSystem;

import java.util.*;

public class HotelReservationMain 
{

	private Map<String, Double> hotelRateMap = new HashMap<>();
	
	public void printWelcomeMessage() {
		System.out.println("***Welcome to Hotel Resevation System***");
	}
	
	public boolean addHotelNameAndRate(String hotelName, double hotelRates) {
		if(hotelRateMap.put(hotelName, hotelRates) == null)
			return true;
		return false;
	} 
 }
