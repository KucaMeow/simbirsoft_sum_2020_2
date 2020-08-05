package ru.stepan.ponomarev.storage_project.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.stepan.ponomarev.storage_project.dto.ProductInfoDto;
import ru.stepan.ponomarev.storage_project.dto.WriteOffDto;
import ru.stepan.ponomarev.storage_project.model.*;
import ru.stepan.ponomarev.storage_project.repository.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WriteOffServiceImpl implements WriteOffService {

    private final DtoMapper dtoMapper;
    private final WriteOffRepository writeOffRepository;
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final ProductsInfoRepository productsInfoRepository;

    public WriteOffServiceImpl(DtoMapper dtoMapper, WriteOffRepository writeOffRepository, TransactionRepository transactionRepository, ProductRepository productRepository, ShopRepository shopRepository, ProductsInfoRepository productsInfoRepository) {
        this.dtoMapper = dtoMapper;
        this.writeOffRepository = writeOffRepository;
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
        this.productsInfoRepository = productsInfoRepository;
    }

    @Override
    public ResponseEntity<WriteOffDto> getWriteOff(Long id) {
        Optional<WriteOff> writeOffOptional = writeOffRepository.findById(id);
        return writeOffOptional.map(a -> ResponseEntity.ok(dtoMapper.from(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<WriteOffDto>> getWriteOffs() {
        return ResponseEntity.ok(writeOffRepository.findAll()
                .stream().map(dtoMapper::from).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<WriteOffDto> saveOrUpdateWriteOff(WriteOffDto writeOffDto) {
        WriteOff toSave = dtoMapper.from(writeOffDto);
        if(toSave.getTransaction() == null) {
            List<TransactionProductsInfo> productsInfos = new ArrayList<>();

            for(ProductInfoDto dto : writeOffDto.getProductInfoDtos()) {
                Optional<Product> product = productRepository.findById(dto.getProductId());
                if(product.isPresent()) {
                    productsInfos.add(TransactionProductsInfo.builder()
                            .atStorage(writeOffDto.getShopId() == null)
                            .currentCost(product.get().getCost())
                            .product(product.get())
                            .quantity(dto.getQuantity())
                            .shop(writeOffDto.getShopId() == null ? null :
                                    shopRepository.findById(writeOffDto.getShopId()).orElse(null))
                            .transaction(toSave.getTransaction())
                            .build());
                } else throw new IllegalArgumentException("Can't find product by id");
            }

            toSave.setTransaction(Transaction.builder()
                    .date(new Date())
                    .cost_sum(0)
                    .productList(productsInfos)
                    .build());
            transactionRepository.save(toSave.getTransaction());
        }

        return ResponseEntity.ok(dtoMapper.from(writeOffRepository.save(toSave)));
    }

    @Override
    public ResponseEntity<String> confirmWriteOff(Long id) {
        Optional<WriteOff> writeOffOptional = writeOffRepository.findById(id);
        if(writeOffOptional.isPresent()) {
            WriteOff writeOff = writeOffOptional.get();
            if(!writeOff.isConfirmed()) {
                //Add products to shop or storage
                for(TransactionProductsInfo info : writeOff.getTransaction().getProductList()) {
                    Optional<ProductsInfo> productsInfoOptional = productsInfoRepository.findByProductIdAndShopIdAndAtStorage(info.getProduct().getId(),
                            info.getShop() == null ? null : info.getShop().getId(), info.isAtStorage());
                    if(productsInfoOptional.isPresent()) {
                        ProductsInfo productsInfo = productsInfoOptional.get();
                        if (productsInfo.getQuantity() - info.getQuantity() < 0) throw new IllegalArgumentException("Quantity out of bound at product with id " + info.getProduct().getId());
                        productsInfo.setQuantity(productsInfo.getQuantity() - info.getQuantity());
                        productsInfoRepository.save(productsInfo);
                    } else {
                        throw new IllegalArgumentException("Can't find products with such id at storage or shop with id " + info.getShop().getId());
                    }
                }

                //Mark as confirmed
                writeOff.setConfirmed(true);
                writeOffRepository.save(writeOff);
                return ResponseEntity.ok("Confirmed successfully");
            }
            return ResponseEntity.ok("Already confirmed. Nothing changed");
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<WriteOffDto> deleteWriteOff(Long id) {
        Optional<WriteOff> writeOff = writeOffRepository.findById(id);
        if(writeOff.isPresent()) {
            writeOffRepository.delete(writeOff.get());
            writeOff.get().setId(null);
            return ResponseEntity.ok(dtoMapper.from(writeOff.get()));
        }
        return ResponseEntity.notFound().build();
    }
}
