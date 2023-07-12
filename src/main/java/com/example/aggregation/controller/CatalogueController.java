package com.example.aggregation.controller;

import com.example.aggregation.model.Catalogue;
import com.example.aggregation.model.Product;
import com.example.aggregation.service.ProductAggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CatalogueController {
	private final ProductAggregatorService service;

	/**
	 * "Get" request for getting all products after deduplication
	 * @return all products in JSON format
	 */
	@GetMapping("/api/v1/ecandy/catalogue")
	public Catalogue getCatalogue() {
		List<Product> productList = service.getProducts();
		return new Catalogue(productList);
	}
}
