package com.rollingstone.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class AbstractController implements ApplicationEventPublisherAware{	
	
	protected ApplicationEventPublisher eventPublisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.eventPublisher=applicationEventPublisher;		
	}

}
