package com.example.gamedemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int buyPrice;
    private int rent;
    private boolean isOwned = false;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}
	public int getRent() {
		return rent;
	}
	public void setRent(int rent) {
		this.rent = rent;
	}
	public boolean isOwned() {
		return isOwned;
	}
	public void setOwned(boolean isOwned) {
		this.isOwned = isOwned;
	}
	public Place(Long id, String name, int buyPrice, int rent, boolean isOwned) {
		super();
		this.id = id;
		this.name = name;
		this.buyPrice = buyPrice;
		this.rent = rent;
		this.isOwned = isOwned;
	}
    
}