package com.phasezero.catalog.service;

import com.phasezero.catalog.dto.ProductDto;
import com.phasezero.catalog.dto.Response;

public interface ValidationService {

	Response<?> chceckProductPayload(ProductDto productDto);

}
