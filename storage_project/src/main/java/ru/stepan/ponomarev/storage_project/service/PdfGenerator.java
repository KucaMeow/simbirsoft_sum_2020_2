package ru.stepan.ponomarev.storage_project.service;

import java.io.ByteArrayInputStream;
import java.util.Date;

/**
 * PDF generator interface
 */
public interface PdfGenerator {

    /**
     * Generates PDF for all sold goods from shop
     * @param shopId Id of shop to create report
     * @return ByteArrayInputStream of generated PDF
     */
    public ByteArrayInputStream soldGoodsReport(long shopId);

    /**
     * Generates PDF for all sold goods from shop
     * @param start start date of period
     * @param end last date of period
     * @return ByteArrayInputStream of generated PDF
     */
    public ByteArrayInputStream writtenOffGoodsReport(Date start, Date end);

    /**
     * Generates PDF for average receipt cost in shop
     * @param shopId Id of shop to create report
     * @return ByteArrayInputStream of generated PDF
     */
    public ByteArrayInputStream shopAverageReceiptReport(long shopId);

    /**
     * Generates PDF for shop profit info
     * @param shopId Id of shop to create report
     * @return ByteArrayInputStream of generated PDF
     */
    public ByteArrayInputStream shopProfitReport(long shopId);
    
}
