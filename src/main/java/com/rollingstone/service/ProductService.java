package com.rollingstone.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rollingstone.exceptions.ResourceNotFoundException;
import com.rollingstone.feignClient.CategoryFeignClient;
import com.rollingstone.models.Category;
import com.rollingstone.models.Product;
import com.rollingstone.repository.ProductRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryFeignClient categoryFeignClient;
	
	@Autowired
	private ObjectMapper objectMapper;


	@HystrixCommand(fallbackMethod = "saveProductWithoutValidation")
	public Product save(Product product, Long parentCatId, Long childCatId) {
		Category parentCategory = null;
		Category childCategory = null;
		
		try {
			ResponseEntity<?> fetchParentCategory = categoryFeignClient.get(parentCatId);
			ResponseEntity<?> fetchChildCategory = categoryFeignClient.get(childCatId);
			if (fetchParentCategory.hasBody()) {
				parentCategory=parseJson(parentCategory, fetchParentCategory);
			}
			if (fetchChildCategory.hasBody()) {
				childCategory=parseJson(parentCategory, fetchChildCategory);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		product.setParentCategory(parentCategory);
		product.setCategory(childCategory);
		
		return productRepository.save(product);
	}
	
	public Product saveProductWithoutValidation(Product product) {
		System.out.println("hystrix circuit breaker enabled");
		return productRepository.save(product);
	}
	
	private Category parseJson(Category parentCategory, ResponseEntity<?> fetchParentCategory)
			throws JsonProcessingException, JsonMappingException {
		String json=fetchParentCategory.getBody().toString();
		
		Map<String, String> dataMap = parseStringToMap(json);
        
		parentCategory = convertMapToCategory(dataMap);
        
        System.out.println("id: " + parentCategory.getId());
        System.out.println("categoryName: " + parentCategory.getCategoryName());
        System.out.println("categoryDescription: " + parentCategory.getCategoryDescription());		
		
		return parentCategory;
	}

	private Category convertMapToCategory(Map<String, String> dataMap) {		
        return objectMapper.convertValue(dataMap, Category.class);
	}

	private Map<String, String> parseStringToMap(String json) {
		String[] keyValuePairs = json
                .replaceAll("[{}]", "")
                .split(", ");
        
        Map<String, String> dataMap = new HashMap<>();
       
        for (String pair : keyValuePairs) {
            String[] entry = pair.split("=");
            if (entry.length == 2) {
                dataMap.put(entry[0], entry[1]);
            }
        }
        return dataMap;
	}

	public Product getProductById(Long id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isEmpty()) {
			throw new ResourceNotFoundException("Product not found with id: " + id);
		}
		return product.get();
	}

	public Page<Product> getProductsByPage(Integer pageNum, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("productCode").descending());
		return productRepository.findAll(pageable);
	}

	public void updateProduct(Long id, Long parentCatId, Long childCatId, Product product) {		
		Optional<Product> existingProduct = productRepository.findById(id);
		if (existingProduct.isEmpty()) {
			throw new ResourceNotFoundException("Product not found with id: " + id);
		}
		existingProduct.get().setCanAutomotive(product.isCanAutomotive());
		existingProduct.get().setCanDisplay(product.isCanDisplay());
		existingProduct.get().setCategory(product.getCategory());
		existingProduct.get().setParentCategory(product.getParentCategory());
		existingProduct.get().setCanDeleted(product.isCanDeleted());
		existingProduct.get().setCanInternational(product.isCanInternational());
		existingProduct.get().setLongDescription(product.getLongDescription());
		existingProduct.get().setProductCode(product.getProductCode());
		existingProduct.get().setProductName(product.getProductName());
		existingProduct.get().setShortDescription(product.getShortDescription());
		
		productRepository.save(existingProduct.get());
	}
	
	public void delete(long id) {
		Optional<Product> existingProduct = productRepository.findById(id);
		if (existingProduct.isEmpty()) {
			throw new ResourceNotFoundException("Product not found with id: " + id);
		}
		productRepository.deleteById(id);
	}

}
