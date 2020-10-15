package com.bridgeLabz.hotelResevationSystem;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import com.bridgeLabz.hotelResevationSystem.InvalidHotelAndDateType.Exception_Type;

public class HotelReservationMainTest {
	private HotelReservationMain hotelResevation = null;

	@Before
	public void initialize() {
		hotelResevation = new HotelReservationMain();
		hotelResevation.addPriceAtWeekend(90);
		hotelResevation.addPriceAtWeekend(50);
		hotelResevation.addPriceAtWeekend(150);
		hotelResevation.addRating(3, "Lakewood");
		hotelResevation.addRating(4, "Bridgewood");
		hotelResevation.addRating(5, "Ridgewood");
		hotelResevation.priceForRewardingCust("Lakewood", 80, 80);
		hotelResevation.priceForRewardingCust("Bridgewood", 110, 50);
		hotelResevation.priceForRewardingCust("Ridgewood", 100, 40);
	}

	@Test
	public void addHotelNameAndRateTest1() {
		try {
			assertFalse(hotelResevation.addHotelNameAndRate("Lakewood", 110));
		} catch (InvalidHotelAndDateType e) {
			assertEquals(Exception_Type.INVALID_HOTEL_NAME, e.type);
		}
	}

	@Test
	public void addHotelNameAndRateTest2() {
		try {
			assertFalse(hotelResevation.addHotelNameAndRate("Bridgewood", 160));
		} catch (InvalidHotelAndDateType e) {
			assertEquals(Exception_Type.INVALID_HOTEL_NAME, e.type);
		}
	}

	@Test
	public void addHotelNameAndRateTest3() {
		try {
			assertFalse(hotelResevation.addHotelNameAndRate("Ridgewood", 220));
		} catch (InvalidHotelAndDateType e) {
			assertEquals(Exception_Type.INVALID_HOTEL_NAME, e.type);
		}
	}

	@Test
	public void findCheapestHotelTest4() throws ParseException {
		try {
			hotelResevation.addHotelNameAndRate("Lakewood", 110);
			hotelResevation.addHotelNameAndRate("Bridgewood", 160);
			hotelResevation.addHotelNameAndRate("Ridgewood", 220);
			assertEquals("Lakewood", hotelResevation.findCheapestHotel("10sep2020", "11sep2020"));
		} catch (InvalidHotelAndDateType e) {
			assertEquals(Exception_Type.INVALID_DATE_FORMAT, e.type);
		}
	}

	@Test
	public void findCheapestHotelTest5() throws ParseException {
		try {
			assertEquals("Bridgewood", hotelResevation.findCheapestHotel("11sep2020", "12sep2020"));
		} catch (InvalidHotelAndDateType e) {
			assertEquals(Exception_Type.INVALID_DATE_FORMAT, e.type);
		}
	}

	@Test
	public void findCheapestHotelRatingTest6() throws ParseException {
		try {
			assertEquals(4, hotelResevation.findRating("11sep2020", "12sep2020"));
		} catch (InvalidHotelAndDateType e) {
			assertEquals(Exception_Type.INVALID_DATE_FORMAT, e.type);
		}
	}

	@Test
	public void BestHotelRatingAndPriceTest7() throws ParseException {
		assertEquals("Ridgewood", hotelResevation.bestRatedHotel());

	}

	@Test
	public void BestHotelRatingAndPriceTest8() throws ParseException {
		try {
			hotelResevation.addHotelNameAndRate("Lakewood", 110);
			hotelResevation.addHotelNameAndRate("Bridgewood", 160);
			hotelResevation.addHotelNameAndRate("Ridgewood", 220);
			assertTrue(370 == hotelResevation.bestRatedHotelPrice("11sep2020", "12sep2020"));
		} catch (InvalidHotelAndDateType e) {
			assertEquals(Exception_Type.INVALID_DATE_FORMAT, e.type);
		}
	}

	@Test
	public void findCheapestHotelForRewardingCustTest9() throws ParseException {

		String op;
		try {
			op = hotelResevation.findChepestHotelForRewardingCust("11sep2020", "12sep2020");
			assertEquals("Ridgewood, Rating: 5 and Total rates: $140.0", op);
		} catch (InvalidHotelAndDateType e) {
			assertEquals(Exception_Type.INVALID_DATE_FORMAT, e.type);
		}
	}

	@Test
	public void findCheapestBestRatedForGeneralCustHotelTest10() throws ParseException {
		try {
			assertEquals(4, hotelResevation.findRating("11sep2020", "12sep2020"));
			assertEquals("Bridgewood", hotelResevation.findCheapestHotel("11sep2020", "12sep2020"));
			assertTrue(200.0 == (hotelResevation.findCheapestBestRatedForRegularCust("11sep2020", "12sep2020")));
		} catch (InvalidHotelAndDateType e) {
			assertEquals(Exception_Type.INVALID_DATE_FORMAT, e.type);
		}
	}
}
