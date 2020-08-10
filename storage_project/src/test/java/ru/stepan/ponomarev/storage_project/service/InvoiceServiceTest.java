package ru.stepan.ponomarev.storage_project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.stepan.ponomarev.storage_project.dto.InvoiceDto;
import ru.stepan.ponomarev.storage_project.model.*;
import ru.stepan.ponomarev.storage_project.repository.*;

import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class InvoiceServiceTest {

    @Autowired
    InvoiceService invoiceService;
    @Autowired
    DtoMapper dtoMapper;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private InvoiceRepository invoiceRepository;
    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private ShopRepository shopRepository;
    @MockBean
    private ProductsInfoRepository productsInfoRepository;

    Invoice invoice;
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
        invoice = Invoice.builder()
                .id(1L)
                .isConfirmed(false)
                .transaction(transaction)
                .build();

        given(invoiceRepository.findById(0L)).willReturn(Optional.empty());
        given(invoiceRepository.findById(1L)).willReturn(Optional.of(invoice));
        given(invoiceRepository.findAll()).willReturn(Collections.singletonList(invoice));
        given(shopRepository.findById(any())).willReturn(Optional.of(shop));
        given(transactionRepository.save(any())).willReturn(transaction);
        given(invoiceRepository.save(any())).willReturn(invoice);
        given(productsInfoRepository.findByProductIdAndShopIdAndAtStorage(anyLong(), anyLong(), anyBoolean())).willReturn(Optional.of(productsInfo));
        given(productsInfoRepository.save(any())).willReturn(productsInfo);
        given(productRepository.findById(any())).willReturn(Optional.of(product));
    }

    @Test
    public void getInvoicesShouldReturnListOfInvoices () {
        assertEquals(ResponseEntity.ok(Collections.singletonList(dtoMapper.from(invoice))),
                invoiceService.getInvoices());
    }

    @Test
    public void getInvoiceWithId0ShouldReturnNotFound () {
        assertEquals(ResponseEntity.notFound().build(),
                invoiceService.getInvoice(0L));
    }

    @Test
    public void getInvoiceWithId1ShouldReturnInvoiceDto () {
        assertEquals(ResponseEntity.ok(dtoMapper.from(invoice)),
                invoiceService.getInvoice(1L));
    }

    @Test
    public void savingInvoiceShouldReturnObjectWithValidId() {
        InvoiceDto invoiceDto = InvoiceDto.builder()
                .shopId(null)
                .productInfoDtos(new ArrayList<>())
                .isConfirmed(false)
                .transactionId(1L)
                .id(null)
                .build();
        assertEquals(ResponseEntity.ok(dtoMapper.from(invoice)),
                invoiceService.saveOrUpdateInvoice(invoiceDto));
    }

    @Test
    public void confirmInvoiceShouldReturnMessageWithSuccess () {
        assertEquals(ResponseEntity.ok("Confirmed successfully"),
                invoiceService.confirmInvoice(1L));
    }

    @Test
    public void confirmInvoiceWithId0ShouldReturnNotFound () {
        assertEquals(ResponseEntity.notFound().build(),
                invoiceService.confirmInvoice(0L));
    }

    @Test
    public void deleteInvoiceWithId1ShouldReturnObjectWithNullId () {
        invoice.setId(null);
        assertEquals(ResponseEntity.ok(dtoMapper.from(invoice)),
                invoiceService.deleteInvoice(1L));
    }

    @Test
    public void deleteInvoiceWithId0ShouldReturnNotFound () {
        assertEquals(ResponseEntity.notFound().build(),
                invoiceService.deleteInvoice(0L));
    }
}
