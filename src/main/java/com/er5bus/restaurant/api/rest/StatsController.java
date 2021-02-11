package com.er5bus.restaurant.api.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.er5bus.restaurant.models.TableEntity;
import com.er5bus.restaurant.models.DishEntity;
import com.er5bus.restaurant.models.ClientEntity;

import com.er5bus.restaurant.exception.DataFormatException;
import com.er5bus.restaurant.service.StatsService;

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
@RequestMapping(value = "/restaurant/v1/stats")
@Api(tags = {"stats"})
public class StatsController extends AbstractRestHandler {

  @Autowired
  private StatsService statsService;

  @RequestMapping(value = "/most-selled-dish", method = RequestMethod.GET, produces = {"application/json"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "The most selled dish.", notes = "Return the most selled dishes")
  public DishEntity mostSelledDish () {
    return statsService.mostSelledDish();
  }

  @RequestMapping(value = "/most-loyal-client", method = RequestMethod.GET, produces = {"application/json"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "The most loyal.", notes = "Return the most loyal")
  public ClientEntity mostLoyalClient () {
    return statsService.mostLoyalClient();
  }

  @RequestMapping(value = "/most-reseved-table", method = RequestMethod.GET, produces = {"application/json"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "The most reserved table.", notes = "Return the most reserved table")
  public int mostReservedTable () {
    return statsService.mostReservedTable();
  }

  @RequestMapping(value = "/most-reseved-table-by-client", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "The most reserved table by client.", notes = "Return the most reserved table by client")
  public TableEntity mostReservedTableByClient () {
    return statsService.mostReservedTableByClient();
  }

  @RequestMapping(value = "/revenue-by-day-month-week", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "The revenue by day month week.", notes = "")
  public double revenueByDayMonthWeek () {
    return statsService.revenueByDayMonthWeek();
  }

  @RequestMapping(value = "/revenue-by-year", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "The revenue by year.", notes = "")
  public double revenueByYear () {
    return statsService.revenueByYear();
  }
}
