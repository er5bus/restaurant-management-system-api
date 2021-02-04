package com.er5bus.restaurant.dto;

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
public class DishDTO {

  @NotBlank
	@Size(max=255)
	private String name;
  
  @NotBlank
	@Size(max=50)
  private String type;
  
  @NotBlank
  @Positive
  private double price;
}
