package ru.stepan.ponomarev.storage_project.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.stepan.ponomarev.storage_project.dto.InvoiceDto;
import ru.stepan.ponomarev.storage_project.service.InvoiceService;

import java.util.List;

/**
 * Controller for invoices management
 */
@RestController
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    /**
     * Getting single invoice by it's id
     * @param id id of invoice record
     * @return ResponseEntity OK with InvoiceDto or ResponseEntity NOT_FOUND
     */
    @ApiOperation(value = "Getting single invoice by it's id",
            produces = "ResponseEntity OK with InvoiceDto or ResponseEntity NOT_FOUND")
    @GetMapping("/invoice/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<InvoiceDto> getInvoice (@PathVariable Long id) {
        return invoiceService.getInvoice(id);
    }

    /**
     * Getting all invoices from database
     * @return ResponseEntity OK with List of InvoiceDto objects
     */
    @ApiOperation(value = "Getting all invoices from database",
            produces = "ResponseEntity OK with List of InvoiceDto objects")
    @GetMapping("/invoice")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<InvoiceDto>> getInvoices () {
        return invoiceService.getInvoices();
    }

    /**
     * Save or Update invoice from InvoiceDto
     * @param invoiceDto InvoiceDto object with info to save or update
     * @return InvoiceDto with new id and it's transaction id if save, or updated InvoiceDto of updated Invoice
     */
    @ApiOperation(value = "Save or Update invoice from InvoiceDto",
            produces = "InvoiceDto with new id and it's transaction id if save, or updated InvoiceDto of updated Invoice")
    @PutMapping("/invoice")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<InvoiceDto> saveOrUpdateInvoice(@RequestBody InvoiceDto invoiceDto) {
        return invoiceService.saveOrUpdateInvoice(invoiceDto);
    }

    /**
     * Confirmation of invoice
     * @param id id of Invoice to confirm
     * @return Message of operation process
     */
    @ApiOperation(value = "Confirmation of invoice",
            produces = "Message of operation process")
    @PostMapping("/invoice/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> confirmInvoice (@PathVariable Long id) {
        return invoiceService.confirmInvoice(id);
    }

    /**
     * Deleting invoice by id
     * @param id id of Invoice to delete
     * @return InvoiceDto object of deleted Invoice
     */
    @ApiOperation(value = "Deleting invoice by id",
            produces = "InvoiceDto object of deleted Invoice")
    @DeleteMapping("/invoice/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<InvoiceDto> deleteInvoice (@PathVariable Long id) {
        return invoiceService.deleteInvoice(id);
    }
}
