package com.rollingstone.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventListener {

	@EventListener
	public void onApplicationEvent(ProductEvent productEvent) {
		System.out.println("Recieve product event: "+productEvent.getEventType());
		System.out.println("Recieve product : "+productEvent.getProduct().toString());
	}
}
