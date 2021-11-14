package com.nsss.procurementmanagementsystembackend.controller;

import com.nsss.procurementmanagementsystembackend.constant.Constants;
import com.nsss.procurementmanagementsystembackend.model.Invoice;
import com.nsss.procurementmanagementsystembackend.model.Order;
import com.nsss.procurementmanagementsystembackend.repository.InvoiceRepository;
import com.nsss.procurementmanagementsystembackend.repository.OrderRepository;
import com.nsss.procurementmanagementsystembackend.request.InvoiceRequest;
import com.nsss.procurementmanagementsystembackend.response.MessageResponse;
import java.util.ArrayList;
import java.util.Date;
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
public class InvoiceController {

    public  static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    OrderRepository orderRepository;

    /**
     * This method loads data into ArrayList of Invoice objects
     *
     * @throws NumberFormatException
     *                 -Thrown to indicate that the application has attempted to
     *                 convert a string to one of the numeric types
     */

    @GetMapping(Constants.RequestMapping.INVOICES)
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        try {
            List<Invoice> invoices = new ArrayList<Invoice>();

            invoiceRepository.findAll().forEach(invoices::add);

            if (invoices.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(invoices, HttpStatus.OK);
        }catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(Constants.RequestMapping.INVOICES)
    public ResponseEntity<?> addInvoice(@Valid @RequestBody InvoiceRequest invoiceRequest){
        Order order = orderRepository.findById(invoiceRequest.getOrderId()).get();

        Invoice invoice = new Invoice(
                order,
                new Date(),
                invoiceRequest.getUser()
        );

        invoiceRepository.save(invoice);

        return ResponseEntity.ok(new MessageResponse(Constants.Message.INVOICE_CREATED_SUCCESSFULLY));
    }
}