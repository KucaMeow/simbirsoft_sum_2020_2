package ru.stepan.ponomarev.storage_project.service;

import org.springframework.stereotype.Service;
import ru.stepan.ponomarev.storage_project.dto.ProductDto;
import ru.stepan.ponomarev.storage_project.model.Product;
import ru.stepan.ponomarev.storage_project.repository.ProductsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Uses ProductRepository and DtoMapper beans
 */
@Service
public class ProductsCrudServiceImpl implements ProductsCrudService {

    final
    ProductsRepository productsRepository;
    final
    DtoMapper dtoMapper;

    public ProductsCrudServiceImpl(ProductsRepository productsRepository, DtoMapper dtoMapper) {
        this.productsRepository = productsRepository;
        this.dtoMapper = dtoMapper;
    }

    /**
     * Show all products from repository
     * @return Response OK with list of ProductDtos objects
     */
    public List<ProductDto> showAllProducts() {
        return productsRepository.findAll().stream().map(dtoMapper::from).collect(Collectors.toList());
    }

    /**
     * Show product by it's id from repository
     * @param id id of product
     * @return Response OK with ProductDto object if it's found, or Response NOT_FOUND if it isn't found
     */
    public ProductDto showProductById(long id) {
        Optional<Product> product = productsRepository.findById(id);
        return product.map(dtoMapper::from).orElse(null);
    }

    /**
     * Show all products from repository
     * @return Response OK with list of Product objects
     */
    public List<Product> showAllProductsWithoutMapping() {
        return productsRepository.findAll();
    }

    /**
     * Show product by it's id from repository
     * @param id id of product
     * @return Response OK with Product object if it's found, or Response NOT_FOUND if it isn't found
     */
    public Product showProductByIdWithoutMapping(long id) {
        Optional<Product> product = productsRepository.findById(id);
        return product.orElse(null);
    }

    /**
     * Save new product from request body
     * @param productDto ProductDto object from request body (id should be null if want to create new, or not null to update)
     * @return Response OK with updated object
     */
    public ProductDto addOrUpdateProduct(ProductDto productDto) {
        Product product = dtoMapper.from(productDto);
        return dtoMapper.from(productsRepository.save(product));
    }

    /**
     * Delete product from repository
     * @param id id of Product to delete
     * @return String message. Response ok and "Deleted product with id *id*" or Response NOT_FOUND and "Can't find product by id"
     */
    public boolean delete (long id) {
        Optional<Product> product = productsRepository.findById(id);
        if(product.isPresent()) {
            productsRepository.delete(product.get());
            return true;
        }
        return false;
    }
}
