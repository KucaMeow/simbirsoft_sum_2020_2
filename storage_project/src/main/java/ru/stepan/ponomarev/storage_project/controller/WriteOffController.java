package ru.stepan.ponomarev.storage_project.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.stepan.ponomarev.storage_project.dto.WriteOffDto;
import ru.stepan.ponomarev.storage_project.service.WriteOffService;

import java.util.List;

/**
 * Controller for write off management
 */
@RestController
public class WriteOffController {

    private final WriteOffService writeOffService;

    public WriteOffController(WriteOffService writeOffService) {
        this.writeOffService = writeOffService;
    }

    /**
     * Getting single write off by it's id
     * @param id id of write off record
     * @return ResponseEntity OK with WriteOffDto or ResponseEntity NOT_FOUND
     */
    @ApiOperation(value = "Getting single write off by it's id",
            produces = "ResponseEntity OK with WriteOffDto or ResponseEntity NOT_FOUND")
    @GetMapping("/write-off/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<WriteOffDto> getWriteOff (@PathVariable Long id) {
        return writeOffService.getWriteOff(id);
    }

    /**
     * Getting all write offs from database
     * @return ResponseEntity OK with List of WriteOffDto objects
     */
    @ApiOperation(value = "Getting all write offs from database",
            produces = "ResponseEntity OK with List of WriteOffDto objects")
    @GetMapping("/write-off")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<WriteOffDto>> getWriteOffs () {
        return writeOffService.getWriteOffs();
    }

    /**
     * Save or Update invoice from WriteOffDto
     * @param writeOffDto WriteOffDto object with info to save or update
     * @return WriteOffDto with new id and it's transaction id if save, or updated WriteOffDto of updated WriteOff
     */
    @ApiOperation(value = "Save or Update invoice from WriteOffDto",
            produces = "WriteOffDto with new id and it's transaction id if save, or updated WriteOffDto of updated WriteOff")
    @PutMapping("/write-off")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<WriteOffDto> saveOrUpdateWriteOff(@RequestBody WriteOffDto writeOffDto) {
        return writeOffService.saveOrUpdateWriteOff(writeOffDto);
    }

    /**
     * Confirmation of write off
     * @param id id of WriteOff to confirm
     * @return Message of operation process
     */
    @ApiOperation(value = "Confirmation of write off",
            produces = "Message of operation process")
    @PostMapping("/write-off/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> confirmWriteOff (@PathVariable Long id) {
        return writeOffService.confirmWriteOff(id);
    }

    /**
     * Deleting write off by id
     * @param id id of WriteOff to delete
     * @return WriteOffDto object of deleted WriteOff
     */
    @ApiOperation(value = "Deleting write off by id",
            produces = "WriteOffDto object of deleted WriteOff")
    @DeleteMapping("/write-off/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<WriteOffDto> deleteWriteOff (@PathVariable Long id) {
        return writeOffService.deleteWriteOff(id);
    }
}
