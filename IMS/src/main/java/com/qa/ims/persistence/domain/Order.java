package com.qa.ims.persistence.domain;

import java.util.ArrayList;
import java.util.List;

public class Order  {



	private Long orderId;
	private Customer customer;
	private Item item;
	
	// create an arraylist to store items in an order
	private List<Item> orderitems = new ArrayList<Item>();

	public Order(Long orderId, long itemId) {
		this.orderId = orderId;
		this.setItem(itemId);
	}

	private void setItem(long itemId) {
		// TODO Auto-generated method stub
		
	}

	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Order(Customer newCustomer) {
		// TODO Auto-generated constructor stub
	}


	public Order(Long orderId, Item item) {
		// TODO Auto-generated constructor stub
	}

	// add item to orderitems list
	public void addOrderItem(Item item) {
		this.orderitems.add(item);
	}
	
	// remove item from orderitems
	public void removeOrderItem(Item item) {
		this.orderitems.remove(item);
	}
	
	

	
	public Long getOrderId() {
		return orderId;
	}
	
	

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Item> getOrderitems() {
		return orderitems;
	}

	public void setOrderitems(List<Item> orderitems) {
		this.orderitems = orderitems;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	// returns order ID, customer name, orderitems
	@Override
	public String toString() {
		return "Order id: " + orderId + " first name:" + this.customer.getFirstName() + 
				"last name " + this.customer.getSurname() + orderitems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		
		return true;
	}

	public String getFirstName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSurname() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getItemId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}




}



