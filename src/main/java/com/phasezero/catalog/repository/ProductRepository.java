package com.phasezero.catalog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.phasezero.catalog.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<Product> findByPartNumber(String partNumber);

	List<Product> findAllByCategory(String category);

	@Query(value = "SELECT * FROM products WHERE LOWER(part_name) LIKE CONCAT('%', ?1, '%')", nativeQuery = true)
	List<Product> findByPartNameContainingIgnoreCase(String name);

}
