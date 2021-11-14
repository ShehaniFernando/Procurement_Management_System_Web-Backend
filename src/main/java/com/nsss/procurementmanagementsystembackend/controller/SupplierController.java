package com.nsss.procurementmanagementsystembackend.controller;

import com.nsss.procurementmanagementsystembackend.constant.Constants;
import com.nsss.procurementmanagementsystembackend.model.Supplier;
import com.nsss.procurementmanagementsystembackend.repository.SupplierRepository;
import com.nsss.procurementmanagementsystembackend.response.MessageResponse;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Constants.RequestMapping.REQUEST_MAPPING)
public class SupplierController {

    public  static final Logger logger = LoggerFactory.getLogger(SupplierController.class);
    @Autowired
    SupplierRepository supplierRepository;

    /**
     * This method loads data into ArrayList of Supplier objects
     *
     * @throws NumberFormatException
     *                 -Thrown to indicate that the application has attempted to
     *                 convert a string to one of the numeric types
     */

    @GetMapping(Constants.RequestMapping.SUPPLIERS)
    public ResponseEntity<List<Supplier>> getAllSuppliers(@RequestParam(required = false) String supplierName) {
        try {
            List<Supplier> suppliers = new ArrayList<Supplier>();

            if (supplierName == null)
                supplierRepository.findAll().forEach(suppliers::add);
            else
                supplierRepository.findAllByName(supplierName).forEach(suppliers::add);

            if (suppliers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(suppliers, HttpStatus.OK);
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(Constants.RequestMapping.SUPPLIERS)
    public ResponseEntity<?> addSupplier(@Valid @RequestBody Supplier supplier){
        supplierRepository.save(supplier);

        return ResponseEntity.ok(new MessageResponse(Constants.Message.SUPPLIER_CREATED_SUCCESSFULLY));
    }
}
