package ru.stepan.ponomarev.storage_project.service;

import org.springframework.http.ResponseEntity;
import ru.stepan.ponomarev.storage_project.dto.InvoiceDto;

import java.util.List;

public interface InvoiceService {
     ResponseEntity<InvoiceDto> getInvoice (Long id);
     ResponseEntity<List<InvoiceDto>> getInvoices ();
     ResponseEntity<InvoiceDto> saveOrUpdateInvoice(InvoiceDto invoiceDto);
     ResponseEntity<String> confirmInvoice (Long id);
     ResponseEntity<InvoiceDto> deleteInvoice (Long id);
}
