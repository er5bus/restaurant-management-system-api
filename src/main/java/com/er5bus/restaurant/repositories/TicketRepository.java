package com.er5bus.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.er5bus.restaurant.models.TicketEntity;

/**
 * Repository can be used to delegate CRUD operations against the data source: http://goo.gl/P1J8QH
 */
public interface TicketRepository extends JpaRepository<TicketEntity, Integer>{

}
