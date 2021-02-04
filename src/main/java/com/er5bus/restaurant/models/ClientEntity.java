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
import javax.persistence.UniqueConstraint;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
  name = "Client",
  uniqueConstraints={
    @UniqueConstraint(columnNames = {"first_name", "last_name"})
  }
)
@Getter@Setter
@Data
@ToString(exclude = {"client"})
public class ClientEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 50, name = "first_name", nullable = true)
	private String firstName;

  @Column(length = 50, name = "last_name", nullable = true)
	private String lastName;

  private LocalDate dateOfBirth;

  @Column(length = 200, name = "address", nullable = true)
	private String address;

  @Column(length = 10, name = "phone", nullable = true)
	private String phone;
	
	@OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
  @JsonIgnore
	List<TicketEntity> tickets;
}
