package ru.stepan.ponomarev.storage_project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.stepan.ponomarev.storage_project.dto.InvoiceDto;
import ru.stepan.ponomarev.storage_project.service.InvoiceService;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class InvoiceControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    InvoiceService invoiceService;

    InvoiceDto invoice;
    InvoiceDto savedInvoice;
    InvoiceDto deletedInvoice;

    @BeforeEach
    public void initTest() {
        invoice = InvoiceDto.builder()
                .transactionId(1L)
                .isConfirmed(false)
                .id(1L)
                .build();
        savedInvoice = InvoiceDto.builder()
                .transactionId(1L)
                .isConfirmed(false)
                .id(1L)
                .build();
        deletedInvoice = InvoiceDto.builder()
                .transactionId(1L)
                .isConfirmed(false)
                .id(null)
                .build();

        given(invoiceService.getInvoice(0L)).willReturn(ResponseEntity.notFound().build());
        given(invoiceService.getInvoice(1L)).willReturn(ResponseEntity.ok(invoice));
        given(invoiceService.getInvoices()).willReturn(ResponseEntity.ok(Collections.singletonList(invoice)));
        given(invoiceService.saveOrUpdateInvoice(any())).willReturn(ResponseEntity.ok(savedInvoice));
        given(invoiceService.confirmInvoice(0L)).willReturn(ResponseEntity.notFound().build());
        given(invoiceService.confirmInvoice(1L)).willReturn(ResponseEntity.ok("Confirmed successfuly"));
        given(invoiceService.deleteInvoice(0L)).willReturn(ResponseEntity.notFound().build());
        given(invoiceService.deleteInvoice(1L)).willReturn(ResponseEntity.ok(deletedInvoice));
    }

    @Test
    public void getInvoicesShouldReturnListOfInvoices () throws Exception {
        mockMvc.perform(get("/invoice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getInvoiceWithId0ShouldReturnNotFound () throws Exception {
        mockMvc.perform(get("/invoice/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getInvoiceWithId1ShouldReturnInvoiceDto () throws Exception {
        mockMvc.perform(get("/invoice/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(invoice)));
    }

    @Test
    public void savingInvoiceShouldReturnObjectWithValidId() throws Exception {
        InvoiceDto toSave = InvoiceDto.builder()
                .transactionId(null)
                .id(null)
                .isConfirmed(false)
                .productInfoDtos(new ArrayList<>())
                .shopId(null)
                .build();
        mockMvc.perform(put("/invoice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toSave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.any(Number.class)));
    }

    @Test
    public void confirmInvoiceShouldReturnMessageWithSuccess () throws Exception {
        mockMvc.perform(post("/invoice/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void confirmInvoiceWithId0ShouldReturnNotFound () throws Exception {
        mockMvc.perform(post("/invoice/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteInvoiceWithId1ShouldReturnObjectWithNullId () throws Exception {
        mockMvc.perform(delete("/invoice/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", nullValue()));
    }

    @Test
    public void deleteInvoiceWithId0ShouldReturnNotFound () throws Exception {
        mockMvc.perform(delete("/invoice/0"))
                .andExpect(status().isNotFound());
    }

}
