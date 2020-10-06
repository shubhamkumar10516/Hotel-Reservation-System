package com.bridgeLabz.hotelResevationSystem;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;


public class HotelReservationMainTest 
{
	private HotelReservationMain hotelResevation = null;
    @Before
    public void initialize() {
    	hotelResevation = new HotelReservationMain();
    }
	
    @Test
    public void addHotelNameAndRateTest1()
    {
        assertTrue(hotelResevation.addHotelNameAndRate("Lakewood", 110));
    }
    
    @Test
    public void addHotelNameAndRateTest2()
    {
        assertTrue(hotelResevation.addHotelNameAndRate("Bridgewood", 160));
    }
    
    @Test
    public void addHotelNameAndRateTest3()
    {
        assertTrue(hotelResevation.addHotelNameAndRate("Ridgewood", 220));
    }
    
    @Test
    public void findCheapestHotelTest4() throws ParseException
    {
        assertEquals("Lakewood",hotelResevation.findCheapestHotel("10-sep-2020", "11-sep-2020"));
    }
    
    @Test
    public void findCheapestHotelTest5() throws ParseException
    {
        assertEquals("Bridgewood",hotelResevation.findCheapestHotel("11-sep-2020", "12-sep-2020"));
    }   
    
    @Test
    public void findCheapestHotelRatingTest6() throws ParseException
    {
        assertEquals(4,hotelResevation.findRating("11-sep-2020", "12-sep-2020"));
    }   
    
    @Test
    public void BestHotelRatingAndPriceTest7() throws ParseException
    {
        assertEquals("Ridgewood", hotelResevation.bestRatedHotel());
        
    }   
    
    @Test
    public void BestHotelRatingAndPriceTest8() throws ParseException
    {
        assertTrue(370 == hotelResevation.bestRatedHotelPrice("11-sep-2020", "12-sep-2020"));
    }
    
    @Test
    public void findCheapestHotelForRewardingCustTest9() throws ParseException
    {
    	String op = hotelResevation.findChepestHotelForRewardingCust("11-sep-2020", "12-sep-2020");
        assertEquals("Ridgewood, Rating: 5 and Total rates: $140.0", op);
    }
}
