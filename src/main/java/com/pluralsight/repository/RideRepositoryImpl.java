package com.pluralsight.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

//      SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
//      insert.setGeneratedKeyName("id");
//      Map<String, Object> data = new HashMap<>();
//      data.put("duration", ride.getDuration());
//      data.put("name", ride.getName());
//      List<String> columns = Arrays.asList("duration", "name");
//      insert.setTableName("ride");
//      insert.setColumnNames(columns);
//      Number id = insert.executeAndReturnKey(data);
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

   @Override
   public Ride updateRide(Ride ride) {
      String updateSql = "UPDATE ride SET name = ?, duration = ? WHERE id = ?";
      jdbcTemplate.update(updateSql, ride.getName(), ride.getDuration(), ride.getId());
      return ride;
   }

   @Override
   public void updateRides(List<Object[]> pairs) {
      String batchUpdateSql = "UPDATE ride SET ride_date = ? WHERE id = ?";
      int[] affectedRows = jdbcTemplate.batchUpdate(batchUpdateSql, pairs);
      for (int i : affectedRows) {
         System.out.println("row affected " + i);
      }
      
   }

   @Override
   public void deleteRideById(String rideId) {
      String deleteSql = "DELETE FROM ride WHERE id = :id";
      Map<String, Object> params = new HashMap<>();
      params.put("id", rideId);
      NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
      namedTemplate.update(deleteSql, params);
   }

}
