package dao;

import connection.ConnectionFactory;
import model.Event;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventDAO {
    private String createSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT eventName, description, participantsNumber, locationID, eventImg ");
        sb.append("FROM eventizer.event ");
        sb.append("WHERE eventID = ?;");
        return sb.toString();
    }

    private String createSelectAllQuery() {
        return "SELECT eventID from eventizer.event;";
    }

    public String getEventName(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();

        try {
            connection = (Connection) ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();

            return resultSet.getString(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public String getDescription(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();

        try {
            connection = (Connection) ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();

            return resultSet.getString(2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public int getParticipants(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();

        try {
            connection = (Connection) ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();

            return resultSet.getInt(3);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return -1;
    }

    public int getLocationID(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();

        try {
            connection = (Connection) ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();

            return resultSet.getInt(4);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return -1;
    }

    public String getEventImg(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();

        try {
            connection = (Connection) ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();

            return resultSet.getString(5);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public ArrayList<Integer> getEvents() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllQuery();
        ArrayList<Integer> ev = new ArrayList<>();

        try {
            connection = (Connection) ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                ev.add(resultSet.getInt(1));
            }
            return ev;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


    public Event getEvent(String eventID) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        String query = "SELECT * FROM eventizer.event WHERE eventID = ?;";
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(query);
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();

            if(rs.next()) {
                Event e = new Event(Integer.parseInt(eventID),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getString(6));
                return e;
            }

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(stmt);
            ConnectionFactory.close(con);
        }


        return null;
    }

    public boolean modifyEvent(Event e) {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "UPDATE eventizer.event " +
                "SET eventName = ?, description = ?, participantsNumber = ?, eventImg = ? " +
                "WHERE eventID = ?;";
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(query);
            stmt.setString(1, e.getEventName());
            stmt.setString(2, e.getDescription());
            stmt.setInt(3, e.getParticipantsNumber());
            stmt.setString(4, e.getEventImg());
            stmt.setInt(5, e.getEventID());
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            ConnectionFactory.close(stmt);
            ConnectionFactory.close(con);
        }
        return false;
    }

    public boolean createEvent(Event e) {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO eventizer.event (eventname, description, eventImg) VALUES " +
                "(?, ?, ?);";
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(query);
            stmt.setString(1, e.getEventName());
            stmt.setString(2, e.getDescription());
            stmt.setString(3, e.getEventImg());
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.close(stmt);
            ConnectionFactory.close(con);
        }

        return false;
    }


    public boolean createEventHelp(Event e, User u) {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO eventizer.eventorganization (eventID, userID) VALUES " +
                "(?, ?);";
        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, e.getEventID());
            stmt.setInt(2, u.getUserID());
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.close(stmt);
            ConnectionFactory.close(con);
        }
        return false;
    }
}

