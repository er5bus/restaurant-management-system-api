package com.er5bus.restaurant.api.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.er5bus.restaurant.models.ClientEntity;
import com.er5bus.restaurant.dto.ClientDTO;
import com.er5bus.restaurant.exception.DataFormatException;
import com.er5bus.restaurant.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/api/v1/clients")
@Api(tags = {"clients"})
public class ClientController extends AbstractRestHandler {

  @Autowired
  private ClientService clientService;

  @RequestMapping(value = "", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create a client resource.", notes = "Returns the URL of the new resource in the Location header.")
  public ClientDTO createClient(@RequestBody ClientDTO client, HttpServletRequest request, HttpServletResponse response) {
    return this.clientService.createClient(client);
  }

  @RequestMapping(value = "", method = RequestMethod.GET, produces = {"application/json"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a paginated list of all clients.", notes = "The list is paginated.")
  @ResponseBody
  public Page<ClientEntity> getAllClient(@ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page, @ApiParam(value = "Tha page size", required = true) @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size, HttpServletRequest request, HttpServletResponse response) {
    return this.clientService.getAllClients(page, size);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a single client.", notes = "You have to provide a valid client ID.")
  @ResponseBody
  public ClientEntity getClient(@ApiParam(value = "The ID of the client.", required = true) @PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ClientEntity client = this.clientService.getClient(id);
    checkResourceFound(client);
    return client;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Update a client resource.", notes = "You have to provide a valid client ID in the URL and in the payload.")
  public void updateClient(@ApiParam(required = true) @PathVariable("id") Integer id, @RequestBody ClientDTO client, HttpServletRequest request, HttpServletResponse response) {
    //checkResourceFound(this.clientService.getClient(id));
    //if (id != client.getId()) throw new DataFormatException("ID doesn't match!");
    this.clientService.updateClient(client, id);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {"application/json", "application/xml"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Delete a client resource.", notes = "You have to provide a valid client ID in the URL. Once deleted the resource can not be recovered.")
  public void deleteClient(@ApiParam(value = "The ID of the existing client resource.", required = true) @PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) {
    checkResourceFound(this.clientService.getClient(id));
    this.clientService.deleteClient(id);
  }
}
