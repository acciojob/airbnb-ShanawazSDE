package com.driver.services;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repositories.HotelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HotelService {
    @Autowired
    HotelRepo hotelRepo;

    public void addHotel(Hotel hotel) throws RuntimeException {
        if(hotel == null || hotel.getHotelName() == null
                || hotelRepo.hotelExists(hotel.getHotelName())) throw new RuntimeException("FAILURE");

        hotelRepo.addHotel(hotel);
    }

    public void addUser(User user) {
        hotelRepo.addUser(user);
    }

    public String getHotelWithMostFacilities() {
        List<Hotel> list = hotelRepo.getHotelsAsList();
        if(list.size() == 0) return "";
        Comparator<Hotel> comparator = new Comparator<Hotel>() {
            @Override
            public int compare(Hotel a, Hotel b) {
                if(a.getFacilities().size() > b.getFacilities().size())return -1;
                if(a.getFacilities().size() < b.getFacilities().size()) return 1;
                return a.getHotelName().compareTo(b.getHotelName());
            }
        };
        Collections.sort(list,comparator);
        if(list.get(0).getFacilities().size() == 0) return "";
        return list.get(0).getHotelName();

    }

    public int bookARoom(Booking booking) {
        Hotel bookedHotel = hotelRepo.getHotel(booking.getHotelName());
        if(bookedHotel.getAvailableRooms() < booking.getNoOfRooms()) return -1;
        String bookingId = UUID.randomUUID().toString();
        booking.setBookingId(bookingId);
        booking.setAmountToBePaid(booking.getNoOfRooms() * bookedHotel.getPricePerNight());
        hotelRepo.addBooking(booking);
        return booking.getAmountToBePaid();
    }

    public int getBookings(Integer aadharCard) {
        int count = 0;
        for(Booking booking : hotelRepo.getAllBookings()){
            if(booking.getBookingAadharCard() == aadharCard)count++;
        }
        return count;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotelRepo.getHotel(hotelName);
        List<Facility> oldFacilities = hotel.getFacilities();
        Set<Facility> oldFacilitySet = new HashSet<>(oldFacilities);

        for(Facility newFacility : newFacilities){
            if(!oldFacilitySet.contains(newFacility))oldFacilities.add(newFacility);
        }
        hotel.setFacilities(oldFacilities);
       return hotel;
    }
}
