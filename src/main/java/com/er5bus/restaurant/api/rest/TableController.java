package com.er5bus.restaurant.api.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.er5bus.restaurant.models.TableEntity;
import com.er5bus.restaurant.dto.TableDTO;
import com.er5bus.restaurant.exception.DataFormatException;
import com.er5bus.restaurant.service.TableService;

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
@RequestMapping(value = "/restaurant/v1/tables")
@Api(tags = {"tables"})
public class TableController extends AbstractRestHandler {

  @Autowired
  private TableService tableService;

  @RequestMapping(value = "", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create a table resource.", notes = "Returns the URL of the new resource in the Location header.")
  public TableDTO createTable(@RequestBody TableDTO table, HttpServletRequest request, HttpServletResponse response) {
    return this.tableService.createTable(table);
  }

  @RequestMapping(value = "", method = RequestMethod.GET, produces = {"application/json"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a paginated list of all tables.", notes = "The list is paginated.")
  @ResponseBody
  public Page<TableEntity> getAllTable(@ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page, @ApiParam(value = "Tha page size", required = true) @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size, HttpServletRequest request, HttpServletResponse response) {
    return this.tableService.getAllTables(page, size);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a single table.", notes = "You have to provide a valid table ID.")
  @ResponseBody
  public TableEntity getTable(@ApiParam(value = "The ID of the table.", required = true) @PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
    TableEntity table = this.tableService.getTable(id);
    checkResourceFound(table);
    return table;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Update a table resource.", notes = "You have to provide a valid table ID in the URL and in the payload.")
  public void updateTable(@ApiParam(required = true) @PathVariable("id") Integer id, @RequestBody TableDTO table, HttpServletRequest request, HttpServletResponse response) {
    //checkResourceFound(this.tableService.getTable(id));
    //if (id != table.getId()) throw new DataFormatException("ID doesn't match!");
    this.tableService.updateTable(table, id);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {"application/json", "application/xml"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Delete a table resource.", notes = "You have to provide a valid table ID in the URL. Once deleted the resource can not be recovered.")
  public void deleteTable(@ApiParam(value = "The ID of the existing table resource.", required = true) @PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) {
    checkResourceFound(this.tableService.getTable(id));
    this.tableService.deleteTable(id);
  }
}
