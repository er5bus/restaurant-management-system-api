package com.er5bus.restaurant.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.er5bus.restaurant.models.DishEntity;

/**
 * Repository can be used to delegate CRUD operations against the data source: http://goo.gl/P1J8QH
 */
public interface DishRepository extends JpaRepository<DishEntity, Integer>{
  Optional<DishEntity> findByName(String name);
}
