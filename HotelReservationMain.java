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
	public void addPriceAtWeekend(double priceAtWeek) {
		priceAtWeekend.add(priceAtWeek);
	}

	// adding rating of hotel
	public void addRating(int rate, String hotel) {
		rating.add(rate);
		hotelList.add(hotel);
	}

	// printing welcome message
	public void printWelcomeMessage() {
		System.out.println("***Welcome to Hotel Resevation System***");
	}

	// adding hotel and its rates
	public boolean addHotelNameAndRate(String hotelName, double hotelRates) throws InvalidHotelAndDateType {
		if (!hotelName.matches(hotelNamePattern))
			throw new InvalidHotelAndDateType(Exception_Type.INVALID_HOTEL_NAME, "Enter proper hotel name");
		if (hotelRateMap.put(hotelName, hotelRates) == null)
			return false;
		return true;
	}

	// finding cheapest and rated hotel
	public int findRating(String inDate, String outDate) throws ParseException, InvalidHotelAndDateType {
		String hotel = findCheapestHotel(inDate, outDate);
		//addRating();
		if (hotel.equals(hotelList.get(0)))
			return rating.get(0);
		else if (hotel.equals(hotelList.get(1)))
			return rating.get(1);
		return rating.get(2);
	}

	// comparing price of each hotel and showing result as best rated and cheapest
	// hotel
	public String findCheapestHotel(String inDate, String outDate) throws ParseException, InvalidHotelAndDateType {
		if (inDate.matches(datePattern))
			throw new InvalidHotelAndDateType(Exception_Type.INVALID_DATE_FORMAT, "Enter proper date");
		addHotelNameAndRate(null, 0.0);
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
			priceForLw = calculatePriceForCust(noOfDays, hotelList.get(0), 0);
			priceForBw = calculatePriceForCust(noOfDays, hotelList.get(1), 1);
			priceforRw = calculatePriceForCust(noOfDays, hotelList.get(2), 2);
		} else if ((date1.getDay() == 0 || date1.getDay() == 6) || (date2.getDay() == 0 || date2.getDay() == 6)) {
			priceForLw = calculatePriceForCustAtWn(noOfDays, hotelList.get(0), 0);
			priceForBw = calculatePriceForCustAtWn(noOfDays, hotelList.get(1), 1);
			priceforRw = calculatePriceForCustAtWn(noOfDays, hotelList.get(2), 2);
		} else {
			priceForLw = noOfDays * hotelRateMap.get(hotelList.get(0));
			priceForBw = noOfDays * hotelRateMap.get(hotelList.get(1));
			priceforRw = noOfDays * hotelRateMap.get(hotelList.get(2));
		}
		totalPriceList.add(priceForLw);
		totalPriceList.add(priceForBw);
		totalPriceList.add(priceforRw);
		String hotel = minCost(priceForLw, priceForBw, priceforRw);
		return hotel;
	}

	// returning best rated
	public String bestRatedHotel() {
		int l = rating.size();
		String name = hotelList.get(l - 1);
		return name;
	}

	// best rated hotel with price for general customer
	public double bestRatedHotelPrice(String inDate, String outDate) throws ParseException, InvalidHotelAndDateType {
		Date date1 = new SimpleDateFormat("ddMMMyyyy").parse(inDate);
		Date date2 = new SimpleDateFormat("ddMMMyyyy").parse(outDate);
		double priceforRw = 0.0;
		long diff = date2.getTime() - date1.getTime();
		long noOfDays = diff / (24 * 60 * 60 * 1000) + 1;
		if ((date1.getDay() == 0 || date1.getDay() == 6) && (date2.getDay() == 0 || date2.getDay() == 6)
				&& (date2 != date1)) {
			priceforRw = calculatePriceForCust(noOfDays, hotelList.get(2), 2);
		} else if ((date1.getDay() == 0 || date1.getDay() == 6) || (date2.getDay() == 0 || date2.getDay() == 6)) {
			priceforRw = calculatePriceForCustAtWn(noOfDays, hotelList.get(2), 2);
		} else {
			priceforRw = noOfDays * hotelRateMap.get(hotelList.get(2));
		}
		return priceforRw;
	}

	public void priceForRewardingCust(String hotel, double nCost, double weekendCost) {
		hotelRateMapForRewardingCust.put(hotel,nCost);
		priceAtWeekendForRewardingCust.add(weekendCost);
	}

	public String findChepestHotelForRewardingCust(String inDate, String outDate)
			throws ParseException, InvalidHotelAndDateType {
		if (!(inDate.matches(datePattern) && (outDate.matches(datePattern))))
			throw new InvalidHotelAndDateType(Exception_Type.INVALID_DATE_FORMAT, "Enter proper date format");
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
			priceForLw = calculatePriceForRewardingCust(noOfDays, hotelList.get(0), 0);
			priceForBw = calculatePriceForRewardingCust(noOfDays, hotelList.get(1), 1);
			priceforRw = calculatePriceForRewardingCust(noOfDays, hotelList.get(2), 2);
		} else if ((date1.getDay() == 0 || date1.getDay() == 6) || (date2.getDay() == 0 || date2.getDay() == 6)) {
			priceForLw = calculatePriceForRewardingCustAtWn(noOfDays, hotelList.get(0), 0);
			priceForBw = calculatePriceForRewardingCustAtWn(noOfDays, hotelList.get(1), 1);
			priceforRw = calculatePriceForRewardingCustAtWn(noOfDays, hotelList.get(2), 2);
		} else {
			priceForLw = noOfDays * hotelRateMapForRewardingCust.get(hotelList.get(0));
			priceForBw = noOfDays * hotelRateMapForRewardingCust.get(hotelList.get(1));
			priceforRw = noOfDays * hotelRateMapForRewardingCust.get(hotelList.get(2));
		}
		String hotel = minCost(priceForLw, priceForBw, priceforRw);
		int i;
		for (i = 0; i < hotelList.size(); i++) {
			if (hotelList.get(i).equals(hotel))
				break;
		}
		return hotel + ", Rating: " + (i + 3) + " and Total rates: $" + fare;
	}

	// calculating minimum of three
	public String minCost(double a, double b, double c) {
		String hotel = hotelList.get(0);
		double minVal = a;
		if (minVal > b) {
			minVal = b;
			hotel = hotelList.get(1);
		} else if (minVal > c) {
			minVal = c;
			hotel = hotelList.get(2);
		}
		fare = minVal;
		if (c == minVal)
			return hotelList.get(2);
		else if (b == minVal)
			return hotelList.get(1);
		return hotel;
	}

	// cheapest and best rated for normal customer
	public Double findCheapestBestRatedForRegularCust(String inDate, String outDate)
			throws ParseException, InvalidHotelAndDateType {
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
