package com.rollingstone.feignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="category-microservice")
public interface CategoryFeignClient {

	@GetMapping("/category/{id}")
	public ResponseEntity<?> get(@PathVariable("id") Long id);
		
}
