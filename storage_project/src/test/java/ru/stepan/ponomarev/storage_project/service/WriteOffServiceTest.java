package ru.stepan.ponomarev.storage_project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.stepan.ponomarev.storage_project.dto.WriteOffDto;
import ru.stepan.ponomarev.storage_project.model.*;
import ru.stepan.ponomarev.storage_project.repository.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class WriteOffServiceTest {

    @Autowired
    WriteOffService writeOffService;
    @Autowired
    DtoMapper dtoMapper;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private WriteOffRepository writeOffRepository;
    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private ShopRepository shopRepository;
    @MockBean
    private ProductsInfoRepository productsInfoRepository;

    WriteOff writeOff;
    Transaction transaction;
    Product product;
    Shop shop;
    ProductsInfo productsInfo;

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
        writeOff = WriteOff.builder()
                .id(1L)
                .isConfirmed(false)
                .transaction(transaction)
                .build();

        given(writeOffRepository.findById(0L)).willReturn(Optional.empty());
        given(writeOffRepository.findById(1L)).willReturn(Optional.of(writeOff));
        given(writeOffRepository.findAll()).willReturn(Collections.singletonList(writeOff));
        given(shopRepository.findById(any())).willReturn(Optional.of(shop));
        given(transactionRepository.save(any())).willReturn(transaction);
        given(writeOffRepository.save(any())).willReturn(writeOff);
        given(productsInfoRepository.findByProductIdAndShopIdAndAtStorage(anyLong(), anyLong(), anyBoolean())).willReturn(Optional.of(productsInfo));
        given(productsInfoRepository.save(any())).willReturn(productsInfo);
        given(productRepository.findById(any())).willReturn(Optional.of(product));
    }

    @Test
    public void getWriteOffsShouldReturnListOfWriteOffs () {
        assertEquals(ResponseEntity.ok(Collections.singletonList(dtoMapper.from(writeOff))),
                writeOffService.getWriteOffs());
    }

    @Test
    public void getWriteOffWithId0ShouldReturnNotFound () {
        assertEquals(ResponseEntity.notFound().build(),
                writeOffService.getWriteOff(0L));
    }

    @Test
    public void getWriteOffWithId1ShouldReturnWriteOffDto () {
        assertEquals(ResponseEntity.ok(dtoMapper.from(writeOff)),
                writeOffService.getWriteOff(1L));
    }

    @Test
    public void savingWriteOffShouldReturnObjectWithValidId() {
        WriteOffDto writeOffDto = WriteOffDto.builder()
                .shopId(null)
                .productInfoDtos(new ArrayList<>())
                .isConfirmed(false)
                .transactionId(1L)
                .id(null)
                .build();
        assertEquals(ResponseEntity.ok(dtoMapper.from(writeOff)),
                writeOffService.saveOrUpdateWriteOff(writeOffDto));
    }

    @Test
    public void confirmWriteOffShouldReturnMessageWithSuccess () {
        assertEquals(ResponseEntity.ok("Confirmed successfully"),
                writeOffService.confirmWriteOff(1L));
    }

    @Test
    public void confirmWriteOffWithId0ShouldReturnNotFound () {
        assertEquals(ResponseEntity.notFound().build(),
                writeOffService.confirmWriteOff(0L));
    }

    @Test
    public void deleteWriteOffWithId1ShouldReturnObjectWithNullId () {
        writeOff.setId(null);
        assertEquals(ResponseEntity.ok(dtoMapper.from(writeOff)),
                writeOffService.deleteWriteOff(1L));
    }

    @Test
    public void deleteWriteOffWithId0ShouldReturnNotFound () {
        assertEquals(ResponseEntity.notFound().build(),
                writeOffService.deleteWriteOff(0L));
    }
}
