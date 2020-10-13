package com.bridgeLabz.hotelResevationSystem;

public class InvalidHotelAndDateType extends Exception {

	enum Exception_Type {
		INVALID_HOTEL_NAME, INVALID_RATING_TYPE, INVALID_DATE_FORMAT;
	}

	Exception_Type type;

	public InvalidHotelAndDateType(Exception_Type type, String msg) {
		super(msg);
		this.type = type;
	}

}
