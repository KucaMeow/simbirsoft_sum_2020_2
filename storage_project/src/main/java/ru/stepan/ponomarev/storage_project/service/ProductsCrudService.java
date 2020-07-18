package ru.stepan.ponomarev.storage_project.service;

import ru.stepan.ponomarev.storage_project.dto.ProductDto;
import ru.stepan.ponomarev.storage_project.model.Product;

import java.util.List;

public interface ProductsCrudService {
    /**
     * Show all products from repository
     * @return Response OK with list of ProductDtos objects
     */
    List<ProductDto> showAllProducts();

    /**
     * Show product by it's id from repository
     * @param id id of product
     * @return Response OK with ProductDto object if it's found, or Response NOT_FOUND if it isn't found
     */
    ProductDto showProductById(long id);

    /**
     * Show all products from repository
     * @return Response OK with list of Product objects
     */
    List<Product> showAllProductsWithoutMapping();

    /**
     * Show product by it's id from repository
     * @param id id of product
     * @return Response OK with Product object if it's found, or Response NOT_FOUND if it isn't found
     */
    Product showProductByIdWithoutMapping(long id);

    /**
     * Save new product from request body
     * @param productDto ProductDto object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    ProductDto addOrUpdateProduct(ProductDto productDto);

    /**
     * Delete product from repository
     * @param id id of Product to delete
     * @return String message. Response ok and "Deleted product with id *id*" or Response NOT_FOUND and "Can't find product by id"
     */
    boolean delete (long id);
}
