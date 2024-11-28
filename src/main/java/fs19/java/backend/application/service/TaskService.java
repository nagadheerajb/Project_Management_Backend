package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.task.TaskRequestDTO;
import fs19.java.backend.application.dto.task.TaskResponseDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    TaskResponseDTO create(@Valid TaskRequestDTO taskRequestDTO);
    TaskResponseDTO update(UUID taskId, @Valid TaskRequestDTO taskRequestDTO);
    TaskResponseDTO delete(UUID taskId);
    List<TaskResponseDTO> findAll();
    TaskResponseDTO getById(UUID taskId);
    List<TaskResponseDTO> getByAssignedId(UUID userId);
    List<TaskResponseDTO> getByCreatedUserId(UUID createdUserId);
}


