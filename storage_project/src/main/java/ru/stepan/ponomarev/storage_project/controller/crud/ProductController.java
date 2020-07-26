package ru.stepan.ponomarev.storage_project.controller.crud;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.stepan.ponomarev.storage_project.dto.ProductDto;
import ru.stepan.ponomarev.storage_project.service.ProductCrudService;

import java.util.List;

/**
 * Controller for product crud operations
 */
@RestController
public class ProductController {

    private final ProductCrudService service;

    public ProductController(ProductCrudService service) {
        this.service = service;
    }

    /**
     * Show all products from repository
     * @return Response OK with list of ProductDtos objects
     */
    @ApiOperation(value = "Show all products from repository",
            produces = "Response OK with list of ProductDtos objects")
    @GetMapping("/product")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ProductDto>> showAllProducts() {
        return service.showAllProducts();
    }

    /**
     * Show product by it's id from repository
     * @param id id of product
     * @return Response OK with ProductDto object if it's found, or Response NOT_FOUND if it isn't found
     */
    @ApiOperation(value = "Show product by it's id from repository",
            produces = "Response OK with ProductDto object if it's found, Response NOT_FOUND if it isn't found")
    @GetMapping("/product/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProductDto> showProductById(@PathVariable @ApiParam("id of product") long id) {
        return service.showProductById(id);
    }

    /**
     * Save new product from request body
     * @param productDto ProductDto object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    @ApiOperation(value = "Save new product from request body",
            produces = "Response OK with updated object")
    @PutMapping("/product")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductDto> addOrUpdateProduct(@RequestBody @ApiParam("ProductDto object from request body") ProductDto productDto) {
        return service.addOrUpdateProduct(productDto);
    }

    /**
     * Delete product from repository
     * @param id id of Product to delete
     * @return Response NOT_FOUND if there's no object with this id OR Response OK with deleted object with null id
     */
    @ApiOperation(value = "Delete product from repository",
            produces = "Response NOT_FOUND if there's no object with this id, Response OK with deleted object with null id")
    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductDto> delete (@PathVariable @ApiParam("id of Product to delete") long id) {
        return service.delete(id);
    }

}
