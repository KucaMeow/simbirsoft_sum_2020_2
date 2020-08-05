package ru.stepan.ponomarev.storage_project.service;

import org.springframework.stereotype.Service;
import ru.stepan.ponomarev.storage_project.dto.*;
import ru.stepan.ponomarev.storage_project.model.*;
import ru.stepan.ponomarev.storage_project.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class DtoMapper {

    private final MetricTypeRepository metricTypeRepository;
    private final ProductTypeRepository productTypeRepository;
    private final TransactionRepository transactionRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderStatusRepository orderStatusRepository;

    public DtoMapper(MetricTypeRepository metricTypeRepository, ProductTypeRepository productTypeRepository, InvoiceRepository invoiceRepository, TransactionRepository transactionRepository, WriteOffRepository writeOffRepository, ShopRepository shopRepository, ProductRepository productRepository, UserRepository userRepository, OrderStatusRepository orderStatusRepository) {
        this.metricTypeRepository = metricTypeRepository;
        this.productTypeRepository = productTypeRepository;
        this.transactionRepository = transactionRepository;
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    public Product from(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .cost(productDto.getCost())
                .metricType(metricTypeRepository.findById(productDto.getMetricTypeId()).orElse(null))
                .name(productDto.getName())
                .productType(productTypeRepository.findById(productDto.getProductTypeId()).orElse(null))
                .quantity(productDto.getQuantity())
                .build();
    }

    public ProductDto from(Product product) {
        return ProductDto.builder()
                .cost(product.getCost())
                .id(product.getId())
                .metricTypeId(product.getMetricType().getId())
                .name(product.getName())
                .productTypeId(product.getProductType().getId())
                .quantity(product.getQuantity())
                .build();
    }

    public MetricType from(MetricTypeDto metricTypeDto) {
        return MetricType.builder()
                .metric(metricTypeDto.getMetric())
                .id(metricTypeDto.getId())
                .build();
    }

    public MetricTypeDto from(MetricType metricType) {
        return MetricTypeDto.builder()
                .metric(metricType.getMetric())
                .id(metricType.getId())
                .build();
    }

    public ProductType from(ProductTypeDto productTypeDto) {
        return ProductType.builder()
                .name(productTypeDto.getName())
                .id(productTypeDto.getId())
                .build();
    }

    public ProductTypeDto from(ProductType productType) {
        return ProductTypeDto.builder()
                .name(productType.getName())
                .id(productType.getId())
                .build();
    }

    public Invoice from(InvoiceDto invoiceDto) {
        return Invoice.builder()
                .id(invoiceDto.getId())
                .isConfirmed(invoiceDto.isConfirmed())
                .transaction(transactionRepository.findById(invoiceDto.getTransactionId()).orElse(null))
                .build();
    }

    public InvoiceDto from(Invoice invoice) {
        List<ProductInfoDto> dtos = new ArrayList<>();
        for(TransactionProductsInfo t : invoice.getTransaction().getProductList()) {
            dtos.add(from(t));
        }
        return InvoiceDto.builder()
                .id(invoice.getId())
                .isConfirmed(invoice.isConfirmed())
                .productInfoDtos(dtos)
                .transactionId(invoice.getTransaction().getId())
                .build();
    }

    public WriteOff from(WriteOffDto writeOffDto) {
        return WriteOff.builder()
                .id(writeOffDto.getId())
                .isConfirmed(writeOffDto.isConfirmed())
                .transaction(transactionRepository.findById(writeOffDto.getTransactionId()).orElse(null))
                .build();
    }

    public WriteOffDto from(WriteOff writeOff) {
        List<ProductInfoDto> dtos = new ArrayList<>();
        for(TransactionProductsInfo t : writeOff.getTransaction().getProductList()) {
            dtos.add(from(t));
        }
        return WriteOffDto.builder()
                .id(writeOff.getId())
                .isConfirmed(writeOff.isConfirmed())
                .productInfoDtos(dtos)
                .transactionId(writeOff.getTransaction().getId())
                .build();
    }

    public ProductInfoDto from(ProductsInfo productsInfo) {
        return ProductInfoDto.builder()
                .id(productsInfo.getId())
                .atStorage(productsInfo.isAtStorage())
                .productId(productsInfo.getProduct().getId())
                .quantity(productsInfo.getQuantity())
                .shopId(productsInfo.getShop().getId())
                .build();
    }

    public ProductInfoDto from(TransactionProductsInfo productsInfo) {
        return ProductInfoDto.builder()
                .id(productsInfo.getId())
                .atStorage(productsInfo.isAtStorage())
                .productId(productsInfo.getProduct().getId())
                .quantity(productsInfo.getQuantity())
                .shopId(productsInfo.getShop().getId())
                .build();
    }

    public ProductsInfo from(ProductInfoDto productsInfoDto) {
        return ProductsInfo.builder()
                .shop(shopRepository.findById(productsInfoDto.getShopId()).orElse(null))
                .quantity(productsInfoDto.getQuantity())
                .product(productRepository.findById(productsInfoDto.getProductId()).orElse(null))
                .atStorage(productsInfoDto.isAtStorage())
                .id(productsInfoDto.getId())
                .build();
    }

    public Order from(OrderDto orderDto) {
        return Order.builder()
                .customer(userRepository.getOne(orderDto.getUserId()))
                .id(orderDto.getId())
                .orderStatus(orderStatusRepository.getOne(orderDto.getStatusId()))
                .transaction(transactionRepository.getOne(orderDto.getTransactionId()))
                .build();
    }

    public OrderDto from(Order order) {
        List<ProductInfoDto> dtos = new ArrayList<>();
        for(TransactionProductsInfo t : order.getTransaction().getProductList()) {
            dtos.add(from(t));
        }
        return OrderDto.builder()
                .id(order.getId())
                .productInfoDtos(dtos)
                .statusId(order.getOrderStatus().getId())
                .transactionId(order.getTransaction().getId())
                .userId(order.getCustomer().getId())
                .build();
    }
}
