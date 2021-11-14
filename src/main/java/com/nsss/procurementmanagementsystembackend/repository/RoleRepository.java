package com.nsss.procurementmanagementsystembackend.repository;

import com.nsss.procurementmanagementsystembackend.model.ERole;
import com.nsss.procurementmanagementsystembackend.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
