package com.er5bus.restaurant.repositories;

import java.util.Optional;

import java.util.List;

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
  Optional<List<TableEntity>> findByNumber(int number);
  /*
  @Query("select t"
  + "from Table t "
  + "group by t.number")
    public TableEntity mostReservedTable();*/
}
