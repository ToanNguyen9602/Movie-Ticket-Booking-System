package com.demo.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.demo.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.demo.dtos.BookingDTO;
import com.demo.dtos.FoodBookingDTO;
import com.demo.repositories.AccountRepository;
import com.demo.repositories.BookingDetailRepository;
import com.demo.repositories.BookingRepository2;
import com.demo.repositories.FoodDetailRepository;
import com.demo.repositories.ShowsRepository;
import com.demo.services.BookingService;

@Service
public class BookingServiceImp implements BookingService {

    @Autowired
    private BookingRepository2 bookingRepository2;
    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    @Autowired
    private FoodDetailRepository foodDetailRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ShowsRepository showsRepository;

    @Override
    public List<Booking> findBookingbyAccountId(int id) {
        return bookingRepository2.findBookingbyAccountId(id);
    }

    @Override
    public Integer PaidforMovieByAccount(Account account) {
        return bookingDetailRepository.PaidforMovieByAccount(account);
    }

    @Override
    public boolean save(Account account, BookingDTO bookingDTO) {
        try {
            Booking booking = new Booking();
            booking.setAccount(account);
            booking.setCreated(new Date());
            booking.setStatus(true);
            Booking insertedBooking = bookingRepository2.save(booking);
            if (bookingDTO.getFoods() == null) {
                bookingDTO.setFoods(new LinkedList<FoodBookingDTO>());
            }

            List<FoodBookingDetails> foods = bookingDTO.getFoods().stream()
                    .map(food -> {
                        return new FoodBookingDetails(
                                new FoodBookingDetailsId(insertedBooking.getId(), Integer.parseInt(food.getId())),
                                booking,
                                new FoodMenu() {{
                                    setId(Integer.parseInt(food.getId()));
                                }},
                                Integer.parseInt(food.getQuantity()),
                                Integer.parseInt(food.getPrice()));
                    })
                    .toList();

            List<BookingDetails> bookingDetails = bookingDTO.getSeatIds()
                    .stream()
                    .map(seatId -> mapFromSeat(bookingDTO.getShowId(), seatId, insertedBooking.getId()))
                    .toList();

            booking.setBookingDetailses(new HashSet<>(bookingDetails));
            booking.setFoodBookingDetailses(new HashSet<>(foods));


            foods.forEach(food -> {
                food.setBooking(insertedBooking);
            });
            foodDetailRepository.saveAll(foods);
            bookingDetails.forEach(seat -> seat.setBooking(insertedBooking));
            bookingDetailRepository.saveAll(bookingDetails);
            BookingDetails bookingDetails1 = new BookingDetails();
            bookingDetails1.setId(new BookingDetailsId(insertedBooking.getId(), bookingDTO.getShowId(), Integer.parseInt(bookingDTO.getSeatIds().get(0).split("")[1])));
            bookingDetails1.setPrice(10000d);
            bookingDetailRepository.saveS(bookingDetails1.getPrice(),bookingDetails1.getId().getBookingId(),bookingDetails1.getId().getSeatsId(),bookingDetails1.getId().getShowsId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //	seatIdentifier consists of row and number as a String , ex "A1"
    private BookingDetails mapFromSeat(Integer showId, String seatIdentifider, int bookingId) {
        Shows show = showsRepository.findById(showId).get();
        Hall hall = show.getHall();
        List<Seats> seats = hall.getSeatses();
        String[] rowAndNumber = seatIdentifider.split("");


        List<Seats> filteredSeats = seats.stream()
                .filter(seat -> (seat.getRow().toLowerCase().equals(rowAndNumber[0].toLowerCase()) && seat.getNumber() == Integer.parseInt(rowAndNumber[1])))
                .toList();
        // hien cai filter nay dang tra ve 0 element  =))
        // doan tren code hoi lang nhang nhi =)) beginner ma


        // dau ta
        // vi du voi cai payload nay : 18", "13" thi cai split se la [1][8] va [1][3] a ? de minh fix lai chut do minh load cai Id cua bang seat len
        //chu seat no la A1 , A2 dai loai the giong ghe o may cai cinema , ban dang bi nham lan y , the bay h cai quan trong la cai ham nay ban se lay ve seatID de cho vao booking detail hay lay seat name de cho vao
        // dau so day chu dau  =)) oa nul la xong ah?  day nhe neu cai detail nay ban gan cho null thi neu cai if o duoi ko chay vao thi no cha loi =))
        // va de la ban filtered cai seat bang id hay la bang roww num va col num  =)) phan do la do 1 nguoi chi minh nhung chac la dang dung row va number  vay h chuyens ang find = id ah? ko phai de minh xem da
        // de minh kiem tra cai filtedSeat
        BookingDetails detail = new BookingDetails();
        if (filteredSeats.size() > 0) {
            detail.setId(new BookingDetailsId(bookingId, showId, filteredSeats.get(0).getId()));
            detail.setPrice(show.getMovie().getPrice());
            detail.setSeats(filteredSeats.get(0));
            detail.setShows(show);
        }

        return detail;
    }

    @Override
    public Integer newBooking(Account account, Booking booking) {
        // TODO Auto-generated method stub
        // insert booking vào db
        // return booking id

        return 0;
    }


    //save va lay ID cua booking
    @Override
    public boolean savedetail(Account account, BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setAccount(account);
        booking.setCreated(new Date());
        booking.setStatus(true);
        booking.getId();
        return true;
    }

    @Override
    public Booking findbybookingId(int bookingId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer saveAndGetId(Booking booking) {
        // TODO Auto-generated method stub
        return null;
    }


}
