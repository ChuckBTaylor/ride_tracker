package com.pluralsight.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {
   
   @Autowired
   private JdbcTemplate jdbcTemplate;
   
   @Override
   public Ride createRide(Ride ride) {
      String basicSql = "insert into ride (name, duration) values(?,?)";
      jdbcTemplate.update(basicSql, ride.getName(), ride.getDuration());
      return null;
   }
   
   @Override
   public Ride findRideById(String rideId) {
      return new Ride();
   }

	@Override
	public List<Ride> getRides() {
	   String queryForAllRides = "select * from ride";
	   RowMapper<Ride> rowMapper = new RowMapper<Ride>() {
         @Override
         public Ride mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ride ride = new Ride();
            ride.setId(rs.getInt("id"));
            ride.setDuration(rs.getInt("duration"));
            ride.setName(rs.getString("name"));
            return ride;
         }
	      
      };
      List<Ride> rides = jdbcTemplate.query(queryForAllRides, rowMapper);
      for(Ride ride : rides) {
         System.out.println("Ride " + ride.getId() +" pulled from db: " + ride.getName() + " - " + ride.getDuration());
      }
      return rides;
	}
	
}
