package dao;

import connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    private String createSelectQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("user.username,user.password ");
        sb.append("FROM eventizer.user ");
        sb.append("WHERE user.email = ? AND user.password = ?");
        return sb.toString();
    }

    private String createSelectIDQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("userID ");
        sb.append("FROM eventizer.user ");
        sb.append("WHERE email = ?;");
        return sb.toString();
    }

    public boolean isUser(String email,String password) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();
        try {

            connection = (Connection) ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            if (resultSet.next() != false)
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

    public int findID(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectIDQuery();
        try {

            connection = (Connection) ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            resultSet.next();

            return resultSet.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return -1;
    }

    private String createSelectRoleQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT role from eventizer.user ");
        sb.append("WHERE userID = ?;");
        return sb.toString();
    }

    public String getRole(String userID) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = createSelectRoleQuery();

        try {
           con = ConnectionFactory.getConnection();
           stmt = con.prepareStatement(query);
           stmt.setString(1, userID);
           rs = stmt.executeQuery();

           if (rs.next()) {
               return rs.getString(1);
           }

        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.close(stmt);
            ConnectionFactory.close(rs);
            ConnectionFactory.close(con);
        }

        return "";
    }
}
