package com.er5bus.restaurant.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.validation.constraints.Past;

import org.modelmapper.ModelMapper;

import com.er5bus.restaurant.dto.ClientDTO;
import com.er5bus.restaurant.dto.DishDTO;

import com.er5bus.restaurant.models.ClientEntity;
import com.er5bus.restaurant.models.DishEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

  private static final ModelMapper mapper = new ModelMapper();

  @Past
	private LocalDate date;
  	
	private ClientDTO client;
	
	private List<DishDTO> dishs;
	
	public ClientEntity getClient() {
		return mapper.map(client, ClientEntity.class);
	}
	
	public List<DishEntity> getDishs(){
			return dishs.stream()
					.map(p -> mapper.map(p,DishEntity.class))
					.collect(Collectors.toList());
	}
}
