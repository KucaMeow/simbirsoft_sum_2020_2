package ru.stepan.ponomarev.storage_project.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import ru.stepan.ponomarev.storage_project.model.Shop;
import ru.stepan.ponomarev.storage_project.model.Transaction;
import ru.stepan.ponomarev.storage_project.model.TransactionProductsInfo;
import ru.stepan.ponomarev.storage_project.repository.ShopRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PdfGeneratorImpl implements PdfGenerator {

    final private ReportDataService reportDataService;
    final private ShopRepository shopRepository;

    public PdfGeneratorImpl(ReportDataService reportDataService, ShopRepository shopRepository) {
        this.reportDataService = reportDataService;
        this.shopRepository = shopRepository;
    }

    @Override
    public ByteArrayInputStream soldGoodsReport(long shopId) {
        Optional<Shop> shop = shopRepository.findById(shopId);

        if(!shop.isPresent()) {
            throw new IllegalArgumentException("Can't find shop with such id");
        }
        List<Transaction> transactionList = reportDataService.getTransactionsByShop(shopId);

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {

            PdfWriter.getInstance(document, out);
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph( "Sold report for Shop: " + shop.get().getName(), font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(2);
            Stream.of("Transaction ID", "Transaction info")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(headerTitle, headFont));
                        table.addCell(header);
                    });

            for (Transaction transaction : transactionList) {
                PdfPCell idCell = new PdfPCell(new Phrase(String.valueOf(transaction.getId())));
                idCell.setPaddingLeft(4);
                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idCell);

                PdfPTable transactionTable = new PdfPTable(2);
                Stream.of("Product ID", "Product current cost")
                        .forEach(a -> {
                            PdfPCell header = new PdfPCell();
                            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            header.setHorizontalAlignment(Element.ALIGN_CENTER);
                            header.setBorderWidth(2);
                            header.setPhrase(new Phrase(a, headFont));
                            transactionTable.addCell(header);
                        });
                for (TransactionProductsInfo productsInfo : transaction.getProductList()) {
                    PdfPCell productIdCell = new PdfPCell(new Phrase(String.valueOf(productsInfo.getProduct().getId())));
                    productIdCell.setPaddingLeft(4);
                    productIdCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    productIdCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    transactionTable.addCell(productIdCell);

                    PdfPCell productCostCell = new PdfPCell(new Phrase(String.valueOf(productsInfo.getProduct().getCost())));
                    productCostCell.setPaddingLeft(4);
                    productCostCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    productCostCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    transactionTable.addCell(productCostCell);
                }

                PdfPCell productSumLabel = new PdfPCell(new Phrase("Summary cost of transaction"));
                productSumLabel.setPaddingLeft(4);
                productSumLabel.setVerticalAlignment(Element.ALIGN_MIDDLE);
                productSumLabel.setHorizontalAlignment(Element.ALIGN_CENTER);
                transactionTable.addCell(productSumLabel);

                PdfPCell productSum = new PdfPCell(new Phrase(String.valueOf(transaction.getCost_sum())));
                productSum.setPaddingLeft(4);
                productSum.setVerticalAlignment(Element.ALIGN_MIDDLE);
                productSum.setHorizontalAlignment(Element.ALIGN_CENTER);
                transactionTable.addCell(productSum);

                table.addCell(transactionTable);
            }
            document.add(table);

            document.close();
        }catch(DocumentException e) {
            throw new IllegalStateException(e);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public ByteArrayInputStream writtenOffGoodsReport(Date start, Date end) {
        List<Transaction> transactionList = reportDataService.getTransactionsFromWriteOffsByPeriod(start, end);

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {

            PdfWriter.getInstance(document, out);
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph( "Write-offs for period from " + start.toString() + " to " + end.toString(), font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(2);
            Stream.of("Transaction ID", "Transaction info")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(headerTitle, headFont));
                        table.addCell(header);
                    });

            for (Transaction transaction : transactionList) {
                PdfPCell idCell = new PdfPCell(new Phrase(String.valueOf(transaction.getId())));
                idCell.setPaddingLeft(4);
                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idCell);

                PdfPTable transactionTable = new PdfPTable(2);
                Stream.of("Product ID", "Count")
                        .forEach(a -> {
                            PdfPCell header = new PdfPCell();
                            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            header.setHorizontalAlignment(Element.ALIGN_CENTER);
                            header.setBorderWidth(2);
                            header.setPhrase(new Phrase(a, headFont));
                            transactionTable.addCell(header);
                        });
                for (TransactionProductsInfo productsInfo : transaction.getProductList()) {
                    PdfPCell productIdCell = new PdfPCell(new Phrase(String.valueOf(productsInfo.getProduct().getId())));
                    productIdCell.setPaddingLeft(4);
                    productIdCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    productIdCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    transactionTable.addCell(productIdCell);

                    PdfPCell productCountCell = new PdfPCell(new Phrase(String.valueOf(productsInfo.getQuantity())));
                    productCountCell.setPaddingLeft(4);
                    productCountCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    productCountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    transactionTable.addCell(productCountCell);
                }

                PdfPCell productSumLabel = new PdfPCell(new Phrase("Summary cost of transaction"));
                productSumLabel.setPaddingLeft(4);
                productSumLabel.setVerticalAlignment(Element.ALIGN_MIDDLE);
                productSumLabel.setHorizontalAlignment(Element.ALIGN_CENTER);
                transactionTable.addCell(productSumLabel);

                PdfPCell productSum = new PdfPCell(new Phrase(String.valueOf(transaction.getCost_sum())));
                productSum.setPaddingLeft(4);
                productSum.setVerticalAlignment(Element.ALIGN_MIDDLE);
                productSum.setHorizontalAlignment(Element.ALIGN_CENTER);
                transactionTable.addCell(productSum);

                table.addCell(transactionTable);
            }
            document.add(table);

            document.close();
        }catch(DocumentException e) {
            throw new IllegalStateException(e);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public ByteArrayInputStream shopAverageReceiptReport(long shopId) {
        Optional<Shop> shop = shopRepository.findById(shopId);

        if(!shop.isPresent()) {
            throw new IllegalArgumentException("Can't find shop with such id");
        }
        List<Transaction> transactionList = reportDataService.getTransactionsByShop(shopId);

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            double fullSum = 0;
            PdfWriter.getInstance(document, out);
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph( "Average receipt in Shop: " + shop.get().getName(), font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(2);
            Stream.of("Transaction ID", "Transaction cost")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(headerTitle, headFont));
                        table.addCell(header);
                    });

            for (Transaction transaction : transactionList) {
                fullSum += transaction.getCost_sum();

                PdfPCell idCell = new PdfPCell(new Phrase(String.valueOf(transaction.getId())));
                idCell.setPaddingLeft(4);
                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idCell);

                PdfPCell productSum = new PdfPCell(new Phrase(String.valueOf(transaction.getCost_sum())));
                productSum.setPaddingLeft(4);
                productSum.setVerticalAlignment(Element.ALIGN_MIDDLE);
                productSum.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(productSum);
            }
            document.add(table);
            document.add(Chunk.NEWLINE);

            Paragraph para2 = new Paragraph( "Average receipt in this Shop: " + fullSum/transactionList.size(), font);
            para2.setAlignment(Element.ALIGN_CENTER);
            document.add(para2);

            document.close();
        }catch(DocumentException e) {
            throw new IllegalStateException(e);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public ByteArrayInputStream shopProfitReport(long shopId) {
        Optional<Shop> shop = shopRepository.findById(shopId);

        if(!shop.isPresent()) {
            throw new IllegalArgumentException("Can't find shop with such id");
        }
        List<Transaction> transactionList = reportDataService.getTransactionsByShop(shopId);

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            double fullSum = 0;
            PdfWriter.getInstance(document, out);
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph( "Average receipt in Shop: " + shop.get().getName(), font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(2);
            Stream.of("Transaction ID", "Transaction cost")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(headerTitle, headFont));
                        table.addCell(header);
                    });

            for (Transaction transaction : transactionList) {
                fullSum += transaction.getCost_sum();

                PdfPCell idCell = new PdfPCell(new Phrase(String.valueOf(transaction.getId())));
                idCell.setPaddingLeft(4);
                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idCell);

                PdfPCell productSum = new PdfPCell(new Phrase(String.valueOf(transaction.getCost_sum())));
                productSum.setPaddingLeft(4);
                productSum.setVerticalAlignment(Element.ALIGN_MIDDLE);
                productSum.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(productSum);
            }
            document.add(table);
            document.add(Chunk.NEWLINE);

            Paragraph para2 = new Paragraph( "Sold sum cost in this Shop: " + fullSum, font);
            para2.setAlignment(Element.ALIGN_CENTER);
            document.add(para2);

            document.close();
        }catch(DocumentException e) {
            throw new IllegalStateException(e);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}
