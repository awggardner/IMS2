package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAO implements Dao<Order> {

	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long orderId = resultSet.getLong("order_id");
		long itemId = resultSet.getLong("customer_id");		
		return new Order(orderId, itemId);
	}

	/**
	 * Reads all Orders from the database
	 * 
	 * @return A list of orders
	 */
	@Override
	public List<Order> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery
						("SELECT o.order_id AS order_id," 
						+ "c.id AS customer_id, c.first_name, c.surname,"
						+ "i.Item_id AS item_id, i.item_name, i.item_price"
						+ "FROM orders o "
						+ "JOIN customers c "
						+ "ON o.customer_id = c.id "
						+ "JOIN order_items oi "
						+ "ON o.order_id = oi.order_id"
						+ "JOIN items i "
						+ "ON oi.item_id = i.item_id"
						+ "ORDER BY order_id;");)
		{
			List<Order> orders = new ArrayList<>();
			while (resultSet.next()) {
				orders.add(modelFromResultSet(resultSet));
			}
			return orders;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	public Order readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery
						("SELECT o.order_id AS order_id," 
						+ "c.id AS customer_id, c.first_name, c.surname,"
						+ "i.Item_id AS item_id, i.item_name, i.item_price"
						+ "FROM orders o "
						+ "JOIN customers c "
						+ "ON o.customer_id = c.id "
						+ "JOIN order_items oi "
						+ "ON o.order_id = oi.order_id"
						+ "JOIN items i "
						+ "ON oi.item_id = i.item_id"
						+ "ORDER BY order_id DESC LIMIT 1;");) 
		{
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Creates an order in the database
	 * 
	 * @param order - takes in an order object. id will be ignored
	 */
	@Override
	public Order create(Order order) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement
				("INSERT INTO orders(customer_id) VALUES (?)");) {
			statement.setLong(1, order.getId());
			statement.executeUpdate();
			Long id = this.readLatestOrderId();
			order.setOrderId(id);
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	//allows to
	public Long readLatestOrderId() {
		Long OrderId = null;
		
		try { 
			Connection connection = DBUtils.getInstance().getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery
			("SELECT id FROM orders ORDER BY id DESC LIMIT 1");
			resultset.first();
			OrderId = resultset.getLong("id");
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		
		return OrderId;
	}

	@Override
	public Order read(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement
						("SELECT o.order_id AS order_id," 
						+ "c.id AS customer_id, c.first_name, c.surname,"
						+ "i.Item_id AS item_id, i.item_name, i.item_price"
						+ "FROM orders o "
						+ "JOIN customers c "
						+ "ON o.customer_id = c.id "
						+ "JOIN order_items oi "
						+ "ON o.order_id = oi.order_id"
						+ "JOIN items i "
						+ "ON oi.item_id = i.item_id"
						+ "ORDER BY order_id WHERE id = ?");) 
		{
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				return modelFromResultSet(resultSet);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Updates a order in the database
	 * 
	 * @param order - takes in a order object, the id field will be used to
	 *                 update that order in the database
	 * @return
	 */
	@Override
	public Order update(Order order) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("UPDATE orders SET first_name = ?, surname = ? WHERE id = ?");) {
			statement.setString(1, order.getFirstName());
			statement.setString(2, order.getSurname());
			statement.setLong(3, order.getId());
			statement.executeUpdate();
			return read(order.getId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Deletes a order in the database
	 * 
	 * @param id - id of the order
	 */
	@Override
	public int delete(long order_id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE order_id = ?");) {
			statement.setLong(1, order_id);
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

}
