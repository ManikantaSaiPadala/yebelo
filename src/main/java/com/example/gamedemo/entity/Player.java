package com.example.gamedemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Player(Long id, String name, int cashBalance) {
		super();
		this.id = id;
		this.name = name;
		this.cashBalance = cashBalance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCashBalance() {
		return cashBalance;
	}
	public void setCashBalance(int cashBalance) {
		this.cashBalance = cashBalance;
	}
	private String name;
    private int cashBalance = 1000;
    
}