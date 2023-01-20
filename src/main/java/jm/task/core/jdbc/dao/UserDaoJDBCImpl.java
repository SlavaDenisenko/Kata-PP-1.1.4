package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String TABLE_NAME = "users";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(45), lastName VARCHAR(45), age INT)";

        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(sqlCommand);
            connection.commit();

            System.out.println("Таблица успешно создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        Util util = new Util(); //в этом методе без создания экземпляра Util не проходят тесты
        String sqlCommand = "DROP TABLE IF EXISTS " + TABLE_NAME;

        try {
            Connection connection = util.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(sqlCommand);
            connection.commit();

            System.out.println("Таблица успешно удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlCommand = "INSERT INTO " + TABLE_NAME + " (name, lastName, age) VALUE (?, ?, ?)";

        try {
            Connection connection = Util.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlCommand);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            connection.commit();

            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sqlCommand = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

        try {
            Connection connection = Util.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlCommand);
            ps.setLong(1, id);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                list.add(user);
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String sqlCommand = "DELETE FROM " + TABLE_NAME;

        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
