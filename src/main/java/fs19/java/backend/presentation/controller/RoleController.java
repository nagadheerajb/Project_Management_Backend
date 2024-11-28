package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.RoleServiceImpl;
import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.application.dto.role.RoleResponseDTO;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import fs19.java.backend.presentation.shared.response.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * This class will work as the main role entity controller and each method will work as endpoints
 */

@Tag(name = "Role", description = "Manage Roles")
@RestController
@RequestMapping("api/v1/roles")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    /**
     * Create a role object using the name
     *
     * @param roleRequestDTO
     * @return
     */
    @Operation(summary = "Create a role", description = "Creates a new role with the provided details.")
    @PostMapping
    public ResponseEntity<GlobalResponse<RoleResponseDTO>> createRole(@RequestBody @Valid RoleRequestDTO roleRequestDTO) {
        RoleResponseDTO theRoleResponse = roleService.save(roleRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.CREATED, theRoleResponse.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theRoleResponse, ResponseHandler.convertResponseStatusToError(theRoleResponse.getStatus())), responseCode);
    }

    /**
     * Update a role by id
     *
     * @param roleUpdateDTO
     * @return
     */
    @Operation(summary = "Update a role", description = "Updates the details of an existing role.")
    @PutMapping("/{roleId}")
    public ResponseEntity<GlobalResponse<RoleResponseDTO>> updateRole(@PathVariable @Valid @NotNull(message = "RoleId cannot be null") UUID roleId, @RequestBody @Valid RoleRequestDTO roleUpdateDTO) {
        RoleResponseDTO theRoleResponseDTO = roleService.update(roleId, roleUpdateDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, theRoleResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theRoleResponseDTO, ResponseHandler.convertResponseStatusToError(theRoleResponseDTO.getStatus())), responseCode);
    }

    /**
     * Delete Role By Id
     *
     * @param roleId
     * @return
     */
    @Operation(summary = "Delete a role", description = "Deletes a role by its ID.")
    @DeleteMapping("/{roleId}")
    public ResponseEntity<GlobalResponse<RoleResponseDTO>> deleteRoleById(@PathVariable @Valid @NotNull(message = "RoleId cannot be null") UUID roleId) {
        RoleResponseDTO roleResponseDTO = roleService.delete(roleId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, roleResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), roleResponseDTO, ResponseHandler.convertResponseStatusToError(roleResponseDTO.getStatus())), responseCode);
    }

    /**
     * Return the role
     *
     * @return
     */
    @Operation(summary = "Get all roles", description = "Retrieves the details of all roles.")
    @GetMapping
    public ResponseEntity<GlobalResponse<List<RoleResponseDTO>>> getRoles() {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), roleService.findAll()), HttpStatus.OK);
    }

    /**
     * Return the role according to given Id
     *
     * @return
     */
    @Operation(summary = "Get a role by ID", description = "Retrieves the details of a role by its ID.")
    @GetMapping("/{roleId}")
    public ResponseEntity<GlobalResponse<RoleResponseDTO>> getRoleById(@PathVariable @Valid @NotNull(message = "RoleId cannot be null") UUID roleId) {
        RoleResponseDTO roleResponseDTO = roleService.findById(roleId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, roleResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), roleResponseDTO, ResponseHandler.convertResponseStatusToError(roleResponseDTO.getStatus())), responseCode);
    }

    /**
     * Return the role according to given name
     *
     * @return
     */
    @Operation(summary = "Get a role by Name", description = "Retrieves the details of a role by its Name.")
    @GetMapping("search/{roleName}")
    public ResponseEntity<GlobalResponse<RoleResponseDTO>> getRoleByName(@PathVariable @Valid @NotNull(message = "RoleName cannot be null") String roleName) {
        RoleResponseDTO roleResponseDTO = roleService.findByName(roleName);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, roleResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), roleResponseDTO, ResponseHandler.convertResponseStatusToError(roleResponseDTO.getStatus())), responseCode);
    }


}