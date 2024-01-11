package com.rollingstone.events;

import org.springframework.context.ApplicationEvent;

import com.rollingstone.models.Product;

@SuppressWarnings("serial")
public class ProductEvent extends ApplicationEvent{

	private String eventType;
	private Product product;	

	public ProductEvent(String eventType, Product product) {
		super(product);
		this.eventType = eventType;
		this.product = product;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "ProductEvent [eventType=" + eventType + ", product=" + product + "]";
	}

}
