package com.er5bus.restaurant.api.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.er5bus.restaurant.models.DishEntity;
import com.er5bus.restaurant.dto.DishDTO;
import com.er5bus.restaurant.exception.DataFormatException;
import com.er5bus.restaurant.service.DishService;

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
@RequestMapping(value = "/api/v1/dishs")
@Api(tags = {"dishs"})
public class DishController extends AbstractRestHandler {

  @Autowired
  private DishService dishService;

  @RequestMapping(value = "", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create a dish resource.", notes = "Returns the URL of the new resource in the Location header.")
  public DishDTO createDish(@RequestBody DishDTO dish, HttpServletRequest request, HttpServletResponse response) {
    return this.dishService.createDish(dish);
  }

  @RequestMapping(value = "", method = RequestMethod.GET, produces = {"application/json"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a paginated list of all dishs.", notes = "The list is paginated.")
  @ResponseBody
  public Page<DishEntity> getAllDish(@ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page, @ApiParam(value = "Tha page size", required = true) @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size, HttpServletRequest request, HttpServletResponse response) {
    return this.dishService.getAllDishs(page, size);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a single dish.", notes = "You have to provide a valid dish ID.")
  @ResponseBody
  public DishEntity getDish(@ApiParam(value = "The ID of the dish.", required = true) @PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
    DishEntity dish = this.dishService.getDish(id);
    checkResourceFound(dish);
    return dish;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Update a dish resource.", notes = "You have to provide a valid dish ID in the URL and in the payload.")
  public void updateDish(@ApiParam(required = true) @PathVariable("id") Integer id, @RequestBody DishDTO dish, HttpServletRequest request, HttpServletResponse response) {
    //checkResourceFound(this.dishService.getDish(id));
    //if (id != dish.getId()) throw new DataFormatException("ID doesn't match!");
    this.dishService.updateDish(dish, id);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {"application/json", "application/xml"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Delete a dish resource.", notes = "You have to provide a valid dish ID in the URL. Once deleted the resource can not be recovered.")
  public void deleteDish(@ApiParam(value = "The ID of the existing dish resource.", required = true) @PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) {
    checkResourceFound(this.dishService.getDish(id));
    this.dishService.deleteDish(id);
  }
}
