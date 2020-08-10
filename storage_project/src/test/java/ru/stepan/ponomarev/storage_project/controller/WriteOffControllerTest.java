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
import ru.stepan.ponomarev.storage_project.dto.WriteOffDto;
import ru.stepan.ponomarev.storage_project.service.WriteOffService;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class WriteOffControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    WriteOffService writeOffService;

    WriteOffDto writeOffDto;
    WriteOffDto savedWriteOff;
    WriteOffDto deletedWriteOff;

    @BeforeEach
    public void initTest() {
        writeOffDto = WriteOffDto.builder()
                .transactionId(1L)
                .isConfirmed(false)
                .id(1L)
                .build();
        savedWriteOff = WriteOffDto.builder()
                .transactionId(1L)
                .isConfirmed(false)
                .id(1L)
                .build();
        deletedWriteOff = WriteOffDto.builder()
                .transactionId(1L)
                .isConfirmed(false)
                .id(null)
                .build();

        given(writeOffService.getWriteOff(0L)).willReturn(ResponseEntity.notFound().build());
        given(writeOffService.getWriteOff(1L)).willReturn(ResponseEntity.ok(writeOffDto));
        given(writeOffService.getWriteOffs()).willReturn(ResponseEntity.ok(Collections.singletonList(writeOffDto)));
        given(writeOffService.saveOrUpdateWriteOff(any())).willReturn(ResponseEntity.ok(savedWriteOff));
        given(writeOffService.confirmWriteOff(0L)).willReturn(ResponseEntity.notFound().build());
        given(writeOffService.confirmWriteOff(1L)).willReturn(ResponseEntity.ok("Confirmed successfuly"));
        given(writeOffService.deleteWriteOff(0L)).willReturn(ResponseEntity.notFound().build());
        given(writeOffService.deleteWriteOff(1L)).willReturn(ResponseEntity.ok(deletedWriteOff));
    }

    @Test
    public void getWriteOffsShouldReturnListOfWriteOffs () throws Exception {
        mockMvc.perform(get("/write-off"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getWriteOffWithId0ShouldReturnNotFound () throws Exception {
        mockMvc.perform(get("/write-off/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getWriteOffWithId1ShouldReturnWriteOffDto () throws Exception {
        mockMvc.perform(get("/write-off/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(writeOffDto)));
    }

    @Test
    public void savingWriteOffShouldReturnObjectWithValidId() throws Exception {
        WriteOffDto toSave = WriteOffDto.builder()
                .transactionId(null)
                .id(null)
                .isConfirmed(false)
                .productInfoDtos(new ArrayList<>())
                .shopId(null)
                .build();
        mockMvc.perform(put("/write-off")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toSave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.any(Number.class)));
    }

    @Test
    public void confirmWriteOffShouldReturnMessageWithSuccess () throws Exception {
        mockMvc.perform(post("/write-off/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void confirmWriteOffWithId0ShouldReturnNotFound () throws Exception {
        mockMvc.perform(post("/write-off/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteWriteOffWithId1ShouldReturnObjectWithNullId () throws Exception {
        mockMvc.perform(delete("/write-off/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", nullValue()));
    }

    @Test
    public void deleteWriteOffWithId0ShouldReturnNotFound () throws Exception {
        mockMvc.perform(delete("/write-off/0"))
                .andExpect(status().isNotFound());
    }
}
