package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.dto.activitylog.ActivityLogDTO;
import fs19.java.backend.application.service.ActivityLogService;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "Activity Logs", description = "Manage activity logs")
@RestController
@RequestMapping("/api/v1/activity-logs")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @Operation(summary = "Create an activity log", description = "Creates a new activity log entry.")
    @PostMapping
    public ResponseEntity<GlobalResponse<ActivityLogDTO>> createActivityLog(@Valid @RequestBody ActivityLogDTO activityLogDTO) {
        ActivityLogDTO createdActivityLog = activityLogService.createActivityLog(activityLogDTO);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), createdActivityLog), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an activity log", description = "Updates an existing activity log entry.")
    @PutMapping("/{activityLogId}")
    public ResponseEntity<GlobalResponse<ActivityLogDTO>> updateActivityLog(@PathVariable UUID activityLogId, @Valid @RequestBody ActivityLogDTO activityLogDTO) {
        ActivityLogDTO updatedActivityLog = activityLogService.updateActivityLog(activityLogId, activityLogDTO);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), updatedActivityLog), HttpStatus.OK);
    }

    @Operation(summary = "Get an activity log by ID", description = "Retrieves the details of an activity log by its ID.")
    @GetMapping("/{activityLogId}")
    public ResponseEntity<GlobalResponse<ActivityLogDTO>> getActivityLogById(@PathVariable UUID activityLogId) {
        ActivityLogDTO activityLog = activityLogService.getActivityLogById(activityLogId);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), activityLog), HttpStatus.OK);
    }

    @Operation(summary = "Get all activity logs", description = "Retrieves the details of all activity logs.")
    @GetMapping
    public ResponseEntity<GlobalResponse<List<ActivityLogDTO>>> getAllActivityLogs() {
        List<ActivityLogDTO> activityLogs = activityLogService.getAllActivityLogs();
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), activityLogs), HttpStatus.OK);
    }

    @Operation(summary = "Delete an activity log", description = "Deletes an activity log by its ID.")
    @DeleteMapping("/{activityLogId}")
    public ResponseEntity<GlobalResponse<Void>> deleteActivityLog(@PathVariable UUID activityLogId) {
        activityLogService.deleteActivityLog(activityLogId);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), null), HttpStatus.NO_CONTENT);
    }
}