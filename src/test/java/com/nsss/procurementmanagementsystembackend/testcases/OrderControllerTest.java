package com.nsss.procurementmanagementsystembackend.testcases;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsss.procurementmanagementsystembackend.controller.OrderController;
import com.nsss.procurementmanagementsystembackend.model.*;
import com.nsss.procurementmanagementsystembackend.repository.*;
import com.nsss.procurementmanagementsystembackend.request.OrderRequest;
//import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.ZonedDateTime;
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MaterialRepository materialRepository;

    @MockBean
    SupplierRepository supplierRepository;

    @MockBean
    SiteRepository siteRepository;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    InvoiceRepository invoiceRepository;

    Material MATERIAL_1;
    Material MATERIAL_2;
    Material MATERIAL_3;

    OrderItem ITEM_1;
    OrderItem ITEM_2;
    OrderItem ITEM_3;

    Supplier SUPPLIER_1;
    Supplier SUPPLIER_2;
    Supplier SUPPLIER_3;

    Site SITE_1;
    Site SITE_2;
    Site SITE_3;

    Order ORDER_1;
    Order ORDER_2;
    Order ORDER_3;

    @BeforeEach
    public void init() {
        MATERIAL_1 = new Material("Cement", 1050, "available");
        MATERIAL_2 = new Material("Sand", 800, "available");
        MATERIAL_3 = new Material("Cable", 250.00, "unavailable");

        MATERIAL_1.setId("1");
        MATERIAL_2.setId("2");
        MATERIAL_3.setId("3");

        ITEM_1 = new OrderItem(MATERIAL_1, 9);
        ITEM_2 = new OrderItem(MATERIAL_2, 12);
        ITEM_3 = new OrderItem(MATERIAL_3, 16);

        SUPPLIER_1 = new Supplier( "Supplier A",
                "0773333333", "154/2/A Dam Street, Pettah", "Pettah", "available");
        SUPPLIER_2 = new Supplier("Supplier B",
                "0771111111", "247/A Flower Road, Kandy", "Kandy", "unavailable");
        SUPPLIER_3 = new Supplier( "Supplier C",
                "0772222222", "212/B Baker Street, London", "London", "available");

        SUPPLIER_1.setId("1");
        SUPPLIER_2.setId("2");
        SUPPLIER_3.setId("3");

        SITE_1 = new Site("Site A",
                "143/2 Flower Road, Kandy", "Kandy", "0774444444", "available");
        SITE_2 = new Site("Site B",
                "154/2/A Dam Street, Pettah", "Pettah", "0771111111", "unavailable");
        SITE_3 = new Site( "Site C",
                "212/B Baker Street, London", "London", "0772222222", "available");

        SITE_1.setId("1");
        SITE_2.setId("2");
        SITE_3.setId("3");

        ORDER_1 = new Order(ITEM_1, SUPPLIER_1, "Approved", 9450,
                "New Order", Date.from(ZonedDateTime.parse("2021-10-01T19:31:59.880+00:00").toInstant()),
                Date.from(ZonedDateTime.parse("2021-10-08T00:00:00.000+00:00").toInstant()), SITE_1);
        ORDER_2 = new Order( ITEM_2, SUPPLIER_2, "Pending", 9600,
                "2nd Order", Date.from(ZonedDateTime.parse("2021-10-01T20:06:15.465+00:00").toInstant()),
                Date.from(ZonedDateTime.parse("2021-10-15T00:00:00.000+00:00").toInstant()), SITE_2);
        ORDER_3 = new Order(ITEM_3, SUPPLIER_3, "Declined", 16800,
                "3rd Order", Date.from(ZonedDateTime.parse("2021-10-02T13:37:13.989+00:00").toInstant()),
                Date.from(ZonedDateTime.parse("2021-10-16T00:00:00.000+00:00").toInstant()), SITE_3);

        ORDER_1.setId("1");
        ORDER_2.setId("2");
        ORDER_3.setId("3");
    }

    @Test
    public void getAllOrdersTestCase() throws Exception {

        List<Order> orders = new ArrayList<>(Arrays.asList(ORDER_1, ORDER_2, ORDER_3));

        Mockito.when(orderRepository.findAll()).thenReturn(orders);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/access/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].item.material.name", is("Sand")))
                .andExpect(jsonPath("$[2].item.material.name", not("Cement")))
                .andExpect(jsonPath("$[1].supplier.name", is("Supplier B")))
                .andExpect(jsonPath("$[2].supplier.name", not("Supplier A")))
                .andExpect(jsonPath("$[1].site.name", is("Site B")))
                .andExpect(jsonPath("$[2].site.name", not("Site A")));
    }

    @Test
    public void updateOrderApprovedStatusTestCase() throws Exception {
        Order updatedOrder = ORDER_2;
        updatedOrder.setStatus("Approved");

        Mockito.when(orderRepository.findById(ORDER_2.getId())).thenReturn(Optional.of(ORDER_2));
        Mockito.when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/access/orders/approved/2")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void updateOrderApprovedStatusTestCase_InvalidId() throws Exception {
        Order updatedOrder = ORDER_2;
        updatedOrder.setStatus("Approved");

        Mockito.when(orderRepository.findById(ORDER_2.getId())).thenReturn(Optional.of(ORDER_2));
        Mockito.when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/access/orders/approved/404")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrderHoldStatusTestCase() throws Exception {
        Order updatedOrder = ORDER_2;
        updatedOrder.setStatus("Hold");

        Mockito.when(orderRepository.findById(ORDER_2.getId())).thenReturn(Optional.of(ORDER_2));
        Mockito.when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/access/orders/hold/2")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void updateOrderHoldStatusTestCase_InvalidId() throws Exception {
        Order updatedOrder = ORDER_2;
        updatedOrder.setStatus("Hold");

        Mockito.when(orderRepository.findById(ORDER_2.getId())).thenReturn(Optional.of(ORDER_2));
        Mockito.when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/access/orders/hold/404")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrderDeclinedStatusTestCase() throws Exception {
        Order updatedOrder = ORDER_2;
        updatedOrder.setStatus("Declined");

        Mockito.when(orderRepository.findById(ORDER_2.getId())).thenReturn(Optional.of(ORDER_2));
        Mockito.when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/access/orders/declined/2")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void updateOrderDeclinedStatusTestCase_InvalidId() throws Exception {
        Order updatedOrder = ORDER_2;
        updatedOrder.setStatus("Declined");

        Mockito.when(orderRepository.findById(ORDER_2.getId())).thenReturn(Optional.of(ORDER_2));
        Mockito.when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/access/orders/declined/404")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrderReferredStatusTestCase() throws Exception {
        Order updatedOrder = ORDER_2;
        updatedOrder.setStatus("Referred");

        Mockito.when(orderRepository.findById(ORDER_2.getId())).thenReturn(Optional.of(ORDER_2));
        Mockito.when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/access/orders/referred/2")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void updateOrderReferredStatusTestCase_InvalidId() throws Exception {
        Order updatedOrder = ORDER_2;
        updatedOrder.setStatus("Referred");

        Mockito.when(orderRepository.findById(ORDER_2.getId())).thenReturn(Optional.of(ORDER_2));
        Mockito.when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/access/orders/referred/404")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrderDeliveredStatusTestCase() throws Exception {
        Order updatedOrder = ORDER_2;
        updatedOrder.setStatus("Delivered");

        Mockito.when(orderRepository.findById(ORDER_2.getId())).thenReturn(Optional.of(ORDER_2));
        Mockito.when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/access/orders/delivered/2")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void updateOrderDeliveredStatusTestCase_InvalidId() throws Exception {
        Order updatedOrder = ORDER_2;
        updatedOrder.setStatus("Delivered");

        Mockito.when(orderRepository.findById(ORDER_2.getId())).thenReturn(Optional.of(ORDER_2));
        Mockito.when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/access/orders/delivered/404")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound());
    }

    @Disabled
    @Test
    public void getAllPendingOrdersTestCase() throws Exception {
        List<Order> orders = new ArrayList<>(Arrays.asList(ORDER_1, ORDER_2, ORDER_3));

        //Mockito.when(orderRepository.findAllByStatusEquals(orders.get())).thenReturn(orders);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/access/orders/pending")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].item.material.name", is("Sand")))
                .andExpect(jsonPath("$[0].item.material.name", not("Cement")))
                .andExpect(jsonPath("$[0].supplier.name", is("Supplier B")))
                .andExpect(jsonPath("$[0].supplier.name", not("Supplier A")))
                .andExpect(jsonPath("$[0].site.name", is("Site B")))
                .andExpect(jsonPath("$[0].site.name", not("Site A")));
    }

    @Disabled
    @Test
    public void addOrderTestCase() throws Exception {
        OrderRequest orderRequest = new OrderRequest("Cement", 9, "Supplier A", "New Order", "Site A", Date.from(ZonedDateTime.parse("2021-10-08T00:00:00.000+00:00").toInstant()));

        MATERIAL_1.setId("6156ea637420372fbcdc2822");

        OrderItem ITEM_1 = new OrderItem(MATERIAL_1, 9);

        SUPPLIER_1.setId("6156eaad7420372fbcdc2824");

        SITE_1.setId("6156eb8e7420372fbcdc2829");

        Order ORDER_1 = new Order(ITEM_1, SUPPLIER_1, "Approved", 9450,
                "New Order", Date.from(ZonedDateTime.parse("2021-10-01T19:31:59.880+00:00").toInstant()),
                Date.from(ZonedDateTime.parse("2021-10-08T00:00:00.000+00:00").toInstant()), SITE_1);
        ORDER_1.setId("6157622fb163b211d159d9bb");

        Mockito.when(orderRepository.save(ORDER_1)).thenReturn(ORDER_1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/access/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(orderRequest));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.item.material.name", is("Cement")))
                .andExpect(jsonPath("$.item.material.name", not("Sand")))
                .andExpect(jsonPath("$.supplier.name", is("Supplier A")))
                .andExpect(jsonPath("$.supplier.name", not("Supplier B")))
                .andExpect(jsonPath("$.site.name", is("Site A")))
                .andExpect(jsonPath("$.site.name", not("Site B")));
    }
}
