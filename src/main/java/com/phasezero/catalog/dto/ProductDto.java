package com.phasezero.catalog.dto;

public class ProductDto {

	private Long id;
	private String partNumber;

	private String partName;

	private String category;

	private Double price;

	private Integer stock;

	private String brand;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public ProductDto(Long id, String partNumber, String partName, String category, Double price, Integer stock,
			String brand) {
		super();
		this.id = id;
		this.partNumber = partNumber;
		this.partName = partName;
		this.category = category;
		this.price = price;
		this.stock = stock;
		this.brand = brand;
	}

	public ProductDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
