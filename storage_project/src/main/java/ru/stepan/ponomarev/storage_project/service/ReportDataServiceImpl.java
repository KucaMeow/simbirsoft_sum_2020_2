package ru.stepan.ponomarev.storage_project.service;

import org.springframework.stereotype.Service;
import ru.stepan.ponomarev.storage_project.model.Order;
import ru.stepan.ponomarev.storage_project.model.Transaction;
import ru.stepan.ponomarev.storage_project.model.WriteOff;
import ru.stepan.ponomarev.storage_project.repository.OrderRepository;
import ru.stepan.ponomarev.storage_project.repository.WriteOffRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportDataServiceImpl implements ReportDataService {

    private final OrderRepository orderRepository;
    private final WriteOffRepository writeOffRepository;

    public ReportDataServiceImpl(OrderRepository orderRepository, WriteOffRepository writeOffRepository) {
        this.orderRepository = orderRepository;
        this.writeOffRepository = writeOffRepository;
    }

    @Override
    public List<Transaction> getTransactionsByShop(Long shopId) {
        return orderRepository.findOrdersByTransactionProductListShop_IdAndProcessedIsTrue(shopId)
                .stream().map(Order::getTransaction).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getTransactionsFromWriteOffsByPeriod(Date periodStart, Date periodEnd) {
        return writeOffRepository.getWriteOffsByTransactionDateBetween(periodStart, periodEnd)
                .stream().map(WriteOff::getTransaction).collect(Collectors.toList());
    }
}
