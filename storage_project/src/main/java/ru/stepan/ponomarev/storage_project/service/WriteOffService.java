package ru.stepan.ponomarev.storage_project.service;

import org.springframework.http.ResponseEntity;
import ru.stepan.ponomarev.storage_project.dto.WriteOffDto;

import java.util.List;

public interface WriteOffService {
    ResponseEntity<WriteOffDto> getWriteOff(Long id);
    ResponseEntity<List<WriteOffDto>> getWriteOffs();
    ResponseEntity<WriteOffDto> saveOrUpdateWriteOff(WriteOffDto writeOffDto);
    ResponseEntity<String> confirmWriteOff(Long id);
    ResponseEntity<WriteOffDto> deleteWriteOff(Long id);
}
