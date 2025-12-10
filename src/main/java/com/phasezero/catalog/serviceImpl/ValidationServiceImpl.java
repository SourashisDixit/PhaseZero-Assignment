package com.phasezero.catalog.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.phasezero.catalog.dto.ProductDto;
import com.phasezero.catalog.dto.Response;
import com.phasezero.catalog.service.ValidationService;

@Service
public class ValidationServiceImpl implements ValidationService {

	@Override
	public Response<?> chceckProductPayload(ProductDto productDto) {
		if (productDto.getPartName() == null || productDto.getPartName().isEmpty()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Please provide the part name.", null);
		} else if (productDto.getPartNumber() == null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Please provide the part number.", null);
		} else if (productDto.getCategory() == null || productDto.getCategory().isEmpty()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Please provide the category.", null);
		} else if (productDto.getPrice() == null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Please provide the price.", null);
		} else if (productDto.getStock() == null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Please provide the stock.", null);
		} else if (productDto.getStock() < 0) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Stock should not be negative.", null);
		} else if (productDto.getPrice() < 0) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Price should not be negative.", null);
		} else if (productDto.getBrand() ==null || productDto.getBrand().isEmpty()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Please provide the brand.", null);
		}
		return new Response<>(HttpStatus.OK.value(), "Ok.", null);
	}

}
