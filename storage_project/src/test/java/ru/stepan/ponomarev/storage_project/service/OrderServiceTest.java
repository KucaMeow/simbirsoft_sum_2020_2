package ru.stepan.ponomarev.storage_project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.stepan.ponomarev.storage_project.dto.OrderDto;
import ru.stepan.ponomarev.storage_project.model.*;
import ru.stepan.ponomarev.storage_project.repository.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    DtoMapper dtoMapper;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private ShopRepository shopRepository;
    @MockBean
    private ProductsInfoRepository productsInfoRepository;
    @MockBean
    private OrderStatusRepository orderStatusRepository;

    Order order;
    Transaction transaction;
    Product product;
    Shop shop;
    ProductsInfo productsInfo;
    OrderStatus orderStatus;

    @BeforeEach
    public void initTest() {
        product = Product.builder()
                .quantity(1)
                .name("1")
                .productType(ProductType.builder().build())
                .metricType(MetricType.builder().build())
                .cost(1)
                .id(1L)
                .build();
        shop = Shop.builder()
                .address("1")
                .id(1L)
                .name("1")
                .products(Collections.singletonList(productsInfo))
                .build();
        productsInfo = ProductsInfo.builder()
                .id(1L)
                .atStorage(false)
                .product(product)
                .quantity(1)
                .shop(shop)
                .build();
        transaction = Transaction.builder()
                .productList(new ArrayList<>())
                .cost_sum(1)
                .date(new Date())
                .id(1L)
                .build();
        orderStatus = OrderStatus.builder()
                .id(1L)
                .status("1")
                .build();
        order = Order.builder()
                .id(1L)
                .transaction(transaction)
                .orderStatus(orderStatus)
                .customer(User.builder().build())
                .isProcessed(false)
                .build();

        given(orderRepository.findById(0L)).willReturn(Optional.empty());
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));
        given(orderRepository.findAll()).willReturn(Collections.singletonList(order));
        given(shopRepository.findById(1L)).willReturn(Optional.of(shop));
        given(transactionRepository.save(any())).willReturn(transaction);
        given(orderRepository.save(any())).willReturn(order);
        given(productsInfoRepository.findByProductIdAndShopIdAndAtStorage(anyLong(), anyLong(), anyBoolean())).willReturn(Optional.of(productsInfo));
        given(productsInfoRepository.save(any())).willReturn(productsInfo);
        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(orderStatusRepository.findById(1L)).willReturn(Optional.of(orderStatus));
    }

    @Test
    public void getOrdersShouldReturnListOfOrders () {
        assertEquals(ResponseEntity.ok(Collections.singletonList(dtoMapper.from(order))),
                orderService.getOrders());
    }

    @Test
    public void getOrderWithId0ShouldReturnNotFound () {
        assertEquals(ResponseEntity.notFound().build(),
                orderService.getOrder(0L));
    }

    @Test
    public void getOrderWithId1ShouldReturnOrderDto () {
        assertEquals(ResponseEntity.ok(dtoMapper.from(order)),
                orderService.getOrder(1L));
    }

    @Test
    public void savingOrderShouldReturnObjectWithValidId() {
        OrderDto orderDto = OrderDto.builder()
                .isProcessed(false)
                .productInfoDtos(new ArrayList<>())
                .statusId(1L)
                .userId(1L)
                .transactionId(1L)
                .id(null)
                .build();
        assertEquals(ResponseEntity.ok(dtoMapper.from(order)),
                orderService.saveOrUpdateOrder(orderDto));
    }

    @Test
    public void deleteOrderWithId1ShouldReturnObjectWithNullId () {
        order.setId(null);
        assertEquals(ResponseEntity.ok(dtoMapper.from(order)),
                orderService.deleteOrder(1L));
    }

    @Test
    public void deleteOrderWithId0ShouldReturnNotFound () {
        assertEquals(ResponseEntity.notFound().build(),
                orderService.deleteOrder(0L));
    }

    @Test
    public void processOrderWithId1ShouldReturnProcessedOrder() {
        Order processedOrder = Order.builder()
                .id(1L)
                .transaction(transaction)
                .orderStatus(orderStatus)
                .customer(User.builder().build())
                .isProcessed(true)
                .build();
        assertEquals(ResponseEntity.ok(dtoMapper.from(processedOrder)),
                orderService.processOrder(1L));
    }

    @Test
    public void processOrderWithId0ShouldReturnNotFound() {
        assertEquals(ResponseEntity.notFound().build(),
                orderService.processOrder(0L));
    }

    @Test
    public void countOrderWithInvalidIdsShouldReturnNotFound() {
        assertEquals(ResponseEntity.notFound().build(),
                orderService.countOrder(0L, 0L));
        assertEquals(ResponseEntity.notFound().build(),
                orderService.countOrder(1L, 0L));
        assertEquals(ResponseEntity.notFound().build(),
                orderService.countOrder(0L, 1L));
    }

    @Test
    public void countOrderWithId1ShouldReturnResponseOk() {
        assertEquals(ResponseEntity.ok(dtoMapper.from(order)),
                orderService.countOrder(1L, 1L));
    }

    @Test
    public void cancelOrderWithId0ShouldReturnNotFound() {
        assertEquals(ResponseEntity.notFound().build(),
                orderService.cancelOrder(0L));
    }

    @Test
    public void cancelOrderWithId1ShouldReturnDeletedObject() {
        order.setId(null);
        assertEquals(ResponseEntity.ok(dtoMapper.from(order)),
                orderService.cancelOrder(1L));
    }

    @Test
    public void changeOrderStatusWithValidIdShouldReturnOk () {
        assertEquals(ResponseEntity.ok("Status changed to 1 successfully"),
                orderService.changeOrderStatus(1L, 1L));
    }

    @Test
    public void changeOrderStatusWithInvalidIdsShouldReturnNotFound () {
        assertEquals(ResponseEntity.notFound().build(),
                orderService.changeOrderStatus(0L, 0L));
        assertEquals(ResponseEntity.notFound().build(),
                orderService.changeOrderStatus(0L, 1L));
    }
}
