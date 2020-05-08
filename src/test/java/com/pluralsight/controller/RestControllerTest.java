package com.pluralsight.controller;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.pluralsight.model.Ride;

public class RestControllerTest {
   
   private String local8080 = "http://localhost:8080/ride_tracker";
   private URI uri;
   
   @Before
   public void SetUp() {
      uri = null;
   }

   @Test(timeout = 3000)
   public void testGetRides() {
      RestTemplate restTemplate = new RestTemplate();
      uri = URI.create(local8080 + "/rides");
      RequestEntity<Void> request = new RequestEntity<>(HttpMethod.GET, uri);
      ResponseEntity<List<Ride>> ridesResponse = restTemplate.exchange(request, new ParameterizedTypeReference<List<Ride>>(){
      });
      List<Ride> rides = ridesResponse.getBody();

      for (Ride ride : rides) {
         System.out.println("Ride name: " + ride.getName());
      }
   }

   @Test(timeout = 3000)
   public void testCreateRide() {
      RestTemplate restTemplate = new RestTemplate();
      Ride ride = new Ride();
      ride.setDuration(38);
      ride.setName("Round Valley Trail");
      URI uri = URI.create(local8080 + "/rides");
      RequestEntity<Ride> requestEntity = new RequestEntity<>(ride, HttpMethod.PUT, uri);
      ResponseEntity<Ride> response = restTemplate.exchange(requestEntity, Ride.class);
      
      assertEquals(HttpStatus.CREATED,response.getStatusCode());
//      System.out.println("Ride name created: " + response.getBody().getName());
   }
}
