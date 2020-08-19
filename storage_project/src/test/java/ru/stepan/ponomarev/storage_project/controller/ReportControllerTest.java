package ru.stepan.ponomarev.storage_project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.stepan.ponomarev.storage_project.model.Shop;
import ru.stepan.ponomarev.storage_project.repository.ShopRepository;
import ru.stepan.ponomarev.storage_project.service.ReportDataService;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class ReportControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReportDataService reportDataService;
    @MockBean
    ShopRepository shopRepository;

    @BeforeEach
    public void initTest() {
        given(shopRepository.findById(anyLong())).willReturn(Optional.of(Shop.builder()
                .id(1L)
                .name("Name")
                .address("Address")
                .build()));
        given(reportDataService.getTransactionsByShop(anyLong())).willReturn(new ArrayList<>());
        given(reportDataService.getTransactionsFromWriteOffsByPeriod(any(), any())).willReturn(new ArrayList<>());
    }

    @Test
    public void soldGoodsReportShouldReturnPdfFile() throws Exception {
        mockMvc.perform(get("/pdf/sold/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF));
    }

    @Test
    public void shopProfitReportShouldReturnPdfFile() throws Exception {
        mockMvc.perform(get("/pdf/profit/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF));
    }

    @Test
    public void shopAverageReceiptReportShouldReturnPdfFile() throws Exception {
        mockMvc.perform(get("/pdf/average-receipt/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF));
    }

    @Test
    public void writtenOffGoodsReportShouldReturnPdfFile() throws Exception {
        mockMvc.perform(get("/pdf/write-off/1995-01-01/2020-08-19"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF));
    }
}
