package com.example.springjpa.rest.animal;

import com.example.springjpa.rest.model.AnimalRequest;
import com.example.springjpa.rest.model.AnimalResponse;
import com.example.springjpa.service.AnimalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping("/animal")
    public void create(@RequestBody AnimalRequest animalRequest) {
        animalService.create(animalRequest);
    }

    @GetMapping("/animal/{animalId}")
    public AnimalResponse getById(@PathVariable Long animalId) {
        return animalService.getById(animalId);
    }

    @DeleteMapping("/animal/{animalId}")
    public void delete(@PathVariable Long animalId) {
        animalService.delete(animalId);
    }

    @PutMapping("/animal/{animalId}")
    public void update(@PathVariable Long animalId, @RequestBody AnimalRequest animalRequest) {
        animalService.update(animalId, animalRequest);
    }

    @GetMapping("/animal")
    public List<AnimalResponse> getAll() {
        return animalService.getAll();
    }
}
