package main.java.com.epam.training.mapper;

import main.java.com.epam.training.entity.User;
import main.java.com.epam.training.entity.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User>{

    @Override
    public User map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(User.ID);
        String firstName = resultSet.getString(User.FIRST_NAME);
        String lastName = resultSet.getString(User.LAST_NAME);
        String login = resultSet.getString(User.LOGIN);
        String role = resultSet.getString(User.ROLE);
        UserRole userRole = UserRole.valueOf(role.toUpperCase());
        User user = new User(id, firstName, lastName, login, userRole);

        return user;
    }
}
