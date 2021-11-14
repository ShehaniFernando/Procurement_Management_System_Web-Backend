package com.nsss.procurementmanagementsystembackend.repository;

import com.nsss.procurementmanagementsystembackend.model.Material;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends MongoRepository<Material, String> {
    Optional<Material> findMaterialByName(String name);
    List<Material> findAllByName(String name);
}
