package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.PermissionServiceImpl;
import fs19.java.backend.application.dto.permission.PermissionRequestDTO;
import fs19.java.backend.application.dto.permission.PermissionResponseDTO;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import fs19.java.backend.presentation.shared.response.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * This class will work as the main permission entity controller and each method will work as endpoints
 */

@Tag(name = "Permission", description = "Manage Permissions")
@RestController
@RequestMapping("api/v1/permissions")
public class PermissionController {

    @Autowired
    private PermissionServiceImpl permissionService;

    /**
     * Create a permission object using the permission-name
     *
     * @param permissionRequestDTO PermissionRequestDTO
     * @return
     */
    @Operation(summary = "Create a Permission", description = "Creates a new Permission with the provided details.")
    @PostMapping
    public ResponseEntity<GlobalResponse<PermissionResponseDTO>> createPermission(@RequestBody @Valid PermissionRequestDTO permissionRequestDTO) {
        PermissionResponseDTO thePermissionDTO = permissionService.save(permissionRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.CREATED, thePermissionDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), thePermissionDTO, ResponseHandler.convertResponseStatusToError(thePermissionDTO.getStatus())), responseCode);
    }

    /**
     * Update a Permission by id
     *
     * @param permissionRequestDTO PermissionRequestDTO
     * @return
     */
    @Operation(summary = "Update a Permission", description = "Updates the details of an existing permission.")
    @PutMapping("/{permissionId}")
    public ResponseEntity<GlobalResponse<PermissionResponseDTO>> updatePermission(@PathVariable UUID permissionId, @RequestBody @Valid PermissionRequestDTO permissionRequestDTO) {
        PermissionResponseDTO thePermissionDTo = permissionService.update(permissionId, permissionRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, thePermissionDTo.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), thePermissionDTo, ResponseHandler.convertResponseStatusToError(thePermissionDTo.getStatus())), responseCode);
    }

    /**
     * Delete Permission By Id
     *
     * @param permissionId
     * @return
     */
    @Operation(summary = "Delete a permission", description = "Deletes a permission by its ID.")
    @DeleteMapping("/{permissionId}")
    public ResponseEntity<GlobalResponse<PermissionResponseDTO>> deletePermissionById(@PathVariable UUID permissionId) {
        PermissionResponseDTO permissionResponseDTO = permissionService.delete(permissionId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, permissionResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), permissionResponseDTO, ResponseHandler.convertResponseStatusToError(permissionResponseDTO.getStatus())), responseCode);
    }

    /**
     * Return the Permission
     * @return
     */
    @Operation(summary = "Get all permissions", description = "Retrieves the details of all permissions.")
    @GetMapping
    public ResponseEntity<GlobalResponse<List<PermissionResponseDTO>>> getPermissions() {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), permissionService.getAll()), HttpStatus.OK);
    }

    /**
     * Return the permission according to given Id
     * @return
     */
    @Operation(summary = "Get a permission by ID", description = "Retrieves the details of a permission by its ID.")
    @GetMapping("/{permissionId}")
    public ResponseEntity<GlobalResponse<PermissionResponseDTO>> getPermissionById(@PathVariable UUID permissionId) {
        PermissionResponseDTO permissionResponseDTO = permissionService.findById(permissionId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, permissionResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), permissionResponseDTO, ResponseHandler.convertResponseStatusToError(permissionResponseDTO.getStatus())), responseCode);
    }

    /**
     * Return the permission according to given name
     * @return
     */
    @Operation(summary = "Get a permission by Name", description = "Retrieves the details of a permission by its Name.")
    @GetMapping("search/{permissionName}")
    public ResponseEntity<GlobalResponse<PermissionResponseDTO>> getPermissionByName(@PathVariable String permissionName) {
        PermissionResponseDTO permissionResponseDTO = permissionService.findByName(permissionName);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, permissionResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), permissionResponseDTO, ResponseHandler.convertResponseStatusToError(permissionResponseDTO.getStatus())), responseCode);
    }


}