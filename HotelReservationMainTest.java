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
	}

	@Test
	public void addHotelNameAndRateTest1() {
		try {
			assertTrue(hotelResevation.addHotelNameAndRate("Lakewood", 110));
		} catch (InvalidHotelAndDateType e) {
			assertEquals(Exception_Type.INVALID_HOTEL_NAME, e.type);
		}
	}

	@Test
	public void addHotelNameAndRateTest2() {
		try {
			assertTrue(hotelResevation.addHotelNameAndRate("Bridgewood", 160));
		} catch (InvalidHotelAndDateType e) {
			assertEquals(Exception_Type.INVALID_HOTEL_NAME, e.type);
		}
	}

	@Test
	public void addHotelNameAndRateTest3() {
		try {
			assertTrue(hotelResevation.addHotelNameAndRate("Ridgewood", 220));
		} catch (InvalidHotelAndDateType e) {
			assertEquals(Exception_Type.INVALID_HOTEL_NAME, e.type);
		}
	}

	@Test
	public void findCheapestHotelTest4() throws ParseException {
		try {
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
}
