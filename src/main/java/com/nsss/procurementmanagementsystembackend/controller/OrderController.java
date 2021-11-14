package com.nsss.procurementmanagementsystembackend.controller;

import com.nsss.procurementmanagementsystembackend.constant.Constants;
import com.nsss.procurementmanagementsystembackend.model.Material;
import com.nsss.procurementmanagementsystembackend.model.Order;
import com.nsss.procurementmanagementsystembackend.model.OrderItem;
import com.nsss.procurementmanagementsystembackend.repository.MaterialRepository;
import com.nsss.procurementmanagementsystembackend.repository.OrderRepository;
import com.nsss.procurementmanagementsystembackend.repository.SiteRepository;
import com.nsss.procurementmanagementsystembackend.repository.SupplierRepository;
import com.nsss.procurementmanagementsystembackend.request.OrderRequest;
import com.nsss.procurementmanagementsystembackend.response.MessageResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Constants.RequestMapping.REQUEST_MAPPING)
public class OrderController {
    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    SiteRepository siteRepository;

    @GetMapping(Constants.RequestMapping.ORDERS)
    public ResponseEntity<List<Order>> getAllOrders() {
        try {

            List<Order> orders = new ArrayList<>(orderRepository.findAll());

            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            logger.error("Client Side Exception", e);
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        } catch (HttpServerErrorException e) {
            logger.error("Server Side Exception", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("General Exception", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(Constants.RequestMapping.PENDING_ORDERS)
    public ResponseEntity<List<Order>> getAllPendingOrders() {
        try {
            List<Order> orders = new ArrayList<>(orderRepository.findAllByStatusEquals(Constants.Status.PENDING));

            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            logger.error("Client Side Exception", e);
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        } catch (HttpServerErrorException e) {
            logger.error("Server Side Exception", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("General Exception", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(Constants.RequestMapping.ORDERS)
    public ResponseEntity<?> addOrder(@Valid @RequestBody OrderRequest orderRequest){
        try {
            //getting the material object related to the name passed in the request
            Material material = materialRepository.findMaterialByName(orderRequest.getItem()).get();
            double quantity = orderRequest.getQuantity();

            OrderItem orderItem = new OrderItem(material, quantity);
            //calculating the total amount by multiplying the price of a material by the required quantity
            double total = material.getPrice()*quantity;

            Order order = new Order(
                    orderItem,
                    supplierRepository.findSupplierByName(orderRequest.getSupplier()).get(),
                    Constants.Status.PENDING,
                    total,
                    orderRequest.getComment(),
                    new Date(),
                    orderRequest.getDeliveryDate(),
                    siteRepository.findSiteByName(orderRequest.getSite()).get()
            );

            orderRepository.save(order);

            return ResponseEntity.ok(new MessageResponse(Constants.Message.ORDER_CREATED_SUCCESSFULLY));

        } catch (HttpClientErrorException e) {
            logger.error("Client Side Exception", e);
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        } catch (HttpServerErrorException e) {
            logger.error("Server Side Exception", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("General Exception", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(Constants.RequestMapping.APPROVED_ORDERS_ID)
    public ResponseEntity<Order> updateOrderApprovedStatus(@PathVariable(Constants.Common.ID) String id) {
        try {
            Optional<Order> orderData = orderRepository.findById(id);

            if(orderData.isPresent()) {
                Order _order = orderData.get();
                _order.setStatus(Constants.Status.APPROVED);

                return new ResponseEntity<>(orderRepository.save(_order), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (HttpClientErrorException e) {
            logger.error("Client Side Exception", e);
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        } catch (HttpServerErrorException e) {
            logger.error("Server Side Exception", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("General Exception", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(Constants.RequestMapping.HOLD_ORDERS_ID)
    public ResponseEntity<Order> updateOrderHoldStatus(@PathVariable(Constants.Common.ID) String id) {
        try {
            Optional<Order> orderData = orderRepository.findById(id);

            if(orderData.isPresent()) {
                Order _order = orderData.get();
                _order.setStatus(Constants.Status.HOLD);

                return new ResponseEntity<>(orderRepository.save(_order), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (HttpClientErrorException e) {
            logger.error("Client Side Exception", e);
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        } catch (HttpServerErrorException e) {
            logger.error("Server Side Exception", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("General Exception", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(Constants.RequestMapping.DECLINED_ORDERS_ID)
    public ResponseEntity<Order> updateOrderDeclinedStatus(@PathVariable(Constants.Common.ID) String id) {
        try {
            Optional<Order> orderData = orderRepository.findById(id);

            if(orderData.isPresent()) {
                Order _order = orderData.get();
                _order.setStatus(Constants.Status.DECLINED);

                return new ResponseEntity<>(orderRepository.save(_order), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (HttpClientErrorException e) {
            logger.error("Client Side Exception", e);
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        } catch (HttpServerErrorException e) {
            logger.error("Server Side Exception", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("General Exception", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(Constants.RequestMapping.REFERRED_ORDERS_ID)
    public ResponseEntity<Order> updateOrderReferredStatus(@PathVariable(Constants.Common.ID) String id) {
        try {
            Optional<Order> orderData = orderRepository.findById(id);

            if(orderData.isPresent()) {
                Order _order = orderData.get();
                _order.setStatus(Constants.Status.REFERRED);

                return new ResponseEntity<>(orderRepository.save(_order), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (HttpClientErrorException e) {
            logger.error("Client Side Exception", e);
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        } catch (HttpServerErrorException e) {
            logger.error("Server Side Exception", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("General Exception", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(Constants.RequestMapping.DELIVERED_ORDERS_ID)
    public ResponseEntity<Order> updateOrderDeliveredStatus(@PathVariable(Constants.Common.ID) String id) {
        try {
            Optional<Order> orderData = orderRepository.findById(id);

            if(orderData.isPresent()) {
                Order _order = orderData.get();
                _order.setStatus(Constants.Status.DELIVERED);

                return new ResponseEntity<>(orderRepository.save(_order), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (HttpClientErrorException e) {
            logger.error("Client Side Exception", e);
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        } catch (HttpServerErrorException e) {
            logger.error("Server Side Exception", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("General Exception", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}