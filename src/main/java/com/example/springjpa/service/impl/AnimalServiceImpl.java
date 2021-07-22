package com.example.springjpa.service.impl;

import com.example.springjpa.service.HumanService;
import org.springframework.stereotype.Service;
import com.example.springjpa.persistance.animal.AnimalRepository;
import com.example.springjpa.persistance.animal.model.Animal;
import com.example.springjpa.rest.model.AnimalRequest;
import com.example.springjpa.rest.model.AnimalResponse;
import com.example.springjpa.service.AnimalService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;
    private final HumanService humanService;

    public AnimalServiceImpl(AnimalRepository animalRepository, HumanService humanService) {
        this.animalRepository = animalRepository;
        this.humanService = humanService;
    }

    public void create(AnimalRequest animalRequest) {
        Animal animal = new Animal(animalRequest.getName(),animalRequest.getAge());
        animalRepository.save(animal);
    }

    public AnimalResponse getById(Long animalId) {
        Optional<Animal> byId = animalRepository.findById(animalId);
        if(byId.isPresent()) {
            Animal animal = byId.get();
            AnimalResponse animalResponse = new AnimalResponse(animal.getName(), animal.getAge());
            animalResponse.setId(animal.getId());
            return animalResponse;
        }
        else{
            throw new EntityNotFoundException();
        }
    }

    public void delete(Long animalId) {
        animalRepository.deleteById(animalId);
    }

    public void update(Long animalId, AnimalRequest animalRequest) {
        if(animalRequest.getName()!=null){
            Animal animal = animalRepository.getById(animalId);
            animal.setName(animalRequest.getName());
            animalRepository.save(animal);
        }
        if(animalRequest.getAge()!=0){
            Animal animal = animalRepository.getById(animalId);
            animal.setAge(animalRequest.getAge());
            animalRepository.save(animal);
        }
        if(animalRequest.getHumanId()!=null){
            humanService.updateAnimalByHumanId(animalId, animalRequest.getHumanId());
        }
    }

    @Override
    public void save(Animal animal) {
        animalRepository.save(animal);
    }

    public Animal getByIdAnimal(Long humanId){
        return   animalRepository.getById(humanId);
    }

    public List<AnimalResponse> getAll() {
        List<AnimalResponse> animalResponses = new ArrayList<>();
        List<Animal> allAnimals = animalRepository.findAll();
        allAnimals.forEach(each->{
            AnimalResponse animalResponse = new AnimalResponse(each.getId(), each.getName(), each.getAge());
            if(each.getHuman()!=null){
                animalResponse.setHumanId(each.getHuman().getId());
            }
            animalResponses.add(animalResponse);
        });
        return animalResponses;
    }
}
