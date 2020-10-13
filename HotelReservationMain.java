package com.bridgeLabz.hotelResevationSystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.bridgeLabz.hotelResevationSystem.InvalidHotelAndDateType.Exception_Type;

import java.util.*;

public class HotelReservationMain {

	private Map<String, Double> hotelRateMap = new HashMap<>();
	private Map<String, Double> hotelRateMapForRewardingCust = new HashMap<>();
	private List<Double> priceAtWeekend = new ArrayList<>();
	private List<Double> priceAtWeekendForRewardingCust = new ArrayList<>();
	private List<Integer> rating = new ArrayList<>();
	private List<String> hotelList = new ArrayList<>();
	private double fare = 0.0;
	private String hotelNamePattern = "[a-zA-Z]{3,}";
	private String datePattern = "[0-9]{2}[a-zA-Z]{3}[0-9]{4}";
	private String ratingPattern = "[1-5]{1}";
    private List<Double> totalPriceList = new ArrayList<>();
	// adding price of hotel at weekend
	public void addPriceAtWeekend() {
		priceAtWeekend.add(90.0);
		priceAtWeekend.add(50.0);
		priceAtWeekend.add(150.0);
	}

	// adding rating of hotel
	public void addRating() {
		rating.add(3);
		rating.add(4);
		rating.add(5);
		hotelList.add("Lakewood");
		hotelList.add("Bridgewood");
		hotelList.add("Ridgewood");
	}

	// printing welcome message
	public void printWelcomeMessage() {
		System.out.println("***Welcome to Hotel Resevation System***");
	}

	// adding hotel and its rates
	public boolean addHotelNameAndRate(String hotelName, double hotelRates) throws InvalidHotelAndDateType {
		if (!hotelName.matches(hotelNamePattern))
			throw new InvalidHotelAndDateType(Exception_Type.INVALID_HOTEL_NAME, "Enter proper hotel name");
		hotelRateMap.put("Lakewood", 110.0);
		hotelRateMap.put("Bridgewood", 150.0);
		hotelRateMap.put("Ridgewood", 220.0);
		if (hotelRateMap.put(hotelName, hotelRates) == null)
			return false;
		return true;
	}

	// finding cheapest and rated hotel
	public int findRating(String inDate, String outDate) throws ParseException, InvalidHotelAndDateType {
		String hotel = findCheapestHotel(inDate, outDate);
		addRating();
		if (hotel.equals("Lakewood"))
			return rating.get(0);
		else if (hotel.equals("Bridgewood"))
			return rating.get(1);
		return rating.get(2);
	}

	// comparing price of each hotel and showing result as best rated and cheapest
	// hotel
	public String findCheapestHotel(String inDate, String outDate) throws ParseException, InvalidHotelAndDateType {
		if (inDate.matches(datePattern))
			throw new InvalidHotelAndDateType(Exception_Type.INVALID_DATE_FORMAT, "Enter proper date");
		addHotelNameAndRate(null, 0.0);
		addPriceAtWeekend();
		Date date1 = new SimpleDateFormat("ddMMMyyyy").parse(inDate);
		Date date2 = new SimpleDateFormat("ddMMMyyyy").parse(outDate);
		double price = 0.0;
		double priceForLw = 0.0;
		double priceForBw = 0.0;
		double priceforRw = 0.0;
		long diff = date2.getTime() - date1.getTime();
		long noOfDays = diff / (24 * 60 * 60 * 1000) + 1;
		if ((date1.getDay() == 0 || date1.getDay() == 6) && (date2.getDay() == 0 || date2.getDay() == 6)
				&& (date2 != date1)) {
			priceForLw = calculatePriceForCust(noOfDays, "Lakewood", 0);
			priceForBw = calculatePriceForCust(noOfDays, "Bridgewood", 1);
			priceforRw = calculatePriceForCust(noOfDays, "Ridgewood", 2);
		} else if ((date1.getDay() == 0 || date1.getDay() == 6) || (date2.getDay() == 0 || date2.getDay() == 6)) {
			priceForLw = calculatePriceForCustAtWn(noOfDays, "Lakewood", 0);
			priceForBw = calculatePriceForCustAtWn(noOfDays, "Bridgewood", 1);
			priceforRw = calculatePriceForCustAtWn(noOfDays, "Ridgewood", 2);
		} else {
			priceForLw = noOfDays * hotelRateMap.get("Lakewood");
			priceForBw = noOfDays * hotelRateMap.get("Bridgewood");
			priceforRw = noOfDays * hotelRateMap.get("Ridgewood");
		}
		totalPriceList.add(priceForLw);
		totalPriceList.add(priceForBw);
		totalPriceList.add(priceforRw);
		String hotel = minCost(priceForLw, priceForBw, priceforRw);
		return hotel;
	}

	// returning best rated
	public String bestRatedHotel() {
		addRating();
		int l = rating.size();
		String name = hotelList.get(l - 1);
		return name;
	}

	// best rated hotel with price for general customer
	public double bestRatedHotelPrice(String inDate, String outDate) throws ParseException, InvalidHotelAndDateType {
		addPriceAtWeekend();
		Date date1 = new SimpleDateFormat("ddMMMyyyy").parse(inDate);
		Date date2 = new SimpleDateFormat("ddMMMyyyy").parse(outDate);
		double priceforRw = 0.0;
		long diff = date2.getTime() - date1.getTime();
		long noOfDays = diff / (24 * 60 * 60 * 1000) + 1;
		if ((date1.getDay() == 0 || date1.getDay() == 6) && (date2.getDay() == 0 || date2.getDay() == 6)
				&& (date2 != date1)) {
			priceforRw = calculatePriceForCust(noOfDays, "Ridgewood", 2);
		} else if ((date1.getDay() == 0 || date1.getDay() == 6) || (date2.getDay() == 0 || date2.getDay() == 6)) {
			priceforRw = calculatePriceForCustAtWn(noOfDays, "Ridgewood", 2);
		} else {
			priceforRw = noOfDays * hotelRateMap.get("Ridgewood");
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

	public String findChepestHotelForRewardingCust(String inDate, String outDate)
			throws ParseException, InvalidHotelAndDateType {
		if (!(inDate.matches(datePattern) && (outDate.matches(datePattern))))
			throw new InvalidHotelAndDateType(Exception_Type.INVALID_DATE_FORMAT, "Enter proper date format");
		priceForRewardingCust();
		Date date1 = new SimpleDateFormat("ddMMMyyyy").parse(inDate);
		Date date2 = new SimpleDateFormat("ddMMMyyyy").parse(outDate);
		double price = 0.0;
		double priceForLw = 0.0;
		double priceForBw = 0.0;
		double priceforRw = 0.0;
		long diff = date2.getTime() - date1.getTime();
		long noOfDays = diff / (24 * 60 * 60 * 1000) + 1;
		if ((date1.getDay() == 0 || date1.getDay() == 6) && (date2.getDay() == 0 || date2.getDay() == 6)
				&& (date2 != date1)) {
			priceForLw = calculatePriceForRewardingCust(noOfDays, "Lakewood", 0);
			priceForBw = calculatePriceForRewardingCust(noOfDays, "Bridgewood", 1);
			priceforRw = calculatePriceForRewardingCust(noOfDays, "Ridgewood", 2);
		} else if ((date1.getDay() == 0 || date1.getDay() == 6) || (date2.getDay() == 0 || date2.getDay() == 6)) {
			priceForLw = calculatePriceForRewardingCustAtWn(noOfDays, "Lakewood", 0);
			priceForBw = calculatePriceForRewardingCustAtWn(noOfDays, "Bridgewood", 1);
			priceforRw = calculatePriceForRewardingCustAtWn(noOfDays, "Ridgewood", 2);
		} else {
			priceForLw = noOfDays * hotelRateMapForRewardingCust.get("Lakewood");
			priceForBw = noOfDays * hotelRateMapForRewardingCust.get("Bridgewood");
			priceforRw = noOfDays * hotelRateMapForRewardingCust.get("Ridgewood");
		}
		String hotel = minCost(priceForLw, priceForBw, priceforRw);
		addRating();
		int i;
		for (i = 0; i < hotelList.size(); i++) {
			if (hotelList.get(i).equals(hotel))
				break;
		}
		return hotel + ", Rating: " + (i + 3) + " and Total rates: $" + fare;
	}

	// calculating minimum of three
	public String minCost(double a, double b, double c) {
		String hotel = "Lakewood";
		double minVal = a;
		if (minVal > b) {
			minVal = b;
			hotel = "Bridgewood";
		} else if (minVal > c) {
			minVal = c;
			hotel = "Ridgewood";
		}
		fare = minVal;
		if (c == minVal)
			return "Ridgewood";
		else if (b == minVal)
			return "Bridgewood";
		return hotel;
	}

	// cheapest and best rated for normal customer

	public Double findCheapestBestRatedForRegularCust(String inDate, String outDate) throws ParseException, InvalidHotelAndDateType {
		findCheapestHotel(inDate, outDate);
		Double minCost = totalPriceList.stream().min(Double::compare).get();
		return minCost;
	}
	// calculating price for different hotels at weekend and non-weekend
	public double calculatePriceForRewardingCust(long noOfDays, String hotelName, int i) {
		return (2 * priceAtWeekendForRewardingCust.get(i)
				+ (noOfDays - 2) * hotelRateMapForRewardingCust.get(hotelName));
	}

	public double calculatePriceForRewardingCustAtWn(long noOfDays, String hotelName, int i) {
		return (1 * priceAtWeekendForRewardingCust.get(i)
				+ (noOfDays - 1) * hotelRateMapForRewardingCust.get(hotelName));
	}

	private double calculatePriceForCustAtWn(long noOfDays, String hotelName, int i) {
		return (1 * priceAtWeekend.get(i) + (noOfDays - 1) * hotelRateMap.get(hotelName));
	}

	private double calculatePriceForCust(long noOfDays, String hotelName, int i) {
		return (2 * priceAtWeekend.get(i) + (noOfDays - 2) * hotelRateMap.get(hotelName));
	}
}
