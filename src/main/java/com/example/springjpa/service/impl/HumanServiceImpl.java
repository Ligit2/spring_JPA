package com.example.springjpa.service.impl;

import com.example.springjpa.persistance.animal.model.Animal;
import com.example.springjpa.rest.model.AnimalResponse;
import com.example.springjpa.service.AnimalService;
import org.springframework.stereotype.Service;
import com.example.springjpa.persistance.human.HumanRepository;
import com.example.springjpa.persistance.human.model.Human;
import com.example.springjpa.rest.model.HumanRequest;
import com.example.springjpa.rest.model.HumanResponse;
import com.example.springjpa.service.HumanService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HumanServiceImpl implements HumanService {
    private final HumanRepository humanRepository;
    private final AnimalService animalService;

    public HumanServiceImpl(HumanRepository humanRepository, AnimalService animalService) {
        this.humanRepository = humanRepository;
        this.animalService = animalService;
    }

    public void create(HumanRequest humanRequest) {
        Human human = new Human(humanRequest.getName(), humanRequest.getAge());
        humanRepository.save(human);
    }
    public Human getByIdHuman(Long humanId){
      return   humanRepository.getById(humanId);
    }

    public HumanResponse getById(Long humanId) {
        Optional<Human> byId = humanRepository.findById(humanId);
        if(byId.isPresent()) {
            Human human = byId.get();
            HumanResponse humanResponse = getHumanResponse(human);
            return humanResponse;
        }
        else{
            throw new EntityNotFoundException();
        }
    }

    private HumanResponse getHumanResponse(Human human) {
        HumanResponse humanResponse = new HumanResponse(human.getId(), human.getName(), human.getAge());
        if (!human.getAnimals().isEmpty()) {
            human.getAnimals().forEach(each -> humanResponse.
                    getAnimalIds().
                    add(each.getId()));
        }
        return humanResponse;
    }

    public void delete(Long humanId) {
        humanRepository.deleteById(humanId);
    }

    public List<HumanResponse> getAll() {
        List<Human> allHumans = humanRepository.findAll();
        List<HumanResponse> humanResponses = new ArrayList<>();
        allHumans.forEach(each->{
            HumanResponse humanResponse = getHumanResponse(each);
            humanResponses.add(humanResponse);
        });
        return humanResponses;
    }

    public void update(Long humanId, HumanRequest humanRequest) {
        if(humanRequest.getName()!=null){
            Human byId = humanRepository.getById(humanId);
            byId.setName(humanRequest.getName());
            humanRepository.save(byId);
        }
        if(humanRequest.getAge()!=0){
            Human byId = humanRepository.getById(humanId);
            byId.setAge(humanRequest.getAge());
            humanRepository.save(byId);
        }
        if(!humanRequest.getAnimalIds().isEmpty()){
            humanRequest.getAnimalIds().forEach(each->{
                updateAnimalByHumanId(each,humanId);
            });
        }
    }

    public void updateAnimalByHumanId(Long animalId, Long humanId){
        Animal animal = animalService.getByIdAnimal(animalId);
        Human human = getByIdHuman(humanId);
        animal.setHuman(human);
        humanRepository.save(human);
        animalService.save(animal);
    }

    public void assign(Long humanId, Long animalId) {
       updateAnimalByHumanId(animalId,humanId);
    }
}
