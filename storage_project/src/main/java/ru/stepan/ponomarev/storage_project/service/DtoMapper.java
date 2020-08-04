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


    public DtoMapper(MetricTypeRepository metricTypeRepository, ProductTypeRepository productTypeRepository, InvoiceRepository invoiceRepository, TransactionRepository transactionRepository, WriteOffRepository writeOffRepository) {
        this.metricTypeRepository = metricTypeRepository;
        this.productTypeRepository = productTypeRepository;
        this.transactionRepository = transactionRepository;
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
        List<Long> ids = new ArrayList<>();
        for(TransactionProductsInfo t : invoice.getTransaction().getProductList()) {
            ids.add(t.getProduct().getId());
        }
        return InvoiceDto.builder()
                .id(invoice.getId())
                .isConfirmed(invoice.isConfirmed())
                .productInfoIds(ids)
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
        List<Long> ids = new ArrayList<>();
        for(TransactionProductsInfo t : writeOff.getTransaction().getProductList()) {
            ids.add(t.getProduct().getId());
        }
        return WriteOffDto.builder()
                .id(writeOff.getId())
                .isConfirmed(writeOff.isConfirmed())
                .productInfoIds(ids)
                .transactionId(writeOff.getTransaction().getId())
                .build();
    }
}
