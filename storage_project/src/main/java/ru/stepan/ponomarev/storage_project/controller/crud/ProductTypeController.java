package ru.stepan.ponomarev.storage_project.controller.crud;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.stepan.ponomarev.storage_project.dto.ProductTypeDto;
import ru.stepan.ponomarev.storage_project.service.ProductTypeCrudService;

import java.util.List;

/**
 * Controller for product type type crud operations
 */
@RestController
public class ProductTypeController {

    private final ProductTypeCrudService service;

    public ProductTypeController(ProductTypeCrudService service) {
        this.service = service;
    }

    /**
     * Show all product types from repository
     * @return Response OK with list of ProductType objects
     */
    @ApiOperation(value = "Show all product types from repository",
            produces = "Response OK with list of ProductType objects")
    @GetMapping("/product-type")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ProductTypeDto>> showAllProductsTypes() {
        return service.showAllProductsTypes();
    }

    /**
     * Show product types by it's id from repository
     * @param id id of product types
     * @return Response OK with ProductType object if it's found, or Response NOT_FOUND if it isn't found
     */
    @ApiOperation(value = "Show product types by it's id from repository",
            produces = "Response OK with ProductType object if it's found, Response NOT_FOUND if it isn't found")
    @GetMapping("/product-type/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProductTypeDto> showProductTypeById(@PathVariable @ApiParam("id of product types") long id) {
        return service.showProductTypeById(id);
    }

    /**
     * Save new product types from request body
     * @param productType ProductType object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    @ApiOperation(value = "Save new product types from request body",
            produces = "Response OK with updated object")
    @PutMapping("/product-type")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductTypeDto> addOrUpdateProductType(
            @RequestBody @ApiParam("ProductType object from request body") ProductTypeDto productType) {
        return service.addOrUpdateProductType(productType);
    }

    /**
     * Delete product types from repository
     * @param id id of ProductType to delete
     * @return Response NOT_FOUND if there's no object with this id OR Response OK with deleted object with null id
     */
    @ApiOperation(value = "Delete product types from repository",
            produces = "Response NOT_FOUND if there's no object with this id, Response OK with deleted object with null id")
    @DeleteMapping("/product-type/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductTypeDto> delete (@PathVariable @ApiParam("id of ProductType to delete") long id) {
        return service.delete(id);
    }
}
