package ru.stepan.ponomarev.storage_project.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.stepan.ponomarev.storage_project.dto.OrderDto;
import ru.stepan.ponomarev.storage_project.dto.ProductInfoDto;
import ru.stepan.ponomarev.storage_project.model.*;
import ru.stepan.ponomarev.storage_project.repository.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final DtoMapper dtoMapper;
    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final ShopRepository shopRepository;
    private final ProductsInfoRepository productsInfoRepository;

    public OrderServiceImpl(DtoMapper dtoMapper, OrderRepository orderRepository, TransactionRepository transactionRepository, ProductRepository productRepository, OrderStatusRepository orderStatusRepository, ShopRepository shopRepository, ProductsInfoRepository productsInfoRepository) {
        this.dtoMapper = dtoMapper;
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.shopRepository = shopRepository;
        this.productsInfoRepository = productsInfoRepository;
    }

    @Override
    public ResponseEntity<OrderDto> getOrder(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        return orderOptional.map(a -> ResponseEntity.ok(dtoMapper.from(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<OrderDto>> getOrders() {
        return ResponseEntity.ok(orderRepository.findAll()
                .stream().map(dtoMapper::from).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<OrderDto> saveOrUpdateOrder(OrderDto orderDto) {
        Order toSave = dtoMapper.from(orderDto);
        if(toSave.getTransaction() == null) {
            List<TransactionProductsInfo> productsInfos = new ArrayList<>();

            for(ProductInfoDto dto : orderDto.getProductInfoDtos()) {
                Optional<Product> product = productRepository.findById(dto.getProductId());
                if(product.isPresent()) {
                    productsInfos.add(TransactionProductsInfo.builder()
                            .atStorage(false)
                            .currentCost(product.get().getCost())
                            .product(product.get())
                            .quantity(dto.getQuantity())
                            .shop(null)
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

        return ResponseEntity.ok(dtoMapper.from(orderRepository.save(toSave)));
    }

    @Override
    public ResponseEntity<String> changeOrderStatus(Long orderId, Long statusId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if(orderOptional.isPresent()) {
            Order order = orderOptional.get();
            //Change status
            Optional<OrderStatus> status = orderStatusRepository.findById(statusId);
            if(!status.isPresent())
                return ResponseEntity.ok("Can't find status by id " + statusId + ". Nothing changed");
            order.setOrderStatus(status.get());
            orderRepository.save(order);
            return ResponseEntity.ok("Status changed to " + status.get().getStatus() + " successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<OrderDto> deleteOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()) {
            orderRepository.delete(order.get());
            order.get().setId(null);
            return ResponseEntity.ok(dtoMapper.from(order.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<OrderDto> processOrder(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(!orderOptional.isPresent()) return ResponseEntity.notFound().build();
        Order order = orderOptional.get();
        if(order.isProcessed()) return ResponseEntity.badRequest().build();

        for(TransactionProductsInfo info : order.getTransaction().getProductList()) {
            ProductsInfo productsInfo = productsInfoRepository.findByProductIdAndShopIdAndAtStorage(info.getProduct().getId(), info.getShop().getId(), false).get();
            productsInfo.setQuantity(productsInfo.getQuantity() - info.getQuantity());
            productsInfoRepository.save(productsInfo);
        }
        order.setProcessed(true);

        return ResponseEntity.ok(dtoMapper.from(orderRepository.save(order)));
    }

    @Override
    public ResponseEntity<OrderDto> countOrder(Long orderId, Long shopId) {
        Optional<Shop> shopOptional = shopRepository.findById(shopId);
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if(!(shopOptional.isPresent() && orderOptional.isPresent()))
            return ResponseEntity.notFound().build();

        Order order = orderOptional.get();

        if(order.isProcessed()) return ResponseEntity.badRequest().build();

        double costSum = 0;

        for(TransactionProductsInfo info : order.getTransaction().getProductList()) {
            Long prodId = info.getProduct().getId();
            Optional<ProductsInfo> shopProductInfoOptional = productsInfoRepository.findByProductIdAndShopIdAndAtStorage(prodId, shopId, false);
            if(shopProductInfoOptional.isPresent()) {
                if(shopProductInfoOptional.get().getQuantity() >= info.getQuantity()) {
                    info.setAtStorage(false);
                    info.setShop(shopProductInfoOptional.get().getShop());
                    info.setCurrentCost(shopProductInfoOptional.get().getProduct().getCost());
                    costSum += info.getCurrentCost();
                    continue;
                }
            }
            return ResponseEntity.badRequest().build();
        }

        order.getTransaction().setCost_sum(costSum);

        return ResponseEntity.ok(dtoMapper.from(orderRepository.save(order)));
    }

    @Override
    public ResponseEntity<OrderDto> cancelOrder(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(!orderOptional.isPresent()) return ResponseEntity.notFound().build();
        Order order = orderOptional.get();
        if(order.isProcessed()) {
            for(TransactionProductsInfo info : order.getTransaction().getProductList()) {
                ProductsInfo productsInfo = productsInfoRepository.findByProductIdAndShopIdAndAtStorage(info.getProduct().getId(), info.getShop().getId(), false).get();
                productsInfo.setQuantity(productsInfo.getQuantity() + info.getQuantity());
                productsInfoRepository.save(productsInfo);
            }
        }

        return deleteOrder(id);
    }
}
