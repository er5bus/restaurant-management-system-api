package com.er5bus.restaurant.api.rest;

import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.HashMap;
import java.lang.Double;

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
@RequestMapping(value = "/api/v1/stats")
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
  public TableEntity mostReservedTable () {
    return statsService.mostReservedTable();
  }

  @RequestMapping(value = "/most-reseved-table-by-client", method = RequestMethod.GET, produces = {"application/json"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "The most reserved table by client.", notes = "Return the most reserved table by client")
  public TableEntity mostReservedTableByClient (
      @ApiParam(value = "The client Id", required = true) @RequestParam(value="clientId", required=true) int clientId
      ) {
    return statsService.mostReservedTableByClient(clientId);
  }

  @RequestMapping(value = "/revenue-by-day-month-week", method = RequestMethod.GET, produces = {"application/json"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "The revenue by day month week.", notes = "")
  public HashMap<String, Object> revenueByDayMonthWeek (
      @ApiParam(value = "The day", required = true) @RequestParam(required=true) @Min(1) @Max(31) int day,
      @ApiParam(value = "The month", required = true) @RequestParam(required=true) @Min(1) @Max(12) int month,
      @ApiParam(value = "The week", required = true) @RequestParam(required=true) @Min(1) @Max(52) int week
      ) {
    HashMap<String, Object> map = new HashMap<>();
    map.put("day", day);
    map.put("month", month);
    map.put("week", week);
    map.put("revenue", statsService.revenueByDayMonthWeek(day, month, week));
    return map;
  }

  @RequestMapping(value = "/revenue-between-two-dates", method = RequestMethod.GET, produces = {"application/json"})
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "The revenue by year.", notes = "")
  public HashMap<String, Object> revenueBetweenTwoDates (
      @ApiParam(value = "The start date(dd-MM-yyyy)", required = true) @RequestParam(required=true) @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate startDate, 
      @ApiParam(value = "The end date (dd-MM-yyyy)", required = true) @RequestParam(required=true) @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate endDate
    ) {
    HashMap<String, Object> map = new HashMap<>();
    map.put("startDate", startDate);
    map.put("endDate", endDate);
    map.put("revenue", statsService.revenueByYear(startDate, endDate));
    return map;
  }
}
