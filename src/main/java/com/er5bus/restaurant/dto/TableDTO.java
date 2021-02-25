package com.er5bus.restaurant.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.validation.constraints.Past;

import org.modelmapper.ModelMapper;

import com.er5bus.restaurant.models.TicketEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableDTO {

  private static final ModelMapper mapper = new ModelMapper();

  @Positive
  private int nbCovered;

  @NotBlank
  @Size(max=50)
  private String type;

  @NotBlank
  @Positive
  private double extraCharge;

  private List<TicketDTO> tickets;

  public List<TicketEntity> getTickets() {
    return tickets.stream()
      .map(p -> mapper.map(p,TicketEntity.class))
      .collect(Collectors.toList());
  }
}
