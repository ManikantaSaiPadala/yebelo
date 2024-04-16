package com.example.gamedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gamedemo.entity.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

	String rollDie(Long gameId, Long playerId);
}