package com.pluralsight.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

import com.pluralsight.model.BatchRequest;
import com.pluralsight.model.Ride;

public class RestControllerTest {
   
   private String local8080 = "http://localhost:8080/ride_tracker";
   private URI uri;
   private RestTemplate restTemplate= new RestTemplate();
   
   @Before
   public void SetUp() {
      uri = null;
   }

   @Test(timeout = 3000)
   public void testGetRides() {
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
      Ride ride = new Ride();
      ride.setDuration(62);
      ride.setName("Yellow Fork Trail");
      URI uri = URI.create(local8080 + "/rides");
      RequestEntity<Ride> requestEntity = new RequestEntity<>(ride, HttpMethod.POST, uri);
      ResponseEntity<Ride> response = restTemplate.exchange(requestEntity, Ride.class);
      
      assertEquals(HttpStatus.CREATED,response.getStatusCode());
      assertNotNull("Ride object is returned", response.getBody());
      assertNotNull("Id is returned", response.getBody().getId());
      System.out.println("Ride name created: " + response.getBody().getName());
   }
   
   @Test(timeout = 3000)
   public void testGetRideById() {
      Integer id = Integer.valueOf(3);
      URI uri = URI.create(local8080 + "/rides/" + id);
      RequestEntity<Void> request = new RequestEntity<>(HttpMethod.GET, uri);
      ResponseEntity<Ride> response = restTemplate.exchange(request, Ride.class);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals("Ride gotten has same id as requested ride", id.intValue(), response.getBody().getId());
      System.out.println("Ride gotten " + response.getBody().getName());
   }
   
   @Test(timeout = 4000)
   public void testUpdateRideById() {
      Integer id = Integer.valueOf(3);
      URI uri = URI.create(local8080 + "/rides/" + id);
      RequestEntity<Void> request = new RequestEntity<>(HttpMethod.GET, uri);
      ResponseEntity<Ride> response = restTemplate.exchange(request, Ride.class);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertNotNull(response.getBody());
      Ride gottenRide = response.getBody();
      assertEquals("Ride gotten has same id as requested ride", id.intValue(), gottenRide.getId());
      System.out.println("Ride gotten " + response.getBody().getName());
      
      Integer gottenRideDuration = gottenRide.getDuration();
      gottenRide.setDuration(gottenRide.getDuration() + 1);
      
      RequestEntity<Ride> updateRequest = new RequestEntity<>(gottenRide, HttpMethod.PUT, uri);
      ResponseEntity<Ride> updateResponse = restTemplate.exchange(updateRequest, Ride.class);
      assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
      assertNotNull(updateResponse.getBody());
      Ride updatedRide = updateResponse.getBody();
      assertEquals(gottenRideDuration + 1, updatedRide.getDuration());
      System.out.println("Ride Updated");
      
   }
   
   @Test(timeout = 4000)
   public void testBatchUpdate(){
      URI uri = URI.create(local8080 + "/rides");
      RequestEntity<BatchRequest> batchRequest = new RequestEntity<>(new BatchRequest(), HttpMethod.PATCH, uri);
      ResponseEntity<List<Ride>> response = restTemplate.exchange(batchRequest, new ParameterizedTypeReference<List<Ride>>() {});
   }
}
