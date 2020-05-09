package com.pluralsight.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.util.RideRowMapper;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

   @Autowired
   private JdbcTemplate jdbcTemplate;

   @Autowired
   private RideRowMapper rideRowMapper;

   @Override
   public Ride createRide(Ride ride) {
      String basicSqlInsert = "insert into ride (name, duration) values(?,?)";

      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(new PreparedStatementCreator() {
         @Override
         public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement statement = con.prepareStatement(basicSqlInsert, new String[] { "id" });
            statement.setString(1, ride.getName());
            statement.setInt(2, ride.getDuration());
            return statement;
         }
      }, keyHolder);
      System.out.println("Ride entered into db");
      Number id = keyHolder.getKey();
      return findRideById(String.valueOf(id));
   }

   @Override
   public Ride findRideById(String rideId) {
      String findRideByIdSql = "select * from ride WHERE id = ?";
      Ride ride = jdbcTemplate.queryForObject(findRideByIdSql, rideRowMapper, rideId);
      return ride;
   }

   @Override
   public List<Ride> getRides() {
      String queryForAllRides = "select * from ride";
      List<Ride> rides = jdbcTemplate.query(queryForAllRides, rideRowMapper);
      for (Ride ride : rides) {
         System.out.println("Ride " + ride.getId() + " pulled from db: " + ride.getName() + " - " + ride.getDuration());
      }
      return rides;
   }

}
