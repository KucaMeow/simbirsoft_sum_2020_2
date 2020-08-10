package ru.stepan.ponomarev.storage_project.service;

import org.springframework.http.ResponseEntity;
import ru.stepan.ponomarev.storage_project.dto.InvoiceDto;

import java.util.List;

public interface InvoiceService {
     /**
      * Getting single invoice by it's id
      * @param id id of invoice record
      * @return ResponseEntity OK with InvoiceDto or ResponseEntity NOT_FOUND
      */
     ResponseEntity<InvoiceDto> getInvoice (Long id);

     /**
      * Getting all invoices from database
      * @return ResponseEntity OK with List of InvoiceDto objects
      */
     ResponseEntity<List<InvoiceDto>> getInvoices ();

     /**
      * Save or Update invoice from InvoiceDto
      * @param invoiceDto InvoiceDto object with info to save or update
      * @return InvoiceDto with new id and it's transaction id if save, or updated InvoiceDto of updated Invoice
      */
     ResponseEntity<InvoiceDto> saveOrUpdateInvoice(InvoiceDto invoiceDto);

     /**
      * Confirmation of invoice
      * @param id id of Invoice to confirm
      * @return Message of operation process
      */
     ResponseEntity<String> confirmInvoice (Long id);

     /**
      * Deleting invoice by id
      * @param id id of Invoice to delete
      * @return InvoiceDto object of deleted Invoice
      */
     ResponseEntity<InvoiceDto> deleteInvoice (Long id);
}
