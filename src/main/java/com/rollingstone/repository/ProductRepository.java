package com.rollingstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rollingstone.models.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {	

}
