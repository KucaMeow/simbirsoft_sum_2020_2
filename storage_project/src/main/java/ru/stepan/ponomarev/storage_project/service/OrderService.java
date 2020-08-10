package ru.stepan.ponomarev.storage_project.service;

import org.springframework.http.ResponseEntity;
import ru.stepan.ponomarev.storage_project.dto.OrderDto;

import java.util.List;

public interface OrderService {

    /**
     * Getting single order by it's id
     * @param id id order record
     * @return ResponseEntity OK with OrderDto or ResponseEntity NOT_FOUND
     */
    ResponseEntity<OrderDto> getOrder(Long id);

    /**
     * Getting all orders from database
     * @return ResponseEntity OK with List of OrderDto objects
     */
    ResponseEntity<List<OrderDto>> getOrders();

    /**
     * Save or Update order from OrderDto
     * @param orderDto OrderDto object with info to save or update
     * @return OrderDto with new id and it's transaction id if save, or updated OrderDto of updated Order
     */
    ResponseEntity<OrderDto> saveOrUpdateOrder(OrderDto orderDto);

    /**
     * Changing staus of order
     * @param orderId id of Order to change staus
     * @param statusId id of Status to set it to order
     * @return Message of operation process
     */
    ResponseEntity<String> changeOrderStatus(Long orderId, Long statusId);

    /**
     * Deleting order by id
     * @param id id of Order to delete
     * @return OrderDto object of deleted Order
     */
    ResponseEntity<OrderDto> deleteOrder(Long id);

    /**
     * Process order by id. Makes transaction made from current shop
     * @param id Id of Order
     * @return Will return OrderDto after processing
     */
    ResponseEntity<OrderDto> processOrder(Long id);

    /**
     * Process order by id. Makes transaction made from current shop
     * @param orderId Id of Order
     * @param shopId Id of Shop to make transaction from there
     * @return If it's possible to process order in current shop, will return OrderDto. If cant find order or shop by id, return NOT_FOUND. If impossible to process, will return error
     */
    ResponseEntity<OrderDto> countOrder(Long orderId, Long shopId);

    /**
     * Cancel order by id. Delete order with id. Before deleting return goods to storage or shop if it was processed
     * @param id Id of order
     * @return OrderDto object of deleted Order
     */
    ResponseEntity<OrderDto> cancelOrder(Long id);
}
