package ru.stepan.ponomarev.storage_project.controller;

import org.springframework.core.io.InputStreamResource;
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

@RestController
@RequestMapping("/pdf")
public class ReportController {

    private final PdfGenerator pdfGenerator;

    public ReportController(PdfGenerator pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
    }

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

    @GetMapping(value = "/write-off/{start}/{end}",
            produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<InputStreamResource> writtenOffGoodsReport(@PathVariable Date start, @PathVariable Date end) {

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
