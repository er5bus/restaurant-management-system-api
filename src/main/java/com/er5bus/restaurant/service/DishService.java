package com.er5bus.restaurant.service;

import java.util.Optional;

import com.er5bus.restaurant.exception.ResourceNotFoundException;
import com.er5bus.restaurant.models.DishEntity;
import com.er5bus.restaurant.dto.DishDTO;
import com.er5bus.restaurant.repositories.DishRepository;

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
public class DishService {

  private static final Logger log = LoggerFactory.getLogger(DishService.class);

  @Autowired
  private DishRepository dishRepository;

  @Autowired
  private ModelMapper mapper;

  public DishService() {
  }

  public DishDTO createDish(DishDTO dishInRequest) {
    DishEntity dish = mapper.map(dishInRequest, DishEntity.class);
    DishEntity dishInBase = dishRepository.save(dish);
    return mapper.map(dishInBase, DishDTO.class);
  }

  public DishEntity getDish(int id) {
    return dishRepository.findById(id).map(dish -> {
      return dish;
    }).orElseThrow(() -> new ResourceNotFoundException("Dish not found!"));
  }

  public void updateDish(DishDTO dishInRequest, int id) {
    DishEntity dishToEdit = mapper.map(dishInRequest, DishEntity.class);
    dishRepository.findById(id).map(dish -> {
      dish.setName(dishToEdit.getName());
      dish.setType(dishToEdit.getType());
      dish.setPrice(dishToEdit.getPrice());
      return dishRepository.save(dish);
    }).orElseThrow(() -> new ResourceNotFoundException("Dish not found!"));
  }

  public void deleteDish(int id) {
    dishRepository.deleteById(id);
  }

  public Page<DishEntity> getAllDishs(Integer page, Integer size) {
    Page pageOfDishs = dishRepository.findAll(new PageRequest(page, size));
    return pageOfDishs;
  }
}
