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
}
