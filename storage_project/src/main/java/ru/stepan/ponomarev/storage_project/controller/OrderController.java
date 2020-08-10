package ru.stepan.ponomarev.storage_project.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.stepan.ponomarev.storage_project.dto.OrderDto;
import ru.stepan.ponomarev.storage_project.service.OrderService;

import java.util.List;

/**
 * Controller for orders management
 */
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Getting single order by it's id
     * @param id id order record
     * @return ResponseEntity OK with OrderDto or ResponseEntity NOT_FOUND
     */
    @ApiOperation(value = "Getting single order by it's id",
            produces = "ResponseEntity OK with OrderDto or ResponseEntity NOT_FOUND")
    @GetMapping("/order/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<OrderDto> getOrder (@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    /**
     * Getting all orders from database
     * @return ResponseEntity OK with List of OrderDto objects
     */
    @ApiOperation(value = "Getting all orderss from database",
            produces = "ResponseEntity OK with List of OrderDto objects")
    @GetMapping("/order")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<OrderDto>> getOrders () {
        return orderService.getOrders();
    }

    /**
     * Save or Update order from OrderDto
     * @param orderDto OrderDto object with info to save or update
     * @return OrderDto with new id and it's transaction id if save, or updated OrderDto of updated Order
     */
    @ApiOperation(value = "Save or Update invoice from OrderDto",
            produces = "OrderDto with new id and it's transaction id if save, or updated OrderDto of updated Order")
    @PutMapping("/order")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<OrderDto> saveOrUpdateOrder(@RequestBody OrderDto orderDto) {
        return orderService.saveOrUpdateOrder(orderDto);
    }

    /**
     * Changing staus of order
     * @param orderId id of Order to change staus
     * @param statusId id of Status to set it to order
     * @return Message of operation process
     */
    @ApiOperation(value = "Changing status of order",
            produces = "Message of operation process")
    @PostMapping("/order/{orderId}/status/{statusId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> changeOrderStatus (@PathVariable Long orderId, @PathVariable Long statusId) {
        return orderService.changeOrderStatus(orderId, statusId);
    }

    /**
     * Deleting order by id
     * @param id id of Order to delete
     * @return OrderDto object of deleted Order
     */
    @ApiOperation(value = "Deleting order by id",
            produces = "OrderDto object of deleted Order")
    @DeleteMapping("/order/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<OrderDto> deleteOrder (@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }

    /**
     * Process order by id. Makes transaction made from current shop
     * @param id Id of Order
     * @return Will return OrderDto after processing
     */
    @ApiOperation(value = "Process order by id. Makes transaction made from current shop",
            produces = "Will return OrderDto after processing")
    @PostMapping("/order/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<OrderDto> processOrder(@PathVariable Long id) {
        return orderService.processOrder(id);
    }

    /**
     * Process order by id. Makes transaction made from current shop
     * @param orderId Id of Order
     * @param shopId Id of Shop to make transaction from there
     * @return If it's possible to process order in current shop, will return OrderDto. If cant find order or shop by id, return NOT_FOUND. If impossible to process, will return error
     */
    @ApiOperation(value = "Process order by id. Makes transaction made from current shop",
            produces = "If it's possible to process order in current shop will return OrderDto, If cant find order or shop by id return NOT_FOUND, If impossible to process will return error")
    @PostMapping("/order/{orderId}/shop/{shopId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<OrderDto> countOrder(@PathVariable Long orderId, @PathVariable Long shopId) {
        return orderService.countOrder(orderId, shopId);
    }

    /**
     * Cancel order by id. Delete order with id. Before deleting return goods to storage or shop if it was processed
     * @param id Id of order
     * @return OrderDto object of deleted Order
     */
    @ApiOperation(value = "Cancel order by id. Delete order with id. Before deleting return goods to storage or shop if it was processed",
            produces = "OrderDto object of deleted Order")
    @PostMapping("/order/cancel/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<OrderDto> cancelOrder(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }
}
