package com.pluralsight.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pluralsight.model.BatchRequest;
import com.pluralsight.model.Ride;
import com.pluralsight.repository.RideRepository;

@Service("rideService")
public class RideServiceImpl implements RideService {

	@Autowired
	private RideRepository rideRepository;
	
	@Override
	public Ride createRide(Ride ride) {
	   return rideRepository.createRide(ride);
	}
	
	@Override
	public List<Ride> getRides() {
		return rideRepository.getRides();
	}

   @Override
   public Ride findRideById(String rideId) {
      return rideRepository.findRideById(rideId);
   }

   @Override
   public Ride updateRide(Ride ride) {
      return rideRepository.updateRide(ride);
   }

   @Override
   @Transactional
   public List<Ride> batchUpdateRides(BatchRequest batchRequest) {
      List<Ride> rides = rideRepository.getRides();
      List<Object[]> pairs = new ArrayList<>();
      for (Ride ride : rides) {
         Object[] temp = {new Date(), ride.getId()};
         pairs.add(temp);
      }
      rideRepository.updateRides(pairs);
      throw new DataAccessException("Test Access Exception Thrown") {
         private static final long serialVersionUID = -7235004524905594979L;
      };
   }

   @Override
   public void deleteRideById(String rideId) {
      rideRepository.deleteRideById(rideId);
   }
}
