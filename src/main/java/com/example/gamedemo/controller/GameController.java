package com.example.gamedemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gamedemo.entity.Game;
import com.example.gamedemo.entity.Place;
import com.example.gamedemo.entity.Player;
import com.example.gamedemo.repository.GameRepository;
import com.example.gamedemo.repository.PlaceRepository;
import com.example.gamedemo.repository.PlayerRepository;
import com.example.gamedemo.service.GameService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
  private GameRepository gameRepository;
    
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlaceRepository placeRepository;
    
    @PostMapping("/create-game")
    public ResponseEntity<String> createGame(@RequestBody Game payload) { 
       gameRepository.save(payload) ;
       return ResponseEntity.ok("Game Created Successfully");
    }

    @PostMapping("/roll-die/{gameId}/{playerId}")
    public ResponseEntity<String> rollDie(@PathVariable Long gameId, @PathVariable Long playerId) {
        String message = gameRepository.rollDie(gameId, playerId);
        return ResponseEntity.ok(message);
    }

    
    
    
    @PostMapping("/roll-die/{playerId}")
    public ResponseEntity<String> rollDie(@PathVariable Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));
        // Roll two dice
        int die1 = (int) (Math.random() * 6) + 1;
        int die2 = (int) (Math.random() * 6) + 1;
        int totalDice = die1 + die2;

        // Get place info based on the dice roll
        Place place = placeRepository.findPlaceByPosition(totalDice);
        if (place == null) {
            return ResponseEntity.ok("No place found for the dice roll");
        }

        if (!place.isOwned()) {
            // Auto purchase the place
            if (player.getCashBalance() >= place.getBuyPrice()) {
                player.setCashBalance(player.getCashBalance() - place.getBuyPrice());
                place.setOwned(true);
                playerRepository.save(player);
                placeRepository.save(place);
                return ResponseEntity.ok("Die rolled " + totalDice + " and landed on " + place.getName() + ", Unclaimed place and hence bought for $" + place.getBuyPrice() + ". Remaining balance is $" + player.getCashBalance());
            } else {
                return ResponseEntity.ok("Die rolled " + totalDice + " and landed on " + place.getName() + ", Unclaimed place but insufficient funds to purchase. Remaining balance is $" + player.getCashBalance());
            }
        } else {
            // Auto pay rent
            player.setCashBalance(player.getCashBalance() - place.getRent());
            Player owner = place.isOwned();
            owner.setCashBalance(owner.getCashBalance() + place.getRent());
            playerRepository.save(player);
            playerRepository.save(owner);
            return ResponseEntity.ok("Die rolled " + totalDice + " and landed on " + place.getName() + ", Paid rent $" + place.getRent() + " to " + owner.getName() + ". Remaining balance is $" + player.getCashBalance());
        }
    }

    @PostMapping("/purchase-place/{placeId}")
    public ResponseEntity<String> purchasePlace(@PathVariable Long placeId, @RequestParam Long playerId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place not found"));
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));
        if (place.isOwned()) {
            return ResponseEntity.ok("Place is already owned by another player");
        }
        if (player.getCashBalance() < place.getBuyPrice()) {
            return ResponseEntity.ok("Insufficient funds to purchase the place");
        }
        player.setCashBalance(player.getCashBalance() - place.getBuyPrice());
        place.setOwned(true);
      //  place.setOwned(player);
        playerRepository.save(player);
        placeRepository.save(place);
        return ResponseEntity.ok("Place purchased successfully");
    }

    @PostMapping("/cross-start/{playerId}")
    public ResponseEntity<String> crossStart(@PathVariable Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));
        player.setCashBalance(player.getCashBalance() + 200);
        playerRepository.save(player);
        return ResponseEntity.ok("Crossed 'Start'. Gained $200. Remaining balance is $" + player.getCashBalance());
    }
}
    // Other controller methods for buying places, paying rent, etc.

