package com.pluralsight.service;

import java.util.List;

import com.pluralsight.model.Ride;

public interface RideService {

	List<Ride> getRides();
	Ride createRide(Ride ride);
	Ride findRideById(String rideId);
   Ride updateRide(Ride ride);

}