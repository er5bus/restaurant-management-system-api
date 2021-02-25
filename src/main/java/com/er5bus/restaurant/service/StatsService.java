package com.er5bus.restaurant.service;

import java.util.function.Function;

import java.util.Comparator;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import java.util.Optional;
import java.util.List;
import java.time.LocalDate;
import java.lang.Double;
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

  public TableEntity mostReservedTable () {
    Map<TableEntity, Double> map = tableRepository.findAll().stream()
      .collect(
          Collectors.groupingBy(
            t -> t,
            Collectors.summingDouble(t -> t.getTickets().stream().map(ti -> ti.getPrice()).mapToDouble(d -> d).sum())
          )
        )
      ;

    System.err.println("**************");
    System.err.println(map);
    System.err.println("**************");

    return map.entrySet()
      .stream()
      .max(Map.Entry.comparingByValue(Double::compareTo))
      .get()
      .getKey()
      ;
  }

  public TableEntity mostReservedTableByClient (int clientId) {
    Map<TableEntity, Double> map = tableRepository.findMostReservedTableByClient(clientId).stream()
      .collect(
          Collectors.groupingBy(
            t -> t,
            Collectors.summingDouble(t -> t.getTickets().stream().map(ti -> ti.getPrice()).mapToDouble(d -> d).sum())
            )
          )
      ;

    System.err.println("**************");
    System.err.println(map);
    System.err.println("**************");

    return map.entrySet()
      .stream()
      .max(Map.Entry.comparingByValue(Double::compareTo))
      .get()
      .getKey()
      ;
  }

  public Double revenueByDayMonthWeek (int day, int month, int week) {
    return tableRepository.findByDayAndMonthAndWeek(day, month, week).map(r -> r)
      .orElseThrow(() -> new ResourceNotFoundException("No revenue!"));
  }

  public Double revenueByYear (LocalDate startDate, LocalDate endDate) {
    return tableRepository.findAllBetweenTwoDate(startDate, endDate)
      .orElseThrow(() -> new ResourceNotFoundException("No revenue!"));
  }
}
