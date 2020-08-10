package ru.stepan.ponomarev.storage_project.service;

import org.springframework.http.ResponseEntity;
import ru.stepan.ponomarev.storage_project.dto.WriteOffDto;

import java.util.List;

public interface WriteOffService {
    /**
     * Getting single write off by it's id
     * @param id id of write off record
     * @return ResponseEntity OK with WriteOffDto or ResponseEntity NOT_FOUND
     */
    ResponseEntity<WriteOffDto> getWriteOff(Long id);

    /**
     * Getting all write offs from database
     * @return ResponseEntity OK with List of WriteOffDto objects
     */
    ResponseEntity<List<WriteOffDto>> getWriteOffs();

    /**
     * Save or Update invoice from WriteOffDto
     * @param writeOffDto WriteOffDto object with info to save or update
     * @return WriteOffDto with new id and it's transaction id if save, or updated WriteOffDto of updated WriteOff
     */
    ResponseEntity<WriteOffDto> saveOrUpdateWriteOff(WriteOffDto writeOffDto);

    /**
     * Confirmation of write off
     * @param id id of WriteOff to confirm
     * @return Message of operation process
     */
    ResponseEntity<String> confirmWriteOff(Long id);

    /**
     * Deleting write off by id
     * @param id id of WriteOff to delete
     * @return WriteOffDto object of deleted WriteOff
     */
    ResponseEntity<WriteOffDto> deleteWriteOff(Long id);
}
