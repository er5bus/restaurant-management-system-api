package com.er5bus.restaurant.service;

import java.util.function.Function;

import java.util.Comparator;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import java.util.Optional;
import java.util.List;
import java.util.Map;
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
    Map<DishEntity, Long> map = tableRepository.findAll().stream().
      flatMap(table -> table.getTickets().stream())
      .flatMap(ticket -> ticket.getDishs().stream())
      .collect(
          Collectors.groupingBy(
            d -> d,
            Collectors.counting()
            )
          )
      ;

    System.err.println("**************");
    System.err.println(map);
    System.err.println("**************");

    return map.entrySet()
      .stream()
      .max(Map.Entry.comparingByValue(Long::compareTo))
      .get()
      .getKey()
      ;
  }

  public ClientEntity mostLoyalClient () {
    Map<ClientEntity, Long> map = tableRepository.findAll().stream()
      .flatMap(table -> table.getTickets().stream())
      .collect(
          Collectors.groupingBy(
            t -> t.getClient(),
            Collectors.counting()
            )
          )
      ;

    System.err.println("**************");
    System.err.println(map);
    System.err.println("**************");

    return map.entrySet()
      .stream()
      .max(Map.Entry.comparingByValue(Long::compareTo))
      .get()
      .getKey()
      ;

  }

  public int mostReservedTable () {
    Map<Integer, Long> map = tableRepository.findAll().stream()
      .collect(
          Collectors.groupingBy(
            t -> t.getNumber(),
            Collectors.counting()
            )
          )
      ;

    System.err.println("**************");
    System.err.println(map);
    System.err.println("**************");

    return map.entrySet()
      .stream()
      .max(Map.Entry.comparingByValue(Long::compareTo))
      .get()
      .getKey()
      ;
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
