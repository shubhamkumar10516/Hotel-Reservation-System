package com.bridgeLabz.hotelResevationSystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HotelReservationMain 
{

	private Map<String, Double> hotelRateMap = new HashMap<>();
	private double PRICE_OF_LAKEWOOD_WEEKDAYS = 110;
	private double PRICE_OF_LAKEWOOD_WEEKNDAYS = 90;
	private double PRICE_OF_BWOOD_WEEKDAYS = 160;
	private double PRICE_OF_BWOOD_WEEKNDAYS = 60;
	private double PRICE_OF_RWOOD_WEEKDAYS = 220;
	private double PRICE_OF_RWOOD_WEEKNDAYS = 150;
	public void printWelcomeMessage() {
		System.out.println("***Welcome to Hotel Resevation System***");
	}
	
	public boolean addHotelNameAndRate(String hotelName, double hotelRates) {
		if(hotelRateMap.put(hotelName, hotelRates) == null)
			return true;
		return false;
	} 
	public String findCheapestHotel(String inDate, String outDate) throws ParseException {
		Date date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(inDate);
		Date date2 = new SimpleDateFormat("dd-MMM-yyyy").parse(outDate);
		double price = 0.0;
		double priceForLw = 0.0;
		double priceForBw = 0.0;
		double priceforRw = 0.0;
		long diff = date2.getTime() - date1.getTime();
	    long noOfDays = diff/(24*60*60*1000) + 1;
		
	    if((date1.getDay() == 0 || date1.getDay() == 6) &&
	    		(date2.getDay() == 0 || date2.getDay() == 6)&&
	    		 (date2 != date1)) {
	    	priceForLw = 2 * PRICE_OF_LAKEWOOD_WEEKNDAYS + (noOfDays - 2) * PRICE_OF_LAKEWOOD_WEEKDAYS;
	    	priceForBw = 2 * PRICE_OF_BWOOD_WEEKNDAYS + (noOfDays - 2) * PRICE_OF_BWOOD_WEEKDAYS;
	    	priceforRw = 2 * PRICE_OF_RWOOD_WEEKNDAYS + (noOfDays -2) * PRICE_OF_RWOOD_WEEKDAYS;
	    }
	    else if((date1.getDay() == 0 || date1.getDay() == 6) || (date2.getDay() == 0 || date2.getDay() == 6)) {
	    	
	    	priceForLw = 1 * PRICE_OF_LAKEWOOD_WEEKNDAYS + (noOfDays - 1) * PRICE_OF_LAKEWOOD_WEEKDAYS;
	    	priceForBw = 1 * PRICE_OF_BWOOD_WEEKNDAYS + (noOfDays - 1) * PRICE_OF_BWOOD_WEEKDAYS;
	    	priceforRw = 1 * PRICE_OF_RWOOD_WEEKNDAYS + (noOfDays - 1) * PRICE_OF_RWOOD_WEEKDAYS;
	    	
	    }
	    else {
	    	
	    	priceForLw = (noOfDays) * PRICE_OF_LAKEWOOD_WEEKDAYS;
	    	priceForBw = (noOfDays) * PRICE_OF_BWOOD_WEEKDAYS;
	    	priceforRw = (noOfDays) * PRICE_OF_RWOOD_WEEKDAYS;
	    	
	    }
		
	    String hotel = minCost(priceForLw, priceForBw, priceforRw);
	    
		return hotel;
	}
	
	public String minCost(double a , double b, double c) {
		String hotel = "Lakewood";
		double minVal = a;
		
		if(minVal > b) {
			minVal = b;
			hotel = "Bridgewood";
		}
		else if(minVal > c) {
			minVal = c;
			hotel = "Ridgewood";
		}
		return hotel;
	}
 }
