package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.RolePermissionServiceImpl;
import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
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

@Tag(name = "Role-Permission", description = "Manage Role-Permission")
@RestController
@RequestMapping("app/v1/rolePermissions")
public class RolePermissionController {

    @Autowired
    private RolePermissionServiceImpl rolePermissionService;

    /**
     * Assign permission to role
     *
     * @param rolePermissionRequestDTO
     * @return
     */
    @Operation(summary = "Create a role-permission", description = "Creates a new role-permission with the provided details.")
    @PostMapping
    public ResponseEntity<GlobalResponse<RolePermissionResponseDTO>> assignPermissionToRole(@RequestBody @Valid RolePermissionRequestDTO rolePermissionRequestDTO) {
        RolePermissionResponseDTO theResponse = rolePermissionService.create(rolePermissionRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.CREATED, theResponse.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theResponse, ResponseHandler.convertResponseStatusToError(theResponse.getStatus())), responseCode);
    }

    /**
     * Role permission Id
     *
     * @param rolePermissionId         UUID
     * @param rolePermissionRequestDTO RolePermissionRequestDTO
     * @return
     */
    @Operation(summary = "Update a role-permission", description = "Updates the details of an existing role-permission.")
    @PutMapping("/{rolePermissionId}")
    public ResponseEntity<GlobalResponse<RolePermissionResponseDTO>> updateRolePermissionId(@PathVariable UUID rolePermissionId, @RequestBody @Valid RolePermissionRequestDTO rolePermissionRequestDTO) {
        RolePermissionResponseDTO theRoleResponseDTO = rolePermissionService.update(rolePermissionId, rolePermissionRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, theRoleResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theRoleResponseDTO, ResponseHandler.convertResponseStatusToError(theRoleResponseDTO.getStatus())), responseCode);
    }

    /**
     * Delete Role-Permission By Id
     *
     * @param rolePermissionId
     * @return
     */
    @Operation(summary = "Delete a role-permission", description = "Deletes a role-permission by its ID.")
    @DeleteMapping("/{rolePermissionId}")
    public ResponseEntity<GlobalResponse<RolePermissionResponseDTO>> deleteRolePermissionById(@PathVariable UUID rolePermissionId) {
        RolePermissionResponseDTO roleResponseDTO = rolePermissionService.delete(rolePermissionId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, roleResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), roleResponseDTO, ResponseHandler.convertResponseStatusToError(roleResponseDTO.getStatus())), responseCode);
    }


    /**
     * Return the role-Permission
     *
     * @return
     */
    @Operation(summary = "Get all role-permissions", description = "Retrieves the details of all role-permissions.")
    @GetMapping
    public ResponseEntity<GlobalResponse<List<RolePermissionResponseDTO>>> getRolePermissions() {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), rolePermissionService.findAll()), HttpStatus.OK);
    }


    /**
     * Return the role-permission according to given Id
     *
     * @return
     */
    @Operation(summary = "Get role-permissions by id", description = "Retrieves the details of all role-permissions by id.")
    @GetMapping("/{rolePermissionId}")
    public ResponseEntity<GlobalResponse<RolePermissionResponseDTO>> getRolePermissionById(@PathVariable UUID rolePermissionId) {
        RolePermissionResponseDTO roleResponseDTO = rolePermissionService.findByPermissionId(rolePermissionId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, roleResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), roleResponseDTO, ResponseHandler.convertResponseStatusToError(roleResponseDTO.getStatus())), responseCode);
    }


    /**
     * Return the role-permission according to given Id
     *
     * @return
     */
    @Operation(summary = "Get role-permissions by roleId and PermissionId", description = "Retrieves the details of all role-permissions by role and permission.")
    @GetMapping("/{roleId}/{permissionId}")
    public ResponseEntity<GlobalResponse<RolePermissionResponseDTO>> getExistingRecord(@PathVariable UUID roleId, @PathVariable UUID permissionId) {
        RolePermissionResponseDTO roleResponseDTO = rolePermissionService.findByRoleIdAndPermissionId(roleId, permissionId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, roleResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), roleResponseDTO, ResponseHandler.convertResponseStatusToError(roleResponseDTO.getStatus())), responseCode);
    }


    /**
     * Return the permission according to given permission-Id
     *
     * @return
     */
    @Operation(summary = "Get role-permissions by PermissionId", description = "Retrieves the details of all role-permissions by permission.")
    @GetMapping("findAllRoles/{permissionId}")
    public ResponseEntity<GlobalResponse<List<RolePermissionResponseDTO>>> getRolePermissionByPermissionId(@PathVariable UUID permissionId) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), rolePermissionService.findById(permissionId)), HttpStatus.OK);
    }

    /**
     * Return the roles according to given rolesId
     *
     * @return
     */
    @Operation(summary = "Get role-permissions by roleId", description = "Retrieves the details of all role-permissions by.")
    @GetMapping("findAllPermissions/{roleId}")
    public ResponseEntity<GlobalResponse<List<RolePermissionResponseDTO>>> getRolePermissionByRoleId(@PathVariable UUID roleId) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), rolePermissionService.findByRoleId(roleId)), HttpStatus.OK);
    }


}
