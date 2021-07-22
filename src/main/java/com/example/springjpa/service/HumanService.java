package com.example.springjpa.service;

import com.example.springjpa.persistance.human.model.Human;
import org.springframework.stereotype.Service;
import com.example.springjpa.rest.model.HumanRequest;
import com.example.springjpa.rest.model.HumanResponse;

import java.util.List;
@Service
public interface HumanService {
    public Human getByIdHuman(Long humanId);

    void create(HumanRequest humanRequest) ;

    HumanResponse getById(Long humanId);

    void delete(Long humanId);

    List<HumanResponse> getAll() ;

    void update(Long humanId, HumanRequest humanRequest);
    public void updateAnimalByHumanId(Long animalId, Long humanId);

    void assign(Long humanId, Long animalId);
}
