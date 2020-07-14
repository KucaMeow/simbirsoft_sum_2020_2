package ru.stepan.ponomarev.storage_project.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepan.ponomarev.storage_project.model.Product;
import ru.stepan.ponomarev.storage_project.repository.ProductsRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductsController {

    @Autowired
    ProductsRepository productsRepository;

    @GetMapping("/products/get/all")
    public ResponseEntity<List<Product>> showAllProducts() {
        return ResponseEntity.ok(productsRepository.findAll());
    }

    @GetMapping("/products/get/{id}")
    public ResponseEntity<Product> showProductById(@PathVariable long id) {
        Optional<Product> product = productsRepository.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("products/save")
    public ResponseEntity<Product> addOrUpdateProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productsRepository.save(product));
    }

    @PostMapping("products/update/{id}")
    public ResponseEntity<Product> updateWithId(@RequestBody Product product, @PathVariable long id) {
        product.setId(id);
        return ResponseEntity.ok(productsRepository.save(product));
    }

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
