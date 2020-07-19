package ru.stepan.ponomarev.storage_project.controller.crud;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepan.ponomarev.storage_project.dto.ProductDto;
import ru.stepan.ponomarev.storage_project.model.Product;
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
    @GetMapping("/products/get/all")
    public ResponseEntity<List<ProductDto>> showAllProducts() {
        return ResponseEntity.ok(service.showAllProducts());
    }

    /**
     * Show product by it's id from repository
     * @param id id of product
     * @return Response OK with ProductDto object if it's found, or Response NOT_FOUND if it isn't found
     */
    @ApiOperation(value = "Show product by it's id from repository",
            produces = "Response OK with ProductDto object if it's found, Response NOT_FOUND if it isn't found")
    @GetMapping("/products/get/{id}")
    public ResponseEntity<ProductDto> showProductById(@PathVariable @ApiParam("id of product") long id) {
        ProductDto productDto = service.showProductById(id);
        if(productDto != null) {
            return ResponseEntity.ok(productDto);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Show all products from repository
     * @return Response OK with list of Product objects
     */
    @ApiOperation(value = "Show all products from repository",
            produces = "Response OK with list of Product objects")
    @GetMapping("/products/get-unmapped/all")
    public ResponseEntity<List<Product>> showAllProductsWithoutMapping() {
        return ResponseEntity.ok(service.showAllProductsWithoutMapping());
    }

    /**
     * Show product by it's id from repository
     * @param id id of product
     * @return Response OK with Product object if it's found, or Response NOT_FOUND if it isn't found
     */
    @ApiOperation(value = "Show product by it's id from repository",
            produces = "Response OK with Product object if it's found, Response NOT_FOUND if it isn't found")
    @GetMapping("/products/get-unmapped/{id}")
    public ResponseEntity<Product> showProductByIdWithoutMapping(@PathVariable @ApiParam("id of product") long id) {
        Product product = service.showProductByIdWithoutMapping(id);
        if(product != null) {
            return ResponseEntity.ok(product);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Save new product from request body
     * @param productDto ProductDto object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    @ApiOperation(value = "Save new product from request body",
            produces = "Response OK with updated object")
    @PostMapping("products/save")
    public ResponseEntity<ProductDto> addOrUpdateProduct(@RequestBody @ApiParam("ProductDto object from request body") ProductDto productDto) {
        return ResponseEntity.ok(service.addOrUpdateProduct(productDto));
    }

    /**
     * Delete product from repository
     * @param id id of Product to delete
     * @return String message. Response ok and "Deleted product with id *id*" or Response NOT_FOUND and "Can't find product by id"
     */
    @ApiOperation(value = "Delete product from repository",
            produces = "Response ok and \"Deleted product with id *id*\", Response NOT_FOUND and \"Can't find product by id\"")
    @PostMapping("products/delete/{id}")
    public ResponseEntity<String> delete (@PathVariable @ApiParam("id of Product to delete") long id) {
        if(service.delete(id)) {
            return ResponseEntity.ok("Deleted product with id " + id);
        }
        return new ResponseEntity<>("Can't find product by id", HttpStatus.NOT_FOUND);
    }

}
