package com.er5bus.restaurant.service;

import java.util.Optional;

import com.er5bus.restaurant.exception.ResourceNotFoundException;
import com.er5bus.restaurant.models.ClientEntity;
import com.er5bus.restaurant.dto.ClientDTO;
import com.er5bus.restaurant.repositories.ClientRepository;

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
public class ClientService {

  private static final Logger log = LoggerFactory.getLogger(ClientService.class);

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private ModelMapper mapper;

  public ClientService() {
  }

  public ClientDTO createClient(ClientDTO clientInRequest) {
    ClientEntity client = mapper.map(clientInRequest, ClientEntity.class);
    ClientEntity clientInBase = clientRepository.save(client);
    return mapper.map(clientInBase, ClientDTO.class);
  }

  public ClientEntity getClient(int id) {
    return clientRepository.findById(id).map(client -> {
      return client;
    }).orElseThrow(() -> new ResourceNotFoundException("Client not found!"));
  }

  public void updateClient(ClientDTO clientInRequest, int id) {
    ClientEntity clientToEdit = mapper.map(clientInRequest, ClientEntity.class);
    clientRepository.findById(id).map(client -> {
      client.setFirstName(clientToEdit.getFirstName());
      client.setLastName(clientToEdit.getLastName());
      client.setDateOfBirth(clientToEdit.getDateOfBirth());
      client.setAddress(clientToEdit.getAddress());
      client.setPhone(clientToEdit.getPhone());
      return clientRepository.save(client);
    }).orElseThrow(() -> new ResourceNotFoundException("Client not found!"));
  }

  public void deleteClient(int id) {
    clientRepository.deleteById(id);
  }

  public Page<ClientEntity> getAllClients(Integer page, Integer size) {
    Page pageOfClients = clientRepository.findAll(new PageRequest(page, size));
    return pageOfClients;
  }
}
