package com.phasezero.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phasezero.catalog.dto.ProductDto;
import com.phasezero.catalog.dto.Response;
import com.phasezero.catalog.service.ProductService;
import com.phasezero.catalog.service.ValidationService;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService productService;

	@Autowired
	private ValidationService validationService;

	@PostMapping("/save")
	public ResponseEntity<?> addProduct(@RequestBody ProductDto productDto) {
		Response<?> responseCheck = validationService.chceckProductPayload(productDto);
		if (responseCheck.getResponseCode() == HttpStatus.OK.value()) {
			Response<?> response = productService.addProduct(productDto);
			return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
		}
		return new ResponseEntity<>(responseCheck, HttpStatus.valueOf(responseCheck.getResponseCode()));
	}

	@GetMapping("/get/all")
	public ResponseEntity<?> getAllProducts(@RequestParam(required = false) String category) {
		Response<?> response = productService.getAllProducts(category);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchProduct(@RequestParam String name) {
		Response<?> response = productService.searchByName(name);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}

	@GetMapping("/sorted/price")
	public ResponseEntity<?> sortByPrice() {
		Response<?> response = productService.sortByPriceAsc();
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}

	@GetMapping("/inventory/value")
	public ResponseEntity<?> inventoryValue() {
		Response<?> response = productService.totalInventoryValue();
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}

}
