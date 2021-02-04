package com.er5bus.restaurant.service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import com.er5bus.restaurant.exception.ResourceNotFoundException;

import com.er5bus.restaurant.models.TableEntity;
import com.er5bus.restaurant.models.DishEntity;
import com.er5bus.restaurant.models.ClientEntity;

import com.er5bus.restaurant.repositories.ClientRepository;
import com.er5bus.restaurant.repositories.DishRepository;
import com.er5bus.restaurant.repositories.TableRepository;

import com.er5bus.restaurant.dto.DishDTO;
import com.er5bus.restaurant.dto.ClientDTO;
import com.er5bus.restaurant.dto.TableDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class StatsService {

  private static final Logger log = LoggerFactory.getLogger(TableService.class);

  public StatsService() {
  }
  
  @Autowired
  private TableRepository tableRepository;

  @Autowired
  private DishRepository dishRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private ModelMapper mapper;


  public DishEntity mostSelledDish () {
    return dishRepository.findById(1).map(dish -> dish)
      .orElseThrow(() -> new ResourceNotFoundException("Dishs not found!"));
  }

  public ClientEntity mostLoyalClient () {
    return clientRepository.findById(1).map(client -> client)
      .orElseThrow(() -> new ResourceNotFoundException("Client not found!"));
  }

  public TableEntity mostReservedTable () {
    return tableRepository.findById(1).map(table -> table)
      .orElseThrow(() -> new ResourceNotFoundException("Tables not found!"));
  }

  public TableEntity mostReservedTableByClient () {
    return tableRepository.findById(1).map(table -> table)
      .orElseThrow(() -> new ResourceNotFoundException("Tables not found!"));
  }

  public double revenueByDayMonthWeek () {
    //List<TableEntity> result = tableRepository.findAll();
    //return result.stream().map(table -> table.getCharge()).mapToDouble(f -> f.doubleValue()).sum().orElse(0);
    return 0;
  }

  public double revenueByYear () {
    //List<TableEntity> result = tableRepository.findAll();
    //return result.stream().map(table -> table.getCharge()).mapToDouble(f -> f.doubleValue()).sum().orElse(0);
    return 0;
  }
}
