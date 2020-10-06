package com.bridgeLabz.hotelResevationSystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.*;
public class HotelReservationMain 
{

	private Map<String, Double> hotelRateMap = new HashMap<>();
	private Map<String, Double> hotelRateMapForRewardingCust = new HashMap<>();
	private List<Double> priceAtWeekend = new ArrayList<>();
	private List<Double> priceAtWeekendForRewardingCust = new ArrayList<>();
	private List<Integer> rating = new ArrayList<>();
	private List<String> hotelList = new ArrayList<>();
	private double fare = 0.0;
	public void addPriceAtWeekend() {
		priceAtWeekend.add(90.0);
		priceAtWeekend.add(50.0);
		priceAtWeekend.add(150.0);
	}
	
	public void addRating() {
		rating.add(3);
		rating.add(4);
		rating.add(5);
		hotelList.add("Lakewood");
		hotelList.add("Bridgewood");
		hotelList.add("Ridgewood");
	}
	
	public void printWelcomeMessage() {
		System.out.println("***Welcome to Hotel Resevation System***");
	}
	
	public boolean addHotelNameAndRate(String hotelName, double hotelRates) {
		
		hotelRateMap.put("Lakewood", 110.0);
		hotelRateMap.put("Bridgewood", 150.0);
		hotelRateMap.put("Ridgewood", 220.0);
		if(hotelRateMap.put(hotelName, hotelRates) == null)
			return false;
		return true;
	} 
	
	public int findRating(String inDate, String outDate) throws ParseException {
		String hotel = findCheapestHotel(inDate, outDate);
		addRating();
		if(hotel.equals("Lakewood"))
			return rating.get(0);
		else if(hotel.equals("Bridgewood"))
			return rating.get(1);
		return rating.get(2);
		
	}
	
	public String findCheapestHotel(String inDate, String outDate) throws ParseException {
		
		addHotelNameAndRate( null,  0.0);
		addPriceAtWeekend();
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
	    	priceForLw = 2 * priceAtWeekend.get(0) + (noOfDays - 2) * hotelRateMap.get("Lakewood");
	    	priceForBw = 2 * priceAtWeekend.get(1) + (noOfDays - 2) * hotelRateMap.get("Bridgewood");
	    	priceforRw = 2 * priceAtWeekend.get(2) + (noOfDays - 2) * hotelRateMap.get("Ridgewood");
	    }
	    else if((date1.getDay() == 0 || date1.getDay() == 6) || (date2.getDay() == 0 || date2.getDay() == 6)) {

	    	priceForLw = 1 * priceAtWeekend.get(0) + (noOfDays - 1) * hotelRateMap.get("Lakewood");
	    	priceForBw = 1 * priceAtWeekend.get(1) + (noOfDays - 1) * hotelRateMap.get("Bridgewood");
	    	priceforRw = 1 * priceAtWeekend.get(2) + (noOfDays - 1) * hotelRateMap.get("Ridgewood");
	    }
	    else {
	    	

	    	priceForLw = noOfDays  * hotelRateMap.get("Lakewood");
	    	priceForBw = noOfDays * hotelRateMap.get("Bridgewood");
	    	priceforRw = noOfDays  * hotelRateMap.get("Ridgewood");
	    	
	    }
		
	    String hotel = minCost(priceForLw, priceForBw, priceforRw);
	       
		return hotel;
	}
	
	public String bestRatedHotel() {
		addRating();
		int l = rating.size();
		String name = hotelList.get(l-1);
		return name;
	}
	
    public double bestRatedHotelPrice(String inDate, String outDate) throws ParseException {
		
    	addHotelNameAndRate( null,  0.0);
		addPriceAtWeekend();
		Date date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(inDate);
		Date date2 = new SimpleDateFormat("dd-MMM-yyyy").parse(outDate);
		
		double priceforRw = 0.0;
		long diff = date2.getTime() - date1.getTime();
	    long noOfDays = diff/(24*60*60*1000) + 1;
		
	    if((date1.getDay() == 0 || date1.getDay() == 6) &&
	    		(date2.getDay() == 0 || date2.getDay() == 6)&&
	    		 (date2 != date1)) {
	    	priceforRw = 2 * priceAtWeekend.get(2) + (noOfDays - 2) * hotelRateMap.get("Ridgewood");
	    }
	    else if((date1.getDay() == 0 || date1.getDay() == 6) || (date2.getDay() == 0 || date2.getDay() == 6)) {

	    	priceforRw = 1 * priceAtWeekend.get(2) + (noOfDays - 1) * hotelRateMap.get("Ridgewood");
	    }
	    else {
	    	
	    	priceforRw = noOfDays  * hotelRateMap.get("Ridgewood");
	    	
	    }

		return priceforRw;
	}
	
    public void priceForRewardingCust() {
    	
    	hotelRateMapForRewardingCust.put("Lakewood", 80.0);
    	hotelRateMapForRewardingCust.put("Bridgewood", 110.0);
    	hotelRateMapForRewardingCust.put("Ridgewood", 100.0);
    	priceAtWeekendForRewardingCust.add(80.0);
    	priceAtWeekendForRewardingCust.add(50.0);
    	priceAtWeekendForRewardingCust.add(40.0);
    }
    
    public String findChepestHotelForRewardingCust(String inDate, String outDate) throws ParseException  {
    	priceForRewardingCust();
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
	    	priceForLw = 2 * priceAtWeekendForRewardingCust.get(0) + (noOfDays - 2) * hotelRateMapForRewardingCust.get("Lakewood");
	    	priceForBw = 2 * priceAtWeekendForRewardingCust.get(1) + (noOfDays - 2) * hotelRateMapForRewardingCust.get("Bridgewood");
	    	priceforRw = 2 * priceAtWeekendForRewardingCust.get(2) + (noOfDays - 2) * hotelRateMapForRewardingCust.get("Ridgewood");
	    }
	    else if((date1.getDay() == 0 || date1.getDay() == 6) || (date2.getDay() == 0 || date2.getDay() == 6)) {

	    	priceForLw = 1 * priceAtWeekendForRewardingCust.get(0) + (noOfDays - 1) * hotelRateMapForRewardingCust.get("Lakewood");
	    	priceForBw = 1 * priceAtWeekendForRewardingCust.get(1) + (noOfDays - 1) * hotelRateMapForRewardingCust.get("Bridgewood");
	    	priceforRw = 1 * priceAtWeekendForRewardingCust.get(2) + (noOfDays - 1) * hotelRateMapForRewardingCust.get("Ridgewood");
	    }
	    else {
	    	

	    	priceForLw = noOfDays  * hotelRateMapForRewardingCust.get("Lakewood");
	    	priceForBw = noOfDays * hotelRateMapForRewardingCust.get("Bridgewood");
	    	priceforRw = noOfDays  * hotelRateMapForRewardingCust.get("Ridgewood");
	    	
	    }
		
	    String hotel = minCost(priceForLw, priceForBw, priceforRw);
	    addRating();
	    int i;
	    for( i = 0 ; i < hotelList.size(); i++) {
	         if(hotelList.get(i).equals(hotel)) break;
	    }
		return hotel + ", Rating: "+(i+3)+" and Total rates: $"+fare;

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
		fare = minVal;
		if(c == minVal) 
			return "Ridgewood";
		else if(b == minVal)
			return "Bridgewood";
		
		return hotel;
	}
	
	
	
 }
