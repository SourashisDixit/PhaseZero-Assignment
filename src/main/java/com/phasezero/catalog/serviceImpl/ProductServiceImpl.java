package com.phasezero.catalog.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.phasezero.catalog.dto.ProductDto;
import com.phasezero.catalog.dto.Response;
import com.phasezero.catalog.entity.Product;
import com.phasezero.catalog.repository.ProductRepository;
import com.phasezero.catalog.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Response<?> addProduct(ProductDto productDto) {
		try {
			if (productDto == null) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "please provide product details", null);
			}
			Optional<Product> productOptional = productRepository.findByPartNumber(productDto.getPartNumber());

			if (productOptional.isPresent()) {
				return new Response<>(HttpStatus.CONFLICT.value(), "Duplicate part number found", null);
			}

			Product product = new Product();
			product.setPartNumber(productDto.getPartNumber());
			product.setPartName(productDto.getPartName().toLowerCase().trim());
			product.setBrand(productDto.getBrand());
			product.setCategory(productDto.getCategory());
			product.setPrice(productDto.getPrice());
			product.setStock(productDto.getStock());
			product.setCreatedAt(new Date());
			productRepository.save(product);
			return new Response<>(HttpStatus.OK.value(), "product saved successfully", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Something went wrong", null);
		}

	}

	@Override
	public Response<?> getAllProducts(String category) {
		try {
			List<Product> products = new ArrayList<>();

			if (category == null || category.isBlank()) {
				products = productRepository.findAll();
			} else {
				products = productRepository.findAllByCategory(category);
			}

			if (products.isEmpty()) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "No products found", null);
			}

			List<ProductDto> productDtos = products.stream().map(Product::convertToDto).collect(Collectors.toList());

			return new Response<>(HttpStatus.OK.value(), "Products retrieved successfully", productDtos);

		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Something went wrong", null);
		}

	}

	@Override
	public Response<?> searchByName(String name) {
		try {
			if (name == null || name.isBlank()) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Please provide name to search", null);
			}

			List<Product> products = productRepository.findByPartNameContainingIgnoreCase(name.toLowerCase());

			if (products.isEmpty()) {
				return new Response<>(HttpStatus.NOT_FOUND.value(), "No products found for the given name", null);
			}

			List<ProductDto> productDtos = products.stream().map(Product::convertToDto).collect(Collectors.toList());

			return new Response<>(HttpStatus.OK.value(), "Products retrieved successfully", productDtos);

		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Something went wrong while searching products",
					null);
		}
	}

	@Override
	public Response<?> sortByPriceAsc() {
		try {
			List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));

			if (products.isEmpty()) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "No products found", null);
			}

			List<ProductDto> productDtos = products.stream().map(Product::convertToDto).collect(Collectors.toList());

			return new Response<>(HttpStatus.OK.value(), "Products sorted by price successfully", productDtos);

		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Something went wrong while sorting products", null);
		}
	}

	@Override
	public Response<?> totalInventoryValue() {
		try {
			List<Product> products = productRepository.findAll();

			if (products.isEmpty()) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(),
						"No products available to calculate inventory value", null);
			}

			double totalValue = products.stream().mapToDouble(p -> p.getPrice() * p.getStock()).sum();

			Map<String, Double> result = new HashMap<>();
			result.put("inventoryValue", totalValue);

			return new Response<>(HttpStatus.OK.value(), "Total inventory value calculated successfully", result);

		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST.value(),
					"Something went wrong while calculating inventory value", null);
		}
	}

}
