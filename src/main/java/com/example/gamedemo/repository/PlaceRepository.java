package com.example.gamedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gamedemo.entity.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

	Place findPlaceByPosition(int totalDice);
}