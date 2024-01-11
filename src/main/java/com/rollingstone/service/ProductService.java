package com.rollingstone.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.rollingstone.exceptions.ResourceNotFoundException;
import com.rollingstone.models.Product;
import com.rollingstone.repository.ProductRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public Product save(Product product) {
		return productRepository.save(product);
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
