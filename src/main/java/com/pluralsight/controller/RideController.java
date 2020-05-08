package com.pluralsight.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pluralsight.model.Ride;
import com.pluralsight.service.RideService;

@Controller
public class RideController {

	@Autowired
	private RideService rideService;
	
	@GetMapping(value = "/rides")
	public @ResponseBody List<Ride> getRides() {
		return rideService.getRides();
	}
	
	@PutMapping(value = "/rides")
	public ResponseEntity<Ride> createRide(@RequestBody Ride ride) {
	   Ride createdRide = rideService.createRide(ride);
	   return ResponseEntity.created(URI.create("http://localhost:8080/ride_tracker/rides/1")).body(createdRide);
	}
	
}
