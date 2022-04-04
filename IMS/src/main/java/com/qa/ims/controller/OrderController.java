package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

/**
 * Takes in order details for CRUD functionality
 *
 */
public class OrderController implements CrudController<Order> {

	public static final Logger LOGGER = LogManager.getLogger();

	private OrderDAO orderDAO;
	private Utils utils;


	public OrderController(OrderDAO orderDAO, Utils utils) {
		super();
		this.orderDAO = orderDAO;
		this.utils = utils;
	}

	/**
	 * Reads all orders to the logger
	 */
	@Override
	public List<Order> readAll() {
		List<Order> orders = orderDAO.readAll();
		for (Order order : orders) {
			LOGGER.info(order);
		}
		return orders;
	}

	/**
	 * Creates a order by taking in user input
	 */
	@Override
	public Order create() {
		LOGGER.info("Please enter your customer id to create an order, "
				+ "to add an item to your order please update your order once it has been created");
		Long customerId = utils.getLong();
		CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.read(customerId);
        Order order = orderDAO.create(new Order(customer));
        return order;
	}

	/**
	 * Updates an existing order by taking in user input
	 */
	@Override
	public Order update() {
        LOGGER.info("Please enter the id of the order you would like to update.");
        Long orderId = utils.getLong();
        LOGGER.info("Please enter the ID of the item you would like to update");
        Long itemId = utils.getLong();
        ItemDAO itemDAO = new ItemDAO();
        Item item = itemDAO.read(itemId);
        Order order = orderDAO.update(new Order(orderId, item));    

        LOGGER.info("Order updated");
        return order;
    }

	/**
	 * Deletes an existing order by the id of the order
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the order you would like to delete");
		Long itemId = utils.getLong();
		return orderDAO.delete(itemId);
	}

}