package com.example.springjpa.persistance.human;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.springjpa.persistance.human.model.Human;
@Repository
public interface HumanRepository extends JpaRepository<Human,Long> {
}
