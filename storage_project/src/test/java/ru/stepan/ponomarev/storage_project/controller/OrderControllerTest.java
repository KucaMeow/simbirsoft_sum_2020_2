package ru.stepan.ponomarev.storage_project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.stepan.ponomarev.storage_project.dto.OrderDto;
import ru.stepan.ponomarev.storage_project.service.OrderService;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OrderService orderService;

    OrderDto order;
    OrderDto savedOrder;
    OrderDto deletedOrder;
    OrderDto orderWithOtherStatus;

    @BeforeEach
    public void initTest() {
        order = OrderDto.builder()
                .transactionId(1L)
                .userId(1L)
                .statusId(1L)
                .productInfoDtos(new ArrayList<>())
                .isProcessed(true)
                .id(1L)
                .build();
        savedOrder = OrderDto.builder()
                .transactionId(1L)
                .userId(1L)
                .statusId(1L)
                .productInfoDtos(new ArrayList<>())
                .isProcessed(false)
                .id(1L)
                .build();
        deletedOrder = OrderDto.builder()
                .transactionId(1L)
                .userId(1L)
                .statusId(1L)
                .productInfoDtos(new ArrayList<>())
                .isProcessed(false)
                .id(null)
                .build();
        orderWithOtherStatus = OrderDto.builder()
                .transactionId(1L)
                .userId(1L)
                .statusId(2L)
                .productInfoDtos(new ArrayList<>())
                .isProcessed(true)
                .id(1L)
                .build();

        given(orderService.getOrder(0L)).willReturn(ResponseEntity.notFound().build());
        given(orderService.getOrder(1L)).willReturn(ResponseEntity.ok(order));
        given(orderService.getOrders()).willReturn(ResponseEntity.ok(Collections.singletonList(order)));
        given(orderService.saveOrUpdateOrder(any())).willReturn(ResponseEntity.ok(savedOrder));
        given(orderService.deleteOrder(0L)).willReturn(ResponseEntity.notFound().build());
        given(orderService.deleteOrder(1L)).willReturn(ResponseEntity.ok(deletedOrder));
        given(orderService.processOrder(0L)).willReturn(ResponseEntity.notFound().build());
        given(orderService.processOrder(1L)).willReturn(ResponseEntity.ok(order));
        given(orderService.cancelOrder(0L)).willReturn(ResponseEntity.notFound().build());
        given(orderService.cancelOrder(1L)).willReturn(ResponseEntity.ok(deletedOrder));
        given(orderService.countOrder(anyLong(), anyLong())).willReturn(ResponseEntity.notFound().build());
        given(orderService.countOrder(1L, 1L)).willReturn(ResponseEntity.ok(order));
        given(orderService.changeOrderStatus(anyLong(), anyLong())).willReturn(ResponseEntity.notFound().build());
        given(orderService.changeOrderStatus(1L, 2L)).willReturn(ResponseEntity.ok("changed"));
    }

    @Test
    public void getOrdersShouldReturnListOfOrders () throws Exception {
        mockMvc.perform(get("/order"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getOrderWithId0ShouldReturnNotFound () throws Exception {
        mockMvc.perform(get("/order/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getOrderWithId1ShouldReturnOrderDto () throws Exception {
        mockMvc.perform(get("/order/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(order)));
    }

    @Test
    public void savingOrderShouldReturnObjectWithValidId() throws Exception {
        OrderDto toSave = OrderDto.builder().build();
        mockMvc.perform(put("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toSave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.any(Number.class)));
    }

    @Test
    public void deleteOrderWithId1ShouldReturnObjectWithNullId () throws Exception {
        mockMvc.perform(delete("/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", nullValue()));
    }

    @Test
    public void deleteOrderWithId0ShouldReturnNotFound () throws Exception {
        mockMvc.perform(delete("/order/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void processOrderWithId1ShouldReturnProcessedOrder() throws Exception {
        mockMvc.perform(post("/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.processed", Matchers.is(true)));
    }

    @Test
    public void processOrderWithId0ShouldReturnNotFound() throws Exception {
        mockMvc.perform(post("/order/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void countOrderWithInvalidIdsShouldReturnNotFound() throws Exception {
        mockMvc.perform(post("/order/0/shop/0"))
                .andExpect(status().isNotFound());
        mockMvc.perform(post("/order/0/shop/1"))
                .andExpect(status().isNotFound());
        mockMvc.perform(post("/order/1/shop/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void countOrderWithId1ShouldReturnResponseOk() throws Exception {
        mockMvc.perform(post("/order/1/shop/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void cancelOrderWithId0ShouldReturnNotFound() throws Exception {
        mockMvc.perform(post("/order/cancel/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void cancelOrderWithId1ShouldReturnDeletedObject() throws Exception {
        mockMvc.perform(post("/order/cancel/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", nullValue()));
    }

    @Test
    public void changeOrderStatusWithValidIdShouldReturnOk () throws Exception {
        mockMvc.perform(post("/order/1/status/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void changeOrderStatusWithInvalidIdsShouldReturnNotFound () throws Exception {
        mockMvc.perform(post("/order/0/status/0"))
                .andExpect(status().isNotFound());
    }
}
