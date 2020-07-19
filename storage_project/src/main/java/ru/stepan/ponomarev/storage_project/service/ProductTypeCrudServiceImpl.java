package ru.stepan.ponomarev.storage_project.service;

import org.springframework.stereotype.Service;
import ru.stepan.ponomarev.storage_project.model.ProductType;
import ru.stepan.ponomarev.storage_project.repository.ProductTypeRepository;

import java.util.List;
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
    public List<ProductType> showAllProductsTypes() {
        return productTypeRepository.findAll();
    }

    /**
     * Show product types by it's id from repository
     * @param id id of product types
     * @return Response OK with ProductType object if it's found, or Response NOT_FOUND if it isn't found
     */
    public ProductType showProductTypeById(long id) {
        Optional<ProductType> productType = productTypeRepository.findById(id);
        return productType.orElse(null);
    }

    /**
     * Save new product types from request body
     * @param productType ProductType object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    public ProductType addOrUpdateProductType(ProductType productType) {
        return productTypeRepository.save(productType);
    }

    /**
     * Delete product types from repository
     * @param id id of ProductType to delete
     * @return String message. Response ok and "Deleted product types with id *id*" or Response NOT_FOUND and "Can't find product types by id"
     */
    public boolean delete (long id) {
        Optional<ProductType> productType = productTypeRepository.findById(id);
        if(productType.isPresent()) {
            productTypeRepository.delete(productType.get());
            return true;
        }
        return false;
    }
}
