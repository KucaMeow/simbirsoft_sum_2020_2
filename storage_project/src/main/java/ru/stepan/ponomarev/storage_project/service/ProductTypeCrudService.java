package ru.stepan.ponomarev.storage_project.service;

import ru.stepan.ponomarev.storage_project.model.ProductType;

import java.util.List;

public interface ProductTypeCrudService {
    /**
     * Show all product types from repository
     * @return Response OK with list of ProductType objects
     */
    List<ProductType> showAllProductsTypes();

    /**
     * Show product types by it's id from repository
     * @param id id of product types
     * @return Response OK with ProductType object if it's found, or Response NOT_FOUND if it isn't found
     */
    ProductType showProductTypeById(long id);

    /**
     * Save new product types from request body
     * @param productType ProductType object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    ProductType addOrUpdateProductType(ProductType productType);

    /**
     * Delete product types from repository
     * @param id id of ProductType to delete
     * @return String message. Response ok and "Deleted product types with id *id*" or Response NOT_FOUND and "Can't find product types by id"
     */
    boolean delete (long id);
}
