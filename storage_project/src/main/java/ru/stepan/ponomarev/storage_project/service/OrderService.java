package ru.stepan.ponomarev.storage_project.service;

import org.springframework.http.ResponseEntity;
import ru.stepan.ponomarev.storage_project.dto.OrderDto;

import java.util.List;

public interface OrderService {
    ResponseEntity<OrderDto> getOrder(Long id);
    ResponseEntity<List<OrderDto>> getOrders();
    ResponseEntity<OrderDto> saveOrUpdateOrder(OrderDto orderDto);
    ResponseEntity<String> changeOrderStatus(Long orderId, Long statusId);
    ResponseEntity<OrderDto> deleteOrder(Long id);

    ResponseEntity<OrderDto> processOrder(Long id);

    ResponseEntity<OrderDto> countOrder(Long orderId, Long shopId);

    ResponseEntity<OrderDto> cancelOrder(Long id);
}
