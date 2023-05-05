package com.driver.repositories;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HotelRepo {
    Map<String,Hotel> hotelMap = new HashMap<>();
    Map<Integer,User> userMap = new HashMap<>();
    Map<String,Booking> bookingMap = new HashMap<>();

    public void addHotel(Hotel hotel) {
        hotelMap.put(hotel.getHotelName(),hotel);
    }

    public boolean hotelExists(String hotelName) {
        return hotelMap.containsKey(hotelName);
    }

    public void addUser(User user) {
        userMap.put(user.getaadharCardNo(),user);
    }

    public List<Hotel> getHotelsAsList() {
        return  new ArrayList<Hotel>(hotelMap.values());
    }

    public Map<String, Hotel> getHotelMap() {
        return hotelMap;
    }

    public void setHotelMap(Map<String, Hotel> hotelMap) {
        this.hotelMap = hotelMap;
    }

    public Map<Integer, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<Integer, User> userMap) {
        this.userMap = userMap;
    }

    public Hotel getHotel(String hotelName) {
        return hotelMap.get(hotelName);
    }

    public void addBooking(Booking booking) {
        bookingMap.put(booking.getBookingId(),booking);
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<Booking>(bookingMap.values());
    }
}
