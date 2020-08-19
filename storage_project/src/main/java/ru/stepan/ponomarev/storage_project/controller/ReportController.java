package ru.stepan.ponomarev.storage_project.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stepan.ponomarev.storage_project.service.PdfGenerator;

import java.io.ByteArrayInputStream;
import java.util.Date;

/**
 * Controller for getting reports in pdf format
 */
@RestController
@RequestMapping("/pdf")
public class ReportController {

    private final PdfGenerator pdfGenerator;

    public ReportController(PdfGenerator pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
    }

    /**
     * Getting report for all sold goods in current shop
     * @param id id of shop
     * @return APPLICATION_PDF_VALUE with generated PDF file
     */
    @ApiOperation(value = "Getting report for all sold goods in current shop",
            produces = "APPLICATION_PDF_VALUE with generated PDF file")
    @GetMapping(value = "/sold/{id}",
            produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<InputStreamResource> soldGoodsReport(@PathVariable Long id) {

        ByteArrayInputStream bis = pdfGenerator.soldGoodsReport(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=soldGoodsReport" + id + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    /**
     * Getting report for average receipt in current shop
     * @param id id of shop
     * @return APPLICATION_PDF_VALUE with generated PDF file
     */
    @ApiOperation(value = "Getting report for average receipt in current shop",
            produces = "APPLICATION_PDF_VALUE with generated PDF file")
    @GetMapping(value = "/average-receipt/{id}",
            produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<InputStreamResource> shopAverageReceiptReport(@PathVariable Long id) {

        ByteArrayInputStream bis = pdfGenerator.shopAverageReceiptReport(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=shopAverageReceiptReport" + id + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    /**
     * Getting report for profit sum in current shop
     * @param id id of shop
     * @return APPLICATION_PDF_VALUE with generated PDF file
     */
    @ApiOperation(value = "Getting report for profit sum in current shop",
            produces = "APPLICATION_PDF_VALUE with generated PDF file")
    @GetMapping(value = "/profit/{id}",
            produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<InputStreamResource> shopProfitReport(@PathVariable Long id) {

        ByteArrayInputStream bis = pdfGenerator.shopProfitReport(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=shopProfitReport" + id + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    /**
     * Getting report for write-offs in current period of time
     * @param start start date of time period
     * @param end last date of time period
     * @return APPLICATION_PDF_VALUE with generated PDF file
     */
    @ApiOperation(value = "Getting report for write-offs in current period of time",
            produces = "APPLICATION_PDF_VALUE with generated PDF file",
            notes = "Date format is yyyy-mm-dd; Example: \"pdf/write-off/2000-01-01/2000-01-31\"")
    @GetMapping(value = "/write-off/{start}/{end}",
            produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<InputStreamResource> writtenOffGoodsReport(@PathVariable @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) Date start,
                                                                     @PathVariable @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) Date end) {

        ByteArrayInputStream bis = pdfGenerator.writtenOffGoodsReport(start, end);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=writtenOffGoodsReport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
