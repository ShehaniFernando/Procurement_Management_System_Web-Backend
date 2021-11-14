package com.nsss.procurementmanagementsystembackend.testcases;

import com.nsss.procurementmanagementsystembackend.model.Supplier;
import com.nsss.procurementmanagementsystembackend.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SupplierControllerTest {

    @Autowired
    private SupplierRepository supplierRepository;

    @Test
    @Order(1)
    public void addSupplierTestCase() {

        Supplier supplier = new Supplier("Supplier A", "0712300000", "Flower rd", "Kandy", "available");
        assertNotNull(supplierRepository.save(supplier));
    }

    @Test
    @Order(2)
    public void getAllSupplierTestCase() {

        List<Supplier> list = supplierRepository.findAll();
        assertThat(list).size().isGreaterThan(0);
    }
}
