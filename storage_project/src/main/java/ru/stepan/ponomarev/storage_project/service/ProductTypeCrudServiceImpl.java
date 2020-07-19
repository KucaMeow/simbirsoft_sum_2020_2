package ru.stepan.ponomarev.storage_project.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.stepan.ponomarev.storage_project.model.ProductType;
import ru.stepan.ponomarev.storage_project.repository.ProductTypeRepository;

import java.util.Optional;

/**
 * Uses ProductTypeRepository bean
 */
@Service
public class ProductTypeCrudServiceImpl implements ProductTypeCrudService {

    private final ProductTypeRepository productTypeRepository;

    public ProductTypeCrudServiceImpl(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    /**
     * Show all product types from repository
     * @return Response OK with list of ProductType objects
     */
    public ResponseEntity showAllProductsTypes() {
        return ResponseEntity.ok(productTypeRepository.findAll());
    }

    /**
     * Show product types by it's id from repository
     * @param id id of product types
     * @return Response OK with ProductType object if it's found, or Response NOT_FOUND if it isn't found
     */
    public ResponseEntity showProductTypeById(long id) {
        Optional<ProductType> productType = productTypeRepository.findById(id);
        return productType.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Save new product types from request body
     * @param productType ProductType object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    public ResponseEntity addOrUpdateProductType(ProductType productType) {
        return ResponseEntity.ok(productTypeRepository.save(productType));
    }

    /**
     * Delete product types from repository
     * @param id id of ProductType to delete
     * @return Response NOT_FOUND if there's no object with this id OR Response OK with deleted object with null id
     */
    public ResponseEntity delete (long id) {
        Optional<ProductType> productType = productTypeRepository.findById(id);
        if(productType.isPresent()) {
            productTypeRepository.delete(productType.get());
            productType.get().setId(null);
            return ResponseEntity.ok(productType.get());
        }
        return ResponseEntity.notFound().build();
    }
}
