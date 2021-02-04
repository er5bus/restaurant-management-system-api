package com.er5bus.restaurant.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinTable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Dish")
@Data
@Getter@Setter
@ToString(exclude = {"tickets"})
public class DishEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
  private String type;
  private double price;
	
  @ManyToMany(mappedBy = "dishs", cascade = CascadeType.REMOVE)
  @JsonIgnore
	List<TicketEntity> tickets;
}
