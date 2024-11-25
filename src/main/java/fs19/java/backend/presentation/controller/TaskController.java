package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.TaskServiceImpl;
import fs19.java.backend.application.dto.task.TaskRequestDTO;
import fs19.java.backend.application.dto.task.TaskResponseDTO;
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

@RestController
@Tag(name = "Task", description = "Manage Tasks")
@RequestMapping("app/v1/tasks")
public class TaskController {


    @Autowired
    private TaskServiceImpl taskService;


    @Operation(summary = "Create a Task", description = "Creates a new task with the provided details.")
    @PostMapping
    public ResponseEntity<GlobalResponse<TaskResponseDTO>> createTask(@RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO theTaskResponse = taskService.create(taskRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.CREATED, theTaskResponse.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theTaskResponse, ResponseHandler.convertResponseStatusToError(theTaskResponse.getStatus())), responseCode);
    }


    /**
     * Update a task by id
     *
     * @param taskId
     * @return
     */
    @Operation(summary = "Update a task", description = "Updates the details of an existing task.")
    @PutMapping("/{taskId}")
    public ResponseEntity<GlobalResponse<TaskResponseDTO>> updateTask(@PathVariable UUID taskId, @RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO theTaskResponse = taskService.update(taskId, taskRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, theTaskResponse.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theTaskResponse, ResponseHandler.convertResponseStatusToError(theTaskResponse.getStatus())), responseCode);
    }

    /**
     * Delete Task By Id
     *
     * @param taskId
     * @return
     */
    @Operation(summary = "Delete a task", description = "Deletes a task by its ID.")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<GlobalResponse<TaskResponseDTO>> deleteTaskById(@PathVariable UUID taskId) {
        TaskResponseDTO theTaskResponse = taskService.delete(taskId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, theTaskResponse.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theTaskResponse, ResponseHandler.convertResponseStatusToError(theTaskResponse.getStatus())), responseCode);
    }

    /**
     * Return the all tasks
     *
     * @return
     */
    @Operation(summary = "Get all tasks", description = "Retrieves the details of all tasks.")
    @GetMapping
    public ResponseEntity<GlobalResponse<List<TaskResponseDTO>>> getTasks() {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), taskService.findAll()), HttpStatus.OK);
    }


    /**
     * Return the task according to given Id
     *
     * @return
     */
    @Operation(summary = "Get a task by ID", description = "Retrieves the details of a task by its ID.")
    @GetMapping("/{taskId}")
    public ResponseEntity<GlobalResponse<TaskResponseDTO>> getTaskById(@PathVariable UUID taskId) {
        TaskResponseDTO theTaskResponse = taskService.getById(taskId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, theTaskResponse.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theTaskResponse, ResponseHandler.convertResponseStatusToError(theTaskResponse.getStatus())), responseCode);
    }

    /**
     * return tasks according to assigned userId
     * @param assignedUserId
     * @return
     */
    @Operation(summary = "Get a tasks by assigned-user-Id", description = "Retrieves the details of a tasks by assigned user.")
    @GetMapping("findByAssigned/{assignedUserId}")
    public ResponseEntity<GlobalResponse<List<TaskResponseDTO>>> getTasksByAssignedUserId(@PathVariable UUID assignedUserId) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), taskService.getByAssignedId(assignedUserId)), HttpStatus.OK);
    }

    /**
     * return tasks according to created userId
     * @param createdUserId
     * @return
     */
    @Operation(summary = "Get a tasks by created-user-Id", description = "Retrieves the details of a tasks by created user.")
    @GetMapping("findByCreated/{createdUserId}")
    public ResponseEntity<GlobalResponse<List<TaskResponseDTO>>> getTasksByCreatedUserId(@PathVariable UUID createdUserId) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), taskService.getByCreatedUserId(createdUserId)), HttpStatus.OK);
    }


}
