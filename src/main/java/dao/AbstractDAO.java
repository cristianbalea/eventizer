package dao;

import connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT");
        sb.append(" * ");
        sb.append("FROM eventizer.");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    private String createSelectQueryBy(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT");
        sb.append(" * ");
        sb.append("FROM eventizer.");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + "=?");
        return sb.toString();
    }

    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        StringBuilder values = new StringBuilder();
        sb.append("INSERT INTO eventizer.");
        sb.append(type.getSimpleName());
        sb.append("(");
        for (Field f : type.getDeclaredFields()) {
            sb.append(f.getName() + ",");
            values.append("?,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(") VALUES (");
        sb.append(values);
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }

    private String createUpdateQuery(String field) {

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE eventizer.");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for (Field f : type.getDeclaredFields()) {

            sb.append(f.getName());
            sb.append("=");
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" WHERE " + field + "=?");
        return sb.toString();
    }

    private String createDeleteQueryBy(String field) {

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM eventizer.");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + "=?");

        return sb.toString();
    }

    public List<T> findAll() throws IOException {

        List<T> lista = new ArrayList<T>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();
        try {
            connection = (Connection) ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            lista.addAll(createObjects(resultSet));
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return lista;
    }

    public T findById(int id) throws IOException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQueryBy((type.getDeclaredFields()[0].getName()));
        try {
            connection = (Connection) ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public int deleteById(int id) throws IOException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createDeleteQueryBy((type.getDeclaredFields()[0].getName()));
        try {

            connection = (Connection) ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            return 1;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteById " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }

    public T insert(T t) throws SQLException, IOException {
        Connection connection = ConnectionFactory.getConnection();
        String query = createInsertQuery();
        PreparedStatement insertStatement = null;
        int insertedId = 1;

        try {
            insertStatement = connection.prepareStatement(query);
            for (Field f : type.getDeclaredFields()) {
                f.setAccessible(true);
                Object obj = f.get(t);
                insertStatement.setString(insertedId, obj.toString());
                insertedId++;
            }
            insertStatement.executeUpdate();

            return t;
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public T update(T t, int id) throws SQLException, IOException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        String query = createUpdateQuery((type.getDeclaredFields()[0].getName()));
        int updatedId = 1;

        try {
            updateStatement = connection.prepareStatement(query);
            for (Field f : type.getDeclaredFields()) {
                f.setAccessible(true);
                Object obj = f.get(t);
                updateStatement.setString(updatedId, obj.toString());
                updatedId++;
            }
            updateStatement.setInt(updatedId, id);
            updateStatement.executeUpdate();

            return t;
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();

        try {
            while (resultSet.next()) {
                T instance = type.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return list;
    }
}
