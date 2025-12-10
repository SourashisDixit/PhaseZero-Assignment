package com.phasezero.catalog.service;

import com.phasezero.catalog.dto.ProductDto;
import com.phasezero.catalog.dto.Response;

public interface ProductService {

	Response<?> addProduct(ProductDto productDto);

	Response<?> getAllProducts(String category);

	Response<?> searchByName(String name);

	Response<?> sortByPriceAsc();

	Response<?> totalInventoryValue();

}
