package com.er5bus.restaurant.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.validation.constraints.Past;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
	
	@NotBlank
	@Size(max=50)
	private String firstName;

  @NotBlank
	@Size(max=50)
	private String lastName;
	
  @Past
	private LocalDate dateOfBirth;

  @NotBlank
	@Size(max=200)
	private String address;

  @NotBlank
	@Size(max=10)
	private String phone;
}
