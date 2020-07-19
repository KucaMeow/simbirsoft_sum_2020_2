package ru.stepan.ponomarev.storage_project.service;

import org.springframework.http.ResponseEntity;
import ru.stepan.ponomarev.storage_project.dto.ProductDto;

public interface ProductCrudService {
    /**
     * Show all products from repository
     * @return Response OK with list of ProductDtos objects
     */
    ResponseEntity showAllProducts();

    /**
     * Show product by it's id from repository
     * @param id id of product
     * @return Response OK with ProductDto object if it's found, or Response NOT_FOUND if it isn't found
     */
    ResponseEntity showProductById(long id);

    /**
     * Show all products from repository
     * @return Response OK with list of Product objects
     */
    ResponseEntity showAllProductsWithoutMapping();

    /**
     * Show product by it's id from repository
     * @param id id of product
     * @return Response OK with Product object if it's found, or Response NOT_FOUND if it isn't found
     */
    ResponseEntity showProductByIdWithoutMapping(long id);

    /**
     * Save new product from request body
     * @param productDto ProductDto object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    ResponseEntity addOrUpdateProduct(ProductDto productDto);

    /**
     * Delete product from repository
     * @param id id of Product to delete
     * @return Response NOT_FOUND if there's no object with this id OR Response OK with deleted object with null id
     */
    ResponseEntity delete (long id);
}