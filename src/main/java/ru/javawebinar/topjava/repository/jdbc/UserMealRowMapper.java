package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.jdbc.core.RowMapper;
import ru.javawebinar.topjava.model.UserMeal;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMealRowMapper implements RowMapper<UserMeal> {

    @Override
    public UserMeal mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserMeal userMeal = new UserMeal();
        userMeal.setId(rs.getInt("id"));
        userMeal.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
        userMeal.setDescription(rs.getString("description"));
        userMeal.setCalories(rs.getInt("calories"));
        return userMeal;
    }
}
