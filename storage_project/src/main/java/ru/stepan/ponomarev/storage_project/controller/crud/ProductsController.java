package ru.stepan.ponomarev.storage_project.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepan.ponomarev.storage_project.dto.ProductDto;
import ru.stepan.ponomarev.storage_project.model.Product;
import ru.stepan.ponomarev.storage_project.repository.ProductsRepository;
import ru.stepan.ponomarev.storage_project.service.DtoMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller for product crud operations
 */
@RestController
public class ProductsController {

    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    DtoMapper dtoMapper;

    /**
     * Show all products from repository
     * @return Response OK with list of ProductDtos objects
     */
    @GetMapping("/products/get/all")
    public ResponseEntity<List<ProductDto>> showAllProducts() {
        return ResponseEntity.ok(productsRepository.findAll().stream().map(a -> dtoMapper.from(a)).collect(Collectors.toList()));
    }

    /**
     * Show product by it's id from repository
     * @param id id of product
     * @return Response OK with ProductDto object if it's found, or Response NOT_FOUND if it isn't found
     */
    @GetMapping("/products/get/{id}")
    public ResponseEntity<ProductDto> showProductById(@PathVariable long id) {
        Optional<Product> product = productsRepository.findById(id);
        return product.map(value -> ResponseEntity.ok(dtoMapper.from(value)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Show all products from repository
     * @return Response OK with list of Product objects
     */
    @GetMapping("/products/get-unmapped/all")
    public ResponseEntity<List<Product>> showAllProductsWithoutMapping() {
        return ResponseEntity.ok(productsRepository.findAll());
    }

    /**
     * Show product by it's id from repository
     * @param id id of product
     * @return Response OK with Product object if it's found, or Response NOT_FOUND if it isn't found
     */
    @GetMapping("/products/get-unmapped/{id}")
    public ResponseEntity<Product> showProductByIdWithoutMapping(@PathVariable long id) {
        Optional<Product> product = productsRepository.findById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Save new product from request body
     * @param productDto ProductDto object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    @PostMapping("products/save")
    public ResponseEntity<ProductDto> addOrUpdateProduct(@RequestBody ProductDto productDto) {
        Product product = dtoMapper.from(productDto);
        return ResponseEntity.ok(dtoMapper.from(productsRepository.save(product)));
    }

    /**
     * Delete product from repository
     * @param id id of Product to delete
     * @return String message. Response ok and "Deleted product with id *id*" or Response NOT_FOUND and "Can't find product by id"
     */
    @PostMapping("products/delete/{id}")
    public ResponseEntity<String> delete (@PathVariable long id) {
        Optional<Product> product = productsRepository.findById(id);
        if(product.isPresent()) {
            productsRepository.delete(product.get());
            return ResponseEntity.ok("Deleted product with id " + id);
        }
        return new ResponseEntity<>("Can't find product by id", HttpStatus.NOT_FOUND);
    }

}
