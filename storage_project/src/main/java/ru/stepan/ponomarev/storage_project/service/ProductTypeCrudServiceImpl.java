package ru.stepan.ponomarev.storage_project.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.stepan.ponomarev.storage_project.dto.ProductTypeDto;
import ru.stepan.ponomarev.storage_project.model.ProductType;
import ru.stepan.ponomarev.storage_project.repository.ProductTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Uses ProductTypeRepository bean
 */
@Service
public class ProductTypeCrudServiceImpl implements ProductTypeCrudService {

    private final ProductTypeRepository productTypeRepository;
    private final DtoMapper dtoMapper;

    public ProductTypeCrudServiceImpl(ProductTypeRepository productTypeRepository, DtoMapper dtoMapper) {
        this.productTypeRepository = productTypeRepository;
        this.dtoMapper = dtoMapper;
    }

    /**
     * Show all product types from repository
     * @return Response OK with list of ProductType objects
     */
    public ResponseEntity<List<ProductTypeDto>> showAllProductsTypes() {
        return ResponseEntity.ok(productTypeRepository.findAll()
                .stream().map(dtoMapper::from).collect(Collectors.toList()));
    }

    /**
     * Show product types by it's id from repository
     * @param id id of product types
     * @return Response OK with ProductType object if it's found, or Response NOT_FOUND if it isn't found
     */
    public ResponseEntity<ProductTypeDto> showProductTypeById(long id) {
        Optional<ProductType> productType = productTypeRepository.findById(id);
        return productType.map(a -> ResponseEntity.ok(dtoMapper.from(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Save new product types from request body
     * @param productType ProductType object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    public ResponseEntity<ProductTypeDto> addOrUpdateProductType(ProductTypeDto productType) {
        return ResponseEntity.ok(dtoMapper.from(productTypeRepository.save(dtoMapper.from(productType))));
    }

    /**
     * Delete product types from repository
     * @param id id of ProductType to delete
     * @return Response NOT_FOUND if there's no object with this id OR Response OK with deleted object with null id
     */
    public ResponseEntity<ProductTypeDto> delete (long id) {
        Optional<ProductType> productType = productTypeRepository.findById(id);
        if(productType.isPresent()) {
            productTypeRepository.delete(productType.get());
            productType.get().setId(null);
            return ResponseEntity.ok(dtoMapper.from(productType.get()));
        }
        return ResponseEntity.notFound().build();
    }
}
