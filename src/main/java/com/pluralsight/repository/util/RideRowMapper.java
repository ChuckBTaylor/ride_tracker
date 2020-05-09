package com.pluralsight.repository.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.pluralsight.model.Ride;

@Component
public class RideRowMapper implements RowMapper<Ride> {

   @Override
   public Ride mapRow(ResultSet rs, int rowNum) throws SQLException {
      Ride ride = new Ride();
      ride.setId(rs.getInt("id"));
      ride.setDuration(rs.getInt("duration"));
      ride.setName(rs.getString("name"));
      return ride;
   }

}
