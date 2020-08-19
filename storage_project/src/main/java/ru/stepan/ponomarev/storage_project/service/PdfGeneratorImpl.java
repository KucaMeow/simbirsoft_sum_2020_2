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

    private void addParagraph(String paragraph, Document document) throws DocumentException {
        Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
        Paragraph para = new Paragraph( paragraph, font);
        para.setAlignment(Element.ALIGN_CENTER);
        document.add(para);
        document.add(Chunk.NEWLINE);
    }

    private PdfPTable createTable(int numColumns, String[] headers) {
        PdfPTable table = new PdfPTable(numColumns);

        Stream.of(headers)
                .forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(headerTitle, headFont));
                    table.addCell(header);
                });

        return table;
    }

    private void addCellToTableWithText(String text, PdfPTable table) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPaddingLeft(4);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
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

            addParagraph("Sold report for Shop: " + shop.get().getName(), document);
            PdfPTable table = createTable(2, new String[]{"Transaction ID", "Transaction info"});
            for (Transaction transaction : transactionList) {
                addCellToTableWithText(String.valueOf(transaction.getId()), table);
                PdfPTable transactionTable = createTable(2, new String[]{"Product ID", "Product current cost"});
                for (TransactionProductsInfo productsInfo : transaction.getProductList()) {
                    addCellToTableWithText(String.valueOf(productsInfo.getProduct().getId()), transactionTable);
                    addCellToTableWithText(String.valueOf(productsInfo.getProduct().getCost()), transactionTable);
                }
                addCellToTableWithText("Summary cost of transaction", transactionTable);
                addCellToTableWithText(String.valueOf(transaction.getCost_sum()), transactionTable);

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

            addParagraph("Write-offs for period from " + start.toString() + " to " + end.toString(), document);
            PdfPTable table = createTable(2, new String[]{"Transaction ID", "Transaction info"});

            for (Transaction transaction : transactionList) {
                addCellToTableWithText(String.valueOf(transaction.getId()), table);

                PdfPTable transactionTable = createTable(2, new String[]{"Product ID", "Count"});
                for (TransactionProductsInfo productsInfo : transaction.getProductList()) {
                    addCellToTableWithText(String.valueOf(productsInfo.getProduct().getId()), transactionTable);
                    addCellToTableWithText(String.valueOf(productsInfo.getQuantity()), transactionTable);
                }

                addCellToTableWithText("Summary cost of transaction", transactionTable);
                addCellToTableWithText(String.valueOf(transaction.getCost_sum()), transactionTable);

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

            addParagraph("Average receipt in Shop: " + shop.get().getName(), document);

            PdfPTable table = createTable(2, new String[]{"Transaction ID", "Transaction cost"});

            for (Transaction transaction : transactionList) {
                fullSum += transaction.getCost_sum();
                addCellToTableWithText(String.valueOf(transaction.getId()), table);
                addCellToTableWithText(String.valueOf(transaction.getCost_sum()), table);
            }
            document.add(table);
            document.add(Chunk.NEWLINE);

            addParagraph("Average receipt in this Shop: " + fullSum/transactionList.size(), document);

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

            addParagraph("Profit in Shop: " + shop.get().getName(), document);

            PdfPTable table = createTable(2, new String[]{"Transaction ID", "Transaction cost"});

            for (Transaction transaction : transactionList) {
                fullSum += transaction.getCost_sum();
                addCellToTableWithText(String.valueOf(transaction.getId()), table);
                addCellToTableWithText(String.valueOf(transaction.getCost_sum()), table);
            }
            document.add(table);
            document.add(Chunk.NEWLINE);

            addParagraph("Sold sum cost in this Shop: " + fullSum, document);

            document.close();
        }catch(DocumentException e) {
            throw new IllegalStateException(e);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}
