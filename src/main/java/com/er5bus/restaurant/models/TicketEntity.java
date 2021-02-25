package com.er5bus.restaurant.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
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

  private int nbCovered;

  private LocalDate date;

	private double price;

  @ManyToMany
  @JoinTable(name = "composed")
	List<DishEntity> dishs;

  @ManyToOne
  @JoinColumn(name = "client_id")
	private ClientEntity client;

  @ManyToOne
  @JoinColumn(name = "table_id")
  @JsonIgnore
	private TableEntity table;
}
