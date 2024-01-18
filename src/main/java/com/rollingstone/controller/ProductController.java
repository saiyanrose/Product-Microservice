package com.rollingstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rollingstone.events.AbstractController;
import com.rollingstone.events.ProductEvent;
import com.rollingstone.exceptions.ResourceNotFoundException;
import com.rollingstone.models.Product;
import com.rollingstone.service.ProductService;

@RestController
@RequestMapping(value ="/ecom/product-service")
public class ProductController extends AbstractController {

	@Autowired
	private ProductService productService;


	@PostMapping("/product")
	public ResponseEntity<?> save(@RequestBody Product product, @RequestParam("parentCatId") Long parentCatId,
			@RequestParam("childCatId") Long childCatId) {	

		Product saveProduct = productService.save(product,parentCatId,childCatId);
		
		ProductEvent productEventCreated = new ProductEvent("Product is created", saveProduct);
		eventPublisher.publishEvent(productEventCreated);
		
		return ResponseEntity.ok().body("Product has been saved with ID:" + saveProduct.getId());
	}

	

	@GetMapping("/product/{id}")
	public ResponseEntity<?> get(@PathVariable("id") Long id) {
		Product product = new Product();
		try {
			product = productService.getProductById(id);
			ProductEvent productEventFetchById = new ProductEvent("Product is fechted with id: " + id, product);
			eventPublisher.publishEvent(productEventFetchById);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok().body(product);
	}

	@GetMapping("/product")
	public ResponseEntity<Page<Product>> getProductsByPage(
			@RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
		Page<Product> productList = productService.getProductsByPage(pageNum, pageSize);
		return ResponseEntity.ok().body(productList);
	}

	@PutMapping("/product/{id}/{parentCategoryId}/{childCategory}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @PathVariable("parentCategoryId") Long parentCatId,
			@PathVariable("childCategory") Long childCatId, @RequestBody Product product) {
		try {
			productService.updateProduct(id, parentCatId, childCatId, product);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok().body("Product has been updated successfully.");
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		try {
			productService.delete(id);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok().body("Product has been deleted successfully.");
	}
}