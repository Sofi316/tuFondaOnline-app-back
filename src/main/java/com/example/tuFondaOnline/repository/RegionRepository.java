package com.example.tuFondaOnline.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.tuFondaOnline.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> { 
    
}