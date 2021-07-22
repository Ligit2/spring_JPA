package com.example.springjpa.service;

import com.example.springjpa.persistance.animal.model.Animal;
import com.example.springjpa.persistance.human.model.Human;
import com.example.springjpa.rest.model.AnimalRequest;
import com.example.springjpa.rest.model.AnimalResponse;

import java.util.List;
public interface AnimalService {

    void save(Animal animal);
     Animal getByIdAnimal(Long humanId);
    void create(AnimalRequest animalRequest);

    AnimalResponse getById(Long animalId);

    void delete(Long animalId);

    void update(Long animalId, AnimalRequest animalRequest);

    List<AnimalResponse> getAll();
}
