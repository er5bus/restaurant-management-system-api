package com.er5bus.restaurant.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "TableEntity")
@Data
@Getter@Setter
@ToString(exclude = {"tickets"})
public class TableEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
  @Column(unique = true, nullable = false)
	private int number = 0;
  private int nbCovered;
  private String type;
  private double charge;
  private double extraCharge;
	
	@OneToMany(mappedBy = "table", cascade = CascadeType.REMOVE)
	List<TicketEntity> tickets;
}
