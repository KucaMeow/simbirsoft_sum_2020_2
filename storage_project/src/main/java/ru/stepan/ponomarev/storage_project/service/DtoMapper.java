package ru.stepan.ponomarev.storage_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepan.ponomarev.storage_project.dto.ProductDto;
import ru.stepan.ponomarev.storage_project.model.Product;
import ru.stepan.ponomarev.storage_project.repository.MetricTypeRepository;
import ru.stepan.ponomarev.storage_project.repository.ProductTypeRepository;

@Service
public class DtoMapper {

    private final MetricTypeRepository metricTypeRepository;
    private final ProductTypeRepository productTypeRepository;

    public DtoMapper(MetricTypeRepository metricTypeRepository, ProductTypeRepository productTypeRepository) {
        this.metricTypeRepository = metricTypeRepository;
        this.productTypeRepository = productTypeRepository;
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
}
