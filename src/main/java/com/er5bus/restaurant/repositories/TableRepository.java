package com.er5bus.restaurant.repositories;

import java.util.Optional;

import java.util.List;
import java.util.Map;
import java.lang.Double;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;

import com.er5bus.restaurant.models.TableEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository can be used to delegate CRUD operations against the data source: http://goo.gl/P1J8QH
 */
public interface TableRepository extends PagingAndSortingRepository<TableEntity, Integer> {
  Page findAll(Pageable pageable);
  List<TableEntity> findAll();

  Optional<TableEntity> findByNbCovered(int nbCovered);

  @Query("select t from TableEntity t JOIN t.tickets ti JOIN ti.client c where c.id = ?1")
  List<TableEntity> findMostReservedTableByClient(int clientId);

  @Query("select sum(ti.price) from TableEntity t INNER JOIN t.tickets ti where day(ti.date) = ?1 and month(ti.date) = ?2")
  Optional<Double> findByDayAndMonthAndWeek(int day, int month, int week);

  @Query("select sum(ti.price) from TableEntity t INNER JOIN t.tickets ti where ti.date BETWEEN ?1 AND ?2")
  Optional<Double> findAllBetweenTwoDate(LocalDate startDate, LocalDate endDate);
}
