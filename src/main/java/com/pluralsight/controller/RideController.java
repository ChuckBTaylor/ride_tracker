package com.pluralsight.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

   @GetMapping(value = "/rides/{rideId}")
   public ResponseEntity<Ride> getRideById(@RequestParam String rideId) {
      Ride foundRide = rideService.findRideById(rideId);
      return ResponseEntity.ok(foundRide);
   }

   @PostMapping(value = "/rides")
   public ResponseEntity<Ride> createRide(@RequestBody Ride ride) {
      Ride createdRide = rideService.createRide(ride);
      int createdId = createdRide.getId();
      return ResponseEntity.created(URI.create("http://localhost:8080/ride_tracker/rides/" + createdId)).body(createdRide);
   }

}
