package com.example.gamedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gamedemo.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
