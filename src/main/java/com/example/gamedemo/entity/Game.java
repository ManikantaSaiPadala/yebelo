package com.example.gamedemo.entity;

import java.util.List;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "game")
    private List<Player> players;
    @OneToMany(mappedBy = "game")
    private List<Place> places;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	public List<Place> getPlaces() {
		return places;
	}
	public void setPlaces(List<Place> places) {
		this.places = places;
	}
	public Game(Long id, List<Player> players, List<Place> places) {
		super();
		this.id = id;
		this.players = players;
		this.places = places;
	}
    
}