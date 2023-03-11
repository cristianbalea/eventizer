package dao;

import connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterDAO {
    private String createInsertStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append("eventizer.user (username, email, phone, password, role) ");
        sb.append("VALUES (?, ?, ?, ?, ?);");
        return sb.toString();
    }

    public boolean insertUser(String username, String email, String phone, String password, String role) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createInsertStatement();
        try {

            connection = (Connection) ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setString(4, password);
            statement.setString(5, role);
            System.out.println(statement.toString());
            statement.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return false;
    }
}
