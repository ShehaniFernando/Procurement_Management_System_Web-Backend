package com.nsss.procurementmanagementsystembackend.testcases;

import com.nsss.procurementmanagementsystembackend.model.Invoice;
import com.nsss.procurementmanagementsystembackend.repository.InvoiceRepository;
import com.nsss.procurementmanagementsystembackend.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InvoiceControllerTest {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private OrderRepository orderRepository;


    @Test
    @Order(1)
    public void addInvoiceTestCase() {
        com.nsss.procurementmanagementsystembackend.model.Order order = orderRepository.findById("616011539778e453fd7d570d").get();

        Invoice invoice = new Invoice(
                order,
                new Date(),
                "user1"
        );
        assertNotNull(invoiceRepository.save(invoice));
    }

    @Test
    @Order(2)
    public void getAllInvoiceTestCase() {

        List<Invoice> list = invoiceRepository.findAll();
        assertThat(list).size().isGreaterThan(0);
    }
}