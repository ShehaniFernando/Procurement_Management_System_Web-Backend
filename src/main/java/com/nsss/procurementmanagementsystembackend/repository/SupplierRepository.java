package com.nsss.procurementmanagementsystembackend.repository;

import com.nsss.procurementmanagementsystembackend.model.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends MongoRepository<Supplier, String> {
    Optional<Supplier> findSupplierByName(String name);
    List<Supplier> findAllByName(String name);
}
