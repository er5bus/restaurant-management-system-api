package com.er5bus.restaurant.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Ticket")
@Data
@Getter@Setter
@ToString(exclude = {"table"})
public class TicketEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
  @Column(unique = true, nullable = false)
	private int identifier;

  private int nbCovered;

  private LocalDate date;

	private double price;

  @ManyToMany
  @JoinTable(name = "Composed")
	List<DishEntity> dishs;

  @ManyToOne
	private ClientEntity client;

  @ManyToOne
	@JsonIgnore
	private TableEntity table;
}
