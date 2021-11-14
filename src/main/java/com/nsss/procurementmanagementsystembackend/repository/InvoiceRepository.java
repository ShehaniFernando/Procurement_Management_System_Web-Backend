package com.nsss.procurementmanagementsystembackend.repository;

import com.nsss.procurementmanagementsystembackend.model.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {
}