package com.gcu.cst323activity.repository;

import com.gcu.cst323activity.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
