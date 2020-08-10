package ru.stepan.ponomarev.storage_project.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.stepan.ponomarev.storage_project.dto.InvoiceDto;
import ru.stepan.ponomarev.storage_project.dto.ProductInfoDto;
import ru.stepan.ponomarev.storage_project.model.*;
import ru.stepan.ponomarev.storage_project.repository.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final DtoMapper dtoMapper;
    private final InvoiceRepository invoiceRepository;
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final ProductsInfoRepository productsInfoRepository;

    public InvoiceServiceImpl(DtoMapper dtoMapper, TransactionRepository transactionRepository, InvoiceRepository invoiceRepository, ProductRepository productRepository, ShopRepository shopRepository, ProductsInfoRepository productsInfoRepository) {
        this.dtoMapper = dtoMapper;
        this.invoiceRepository = invoiceRepository;
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
        this.productsInfoRepository = productsInfoRepository;
    }

    @Override
    public ResponseEntity<InvoiceDto> getInvoice(Long id) {
        Optional<Invoice> invoiceDtoOptional = invoiceRepository.findById(id);
        return invoiceDtoOptional.map(a -> ResponseEntity.ok(dtoMapper.from(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<InvoiceDto>> getInvoices() {
        return ResponseEntity.ok(invoiceRepository.findAll()
                .stream().map(dtoMapper::from).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<InvoiceDto> saveOrUpdateInvoice(InvoiceDto invoiceDto) {
        Invoice toSave = dtoMapper.from(invoiceDto);
        //If transaction is null, means that it's new invoice. Need to create transaction and so on
        if(toSave.getTransaction() == null) {
            List<TransactionProductsInfo> productsInfos = new ArrayList<>();

            for(ProductInfoDto dto : invoiceDto.getProductInfoDtos()) {
                Optional<Product> product = productRepository.findById(dto.getProductId());
                if(product.isPresent()) {
                    productsInfos.add(TransactionProductsInfo.builder()
                            .atStorage(invoiceDto.getShopId() == null)
                            .currentCost(product.get().getCost())
                            .product(product.get())
                            .quantity(dto.getQuantity())
                            .shop(invoiceDto.getShopId() == null ? null :
                                    shopRepository.findById(invoiceDto.getShopId()).orElse(null))
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

        return ResponseEntity.ok(dtoMapper.from(invoiceRepository.save(toSave)));
    }

    @Override
    public ResponseEntity<String> confirmInvoice(Long id) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        if(invoiceOptional.isPresent()) {
            Invoice invoice = invoiceOptional.get();
            if(!invoice.isConfirmed()) {
                //Add products to shop or storage
                for(TransactionProductsInfo info : invoice.getTransaction().getProductList()) {
                    Optional<ProductsInfo> productsInfoOptional = productsInfoRepository.findByProductIdAndShopIdAndAtStorage(info.getProduct().getId(),
                            info.getShop() == null ? null : info.getShop().getId(), info.isAtStorage());
                    ProductsInfo productsInfo;
                    if(productsInfoOptional.isPresent()) {
                        productsInfo = productsInfoOptional.get();
                        productsInfo.setQuantity(productsInfo.getQuantity() + info.getQuantity());
                    } else {
                        productsInfo = ProductsInfo.builder()
                                .atStorage(info.isAtStorage())
                                .product(info.getProduct())
                                .quantity(info.getQuantity())
                                .shop(info.getShop())
                                .build();
                    }
                    productsInfoRepository.save(productsInfo);
                }

                //Mark as confirmed
                invoice.setConfirmed(true);
                invoiceRepository.save(invoice);
                return ResponseEntity.ok("Confirmed successfully");
            }
            return ResponseEntity.ok("Already confirmed. Nothing changed");
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<InvoiceDto> deleteInvoice(Long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if(invoice.isPresent()) {
            invoiceRepository.delete(invoice.get());
            invoice.get().setId(null);
            return ResponseEntity.ok(dtoMapper.from(invoice.get()));
        }
        return ResponseEntity.notFound().build();
    }
}
