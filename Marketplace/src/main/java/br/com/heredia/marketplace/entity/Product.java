package br.com.heredia.marketplace.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {

	public Product(String description, String type, Double price, Boolean imported) {
		this.description = description;
		this.type = type;
		this.price = price;
		this.imported = imported;
	}
	
	public Product() {
		super();
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO) 
    private Long id; 
     
    @Column(name="description", nullable=false)
     private String description; 
    
    @Column(name="type", nullable=false)
    private String type; 
    
    @Column(name="price", nullable=false)
    private Double price; 
    
    @Column(name="imported", nullable=false)
    private Boolean imported;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Boolean getImported() {
		return imported;
	}
	public void setImported(Boolean imported) {
		this.imported = imported;
	} 
}
