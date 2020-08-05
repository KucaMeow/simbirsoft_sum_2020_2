package ru.stepan.ponomarev.storage_project.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.stepan.ponomarev.storage_project.dto.ProductDto;
import ru.stepan.ponomarev.storage_project.model.Product;
import ru.stepan.ponomarev.storage_project.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Uses ProductRepository and DtoMapper beans
 */
@Service
public class ProductCrudServiceImpl implements ProductCrudService {

    private final ProductRepository productRepository;
    private final DtoMapper dtoMapper;

    public ProductCrudServiceImpl(ProductRepository productRepository, DtoMapper dtoMapper) {
        this.productRepository = productRepository;
        this.dtoMapper = dtoMapper;
    }

    /**
     * Show all products from repository
     * @return Response OK with list of ProductDtos objects
     */
    public ResponseEntity<List<ProductDto>> showAllProducts() {
        return ResponseEntity.ok(productRepository.findAll()
                .stream().map(dtoMapper::from).collect(Collectors.toList()));
    }

    /**
     * Show product by it's id from repository
     * @param id id of product
     * @return Response OK with ProductDto object if it's found, or Response NOT_FOUND if it isn't found
     */
    public ResponseEntity<ProductDto> showProductById(long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(a -> ResponseEntity.ok(dtoMapper.from(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Save new product from request body
     * @param productDto ProductDto object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    public ResponseEntity<ProductDto> addOrUpdateProduct(ProductDto productDto) {
        Product product = dtoMapper.from(productDto);
        return ResponseEntity.ok(dtoMapper.from(productRepository.save(product)));
    }

    /**
     * Delete product from repository
     * @param id id of Product to delete
     * @return Response NOT_FOUND if there's no object with this id OR Response OK with deleted object with null id
     */
    public ResponseEntity<ProductDto> delete (long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            productRepository.delete(product.get());
            product.get().setId(null);
            return ResponseEntity.ok(dtoMapper.from(product.get()));
        }
        return ResponseEntity.notFound().build();
    }
}
