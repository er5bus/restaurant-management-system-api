package com.er5bus.restaurant.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.er5bus.restaurant.exception.ResourceNotFoundException;

import com.er5bus.restaurant.models.TableEntity;
import com.er5bus.restaurant.models.TicketEntity;
import com.er5bus.restaurant.models.ClientEntity;
import com.er5bus.restaurant.models.DishEntity;

import com.er5bus.restaurant.dto.TableDTO;
import com.er5bus.restaurant.repositories.TableRepository;
import com.er5bus.restaurant.repositories.DishRepository;
import com.er5bus.restaurant.repositories.ClientRepository;
import com.er5bus.restaurant.repositories.TicketRepository;

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
public class TableService {

  private static final Logger log = LoggerFactory.getLogger(TableService.class);

  @Autowired
  private TableRepository tableRepository;

  @Autowired
  private DishRepository dishRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private TicketRepository ticketRepository;

  @Autowired
  private ModelMapper mapper;

  public TableService() {
  }

  @Transactional
  public TableEntity createTable(TableDTO tableInRequest) {
    TableEntity table = mapper.map(tableInRequest, TableEntity.class);
    List<Double> chargeList = table.getTickets().stream()
      .flatMap(t -> t.getDishs().stream())
      .map(dish -> dish.getPrice())
      .collect(Collectors.toList());
    System.err.println(chargeList);
    table.setCharge(chargeList.stream().mapToDouble(d -> d).sum());

    TableEntity tableInBase = tableRepository.save(table);

    if (table.getTickets() == null) {
      throw new ResourceNotFoundException("This table has no tickets");
    }

    table.getTickets().forEach(ticket -> {
      if (ticket.getClient() == null) {
        throw new ResourceNotFoundException("This table has no client");
      }
      ClientEntity client = clientRepository
        .findByFirstNameAndLastName(ticket.getClient().getFirstName(), ticket.getClient().getLastName())
        .map(clientInDb -> clientInDb)
        .orElseGet(() -> clientRepository.save(ticket.getClient()));

      if (ticket.getDishs() == null){
        throw new ResourceNotFoundException("This table has no dishs");
      }

      List<DishEntity> dishs = ticket.getDishs().stream().map(dish -> {
        return dishRepository
          .findByName(dish.getName())
          .map(dishInDb -> dishInDb)
          .orElseGet(() -> dishRepository.save(dish));
      })
      .collect(Collectors.toList());

      System.err.println(dishs);
      //tableInBase  = tableRepository.save(table);
      ticket.setTable(tableInBase);
      ticket.setDishs(dishs);
      ticket.setClient(client);
      ticketRepository.save(ticket);
    });

    return tableInBase;
  }

  public TableEntity getTable(int id) {
    return tableRepository.findById(id).map(table -> {
      return table;
    }).orElseThrow(() -> new ResourceNotFoundException("Table not found!"));
  }

  public void updateTable(TableDTO tableInRequest, int id) {
    TableEntity tableToEdit = mapper.map(tableInRequest, TableEntity.class);
    tableRepository.findById(id).map(table -> {
      table.setNumber(tableToEdit.getNumber());
      table.setNbCovered(tableToEdit.getNbCovered());
      table.setType(tableToEdit.getType());
      table.setExtraCharge(tableToEdit.getExtraCharge());
      table.setTickets(tableToEdit.getTickets());
      return tableRepository.save(table);
    }).orElseThrow(() -> new ResourceNotFoundException("Table not found!"));
  }

  public void deleteTable(int id) {
    tableRepository.deleteById(id);
  }

  public Page<TableEntity> getAllTables(Integer page, Integer size) {
    Page<TableEntity> pageOfTables = tableRepository.findAll(new PageRequest(page, size));
    return pageOfTables;
  }
}
