package com.nsss.procurementmanagementsystembackend.repository;

import com.nsss.procurementmanagementsystembackend.model.Site;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SiteRepository extends MongoRepository<Site, String> {
    Optional<Site> findSiteByName(String name);
    List<Site> findAllByName(String name);
}
