package ru.stepan.ponomarev.storage_project.service;

import ru.stepan.ponomarev.storage_project.model.Transaction;

import java.util.Date;
import java.util.List;

/**
 * Service for getting data for reports
 */
public interface ReportDataService {

    /**
     * Get all transactions from orders, which where made in current shop
     * @param shopId Id of shop
     * @return List of Transaction objects
     */
    List<Transaction> getTransactionsByShop (Long shopId);

    /**
     * Get all write-offs in current period of time
     * @param periodStart Start date of period
     * @param periodEnd Last date of period
     * @return List of Transaction object
     */
    List<Transaction> getTransactionsFromWriteOffsByPeriod (Date periodStart, Date periodEnd);
}
