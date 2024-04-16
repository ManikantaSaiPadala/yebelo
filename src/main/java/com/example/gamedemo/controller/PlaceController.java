package com.example.gamedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.gamedemo.entity.Place;
import com.example.gamedemo.repository.PlaceRepository;
import com.example.gamedemo.repository.PlayerRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
public class PlaceController {
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("/places/{placeId}")
    public ResponseEntity<Place> getPlaceById(@PathVariable Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place not found"));
        return ResponseEntity.ok(place);
    }

    @PostMapping("/places")
    public ResponseEntity<Place> createPlace(@RequestBody Place place) {
        Place savedPlace = placeRepository.save(place);
        return ResponseEntity.ok(savedPlace);
    }

    @PutMapping("/places/{placeId}")
    public ResponseEntity<Place> updatePlace(@PathVariable Long placeId, @RequestBody Place updatedPlace) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place not found"));
        place.setName(updatedPlace.getName());
        place.setBuyPrice(updatedPlace.getBuyPrice());
        place.setRent(updatedPlace.getRent());
        place.setOwned(updatedPlace.isOwned());
        Place savedPlace = placeRepository.save(place);
        return ResponseEntity.ok(savedPlace);
    }

    @DeleteMapping("/places/{placeId}")
    public ResponseEntity<String> deletePlace(@PathVariable Long placeId) {
        placeRepository.deleteById(placeId);
        return ResponseEntity.ok("Place deleted successfully");
    }
}
