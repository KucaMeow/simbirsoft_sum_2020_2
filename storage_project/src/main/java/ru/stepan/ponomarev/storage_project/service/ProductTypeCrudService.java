package ru.stepan.ponomarev.storage_project.service;

import org.springframework.http.ResponseEntity;
import ru.stepan.ponomarev.storage_project.model.ProductType;

public interface ProductTypeCrudService {
    /**
     * Show all product types from repository
     * @return Response OK with list of ProductType objects
     */
    ResponseEntity showAllProductsTypes();

    /**
     * Show product types by it's id from repository
     * @param id id of product types
     * @return Response OK with ProductType object if it's found, or Response NOT_FOUND if it isn't found
     */
    ResponseEntity showProductTypeById(long id);

    /**
     * Save new product types from request body
     * @param productType ProductType object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    ResponseEntity addOrUpdateProductType(ProductType productType);

    /**
     * Delete product types from repository
     * @param id id of ProductType to delete
     * @return Response NOT_FOUND if there's no object with this id OR Response OK with deleted object with null id
     */
    ResponseEntity delete (long id);
}
