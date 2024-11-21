# **Project Management App API Docs**
### **Info**
- **Version**: v1

---
### **Servers**
- **Url**: http://localhost:9099
- **Description**: Below Docs helps to understand the Project Management App Documentation

---
### **Paths**
### /v1/api/users/{userId}

**GET**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| userId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**PUT**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| userId | path |  | string | * |

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**DELETE**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| userId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /app/v1/tasks/{taskId}

**GET**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| taskId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**PUT**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| taskId | path |  | string | * |

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**DELETE**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| taskId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /app/v1/roles/{roleId}

**GET**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| roleId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**PUT**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| roleId | path |  | string | * |

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**DELETE**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| roleId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /app/v1/rolePermissions/{rolePermissionId}

**GET**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| rolePermissionId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**PUT**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| rolePermissionId | path |  | string | * |

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**DELETE**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| rolePermissionId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /app/v1/permissions/{permissionId}

**GET**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| permissionId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**PUT**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| permissionId | path |  | string | * |

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**DELETE**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| permissionId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /app/v1/invitations/{invitationId}

**GET**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| invitationId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**PUT**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| invitationId | path |  | string | * |

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**DELETE**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| invitationId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /api/v1/workspaces/{workspaceId}

**GET**

 **Summary**: Get a workspace by ID

**Description**: Retrieves the details of a workspace by its ID.

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| workspaceId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**PUT**

 **Summary**: Update a workspace

**Description**: Updates the details of an existing workspace.

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| workspaceId | path |  | string | * |

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**DELETE**

 **Summary**: Delete a workspace

**Description**: Deletes a workspace by its ID.

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| workspaceId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /api/v1/workspace-users/{workspaceUserId}

**GET**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| workspaceUserId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**PUT**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| workspaceUserId | path |  | string | * |

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**DELETE**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| workspaceUserId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /api/v1/projects/{projectId}

**GET**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| projectId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**PUT**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| projectId | path |  | string | * |

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**DELETE**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| projectId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /api/v1/notifications/{notificationId}

**GET**

 **Summary**: Get a notification by ID

**Description**: Retrieves the details of a notification by its ID.

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| notificationId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**PUT**

 **Summary**: Update a notification

**Description**: Updates the details of an existing notification.

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| notificationId | path |  | string | * |

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**DELETE**

 **Summary**: Delete a notification

**Description**: Deletes a notification by its ID.

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| notificationId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /api/v1/companies/{companyId}

**GET**

 **Summary**: Get a company by ID

**Description**: Retrieves the details of a company by its ID.

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| companyId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**PUT**

 **Summary**: Update a company

**Description**: Updates the details of an existing company.

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| companyId | path |  | string | * |

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**DELETE**

 **Summary**: Delete a company

**Description**: Deletes a company by its ID.

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| companyId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /api/v1/comments/{commentId}

**GET**

 **Summary**: Get a comment by ID

**Description**: Retrieves the details of a comment by its ID

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| commentId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**PUT**

 **Summary**: Update a comment

**Description**: Updates the details of an existing comment

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| commentId | path |  | string | * |

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**DELETE**

 **Summary**: Delete a comment

**Description**: Deletes a comment by its ID

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| commentId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /api/v1/activity-logs/{activityLogId}

**GET**

 **Summary**: Get an activity log by ID

**Description**: Retrieves the details of an activity log by its ID.

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| activityLogId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**PUT**

 **Summary**: Update an activity log

**Description**: Updates an existing activity log entry.

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| activityLogId | path |  | string | * |

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**DELETE**

 **Summary**: Delete an activity log

**Description**: Deletes an activity log by its ID.

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| activityLogId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /v1/api/users

**GET**

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**POST**

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /app/v1/tasks

**GET**

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**POST**

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /app/v1/roles

**GET**

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**POST**

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /app/v1/rolePermissions

**GET**

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**POST**

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /app/v1/permissions

**GET**

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**POST**

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /app/v1/invitations

**GET**

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**POST**

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /api/v1/workspaces

**GET**

 **Summary**: Get all workspaces

**Description**: Retrieves the details of all workspaces.

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**POST**

 **Summary**: Create a workspace

**Description**: Creates a new workspace with the provided details.

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /api/v1/workspace-users

**GET**

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**POST**

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /api/v1/projects

**GET**

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**POST**

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /api/v1/notifications

**GET**

 **Summary**: Get all notifications

**Description**: Retrieves the details of all notifications.

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**POST**

 **Summary**: Create a notification

**Description**: Creates a new notification with the provided details.

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /api/v1/companies

**GET**

 **Summary**: Get all companies

**Description**: Retrieves the details of all companies.

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**POST**

 **Summary**: Create a company

**Description**: Creates a new company with the provided details.

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /api/v1/comments

**GET**

 **Summary**: Get all comments

**Description**: Fetches all comments

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**POST**

 **Summary**: Create a new comment

**Description**: Creates a new comment with the provided details

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /api/v1/activity-logs

**GET**

 **Summary**: Get all activity logs

**Description**: Retrieves the details of all activity logs.

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

**POST**

 **Summary**: Create an activity log

**Description**: Creates a new activity log entry.

**[ Request Body ]** *

- application/json:

    - type: object

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /app/v1/tasks/findByCreated/{createdUserId}

**GET**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| createdUserId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /app/v1/tasks/findByAssigned/{assignedUserId}

**GET**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| assignedUserId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /app/v1/roles/search/{roleName}

**GET**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| roleName | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /app/v1/rolePermissions/{roleId}/{permissionId}

**GET**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| roleId | path |  | string | * |
| permissionId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /app/v1/rolePermissions/findAllRoles/{permissionId}

**GET**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| permissionId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /app/v1/rolePermissions/findAllPermissions/{roleId}

**GET**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| roleId | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object

### /app/v1/permissions/search/{permissionName}

**GET**

**[ Parameters ]**

| name | in | description | type | required |
|:-----|:-----:|:-----|:-----:|:-----:|
| permissionName | path |  | string | * |

**[ Responses ]**

code: 200

description: OK

- */*:

    - type: object


---
### **Components**
### Schemas
**UserReadDTO**

**id**:

- **string**

    - _description: Unique identifier_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**firstName**:

- **string**

    - _description: First name of the user_

    - _format: string_

    - _required: true_

    - _nullable: false_

    - _maxLength: 45_

    - _minLength: 0_

**lastName**:

- **string**

    - _description: Last name of the user_

    - _format: string_

    - _required: true_

    - _nullable: false_

    - _maxLength: 45_

    - _minLength: 0_

**email**:

- **string**

    - _description: Email of the user_

    - _format: string_

    - _required: true_

    - _nullable: false_

**phone**:

- **string**

    - _description: Password of the user_

    - _format: string_

    - _required: false_

    - _nullable: false_

    - _maxLength: 15_

    - _minLength: 10_

**createdDate**:

- **string**

    - _description: Phone number of the user_

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**profileImage**:

- **string**

    - _description: Profile image of the user_

    - _format: string_

    - _required: false_

    - _nullable: false_

    - _maxLength: 2000_

    - _minLength: 0_





**ErrorItem**

**message**:

- **string**

    - _required: false_

    - _nullable: false_

**errorCode**:

- **string**

    - _required: false_

    - _nullable: false_





**GlobalResponseUserReadDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**TaskRequestDTO**

**id**:

- **string**

    - _description: Unique identifier_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**name**:

- **string**

    - _description: Task name defines here_

    - _format: string_

    - _required: false_

    - _nullable: false_

**description**:

- **string**

    - _description: Task description define here_

    - _format: string_

    - _required: false_

    - _nullable: false_

**createdDate**:

- **string**

    - _description: Task created date define here_

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**resolvedDate**:

- **string**

    - _description: Task resolved date define here_

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**dueDate**:

- **string**

    - _description: Task due date define here_

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**attachments**:

- **string**

    - _description: Task attachments can add here_

    - _format: Object_

    - _required: false_

    - _nullable: false_

**taskStatus**:

- **string**

    - _description: TaskStatus define here_

    - _format: Enum_

    - _required: false_

    - _nullable: false_

    - _enum: [ TODO, IN_DEVELOPMENT, COMPLETE, RELEASED ]_

**projectId**:

- **string**

    - _description: Unique project id define here_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**createdUserId**:

- **string**

    - _description: Unique created user id define here_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**assignedUserId**:

- **string**

    - _description: Unique assign user id define here_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**priority**:

- **string**

    - _description: Task priority define here_

    - _format: Enum_

    - _required: false_

    - _nullable: false_

    - _enum: [ LOW_PRIORITY, MEDIUM_PRIORITY, HIGH_PRIORITY ]_





**GlobalResponseTaskResponseDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**TaskResponseDTO**

**id**:

- **string**

    - _description: Unique identifier_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**name**:

- **string**

    - _description: Task name defines here_

    - _format: string_

    - _required: true_

    - _nullable: false_

    - _maxLength: 45_

    - _minLength: 1_

**description**:

- **string**

    - _description: Task description define here_

    - _format: string_

    - _required: false_

    - _nullable: false_

**createdDate**:

- **string**

    - _description: Task created date define here_

    - _format: date-time_

    - _required: true_

    - _nullable: false_

**resolvedDate**:

- **string**

    - _description: Task resolved date define here_

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**dueDate**:

- **string**

    - _description: Task due date define here_

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**attachment**:

- **string**

    - _description: Task attachments can add here_

    - _format: Object_

    - _required: false_

    - _nullable: false_

**taskStatus**:

- **string**

    - _description: TaskStatus define here_

    - _format: Enum_

    - _required: false_

    - _nullable: false_

    - _enum: [ TODO, IN_DEVELOPMENT, COMPLETE, RELEASED ]_

**projectId**:

- **string**

    - _description: Unique project id define here_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**createdUserId**:

- **string**

    - _description: Unique created user id define here_

    - _format: uuid_

    - _required: true_

    - _nullable: false_

**assignedUserId**:

- **string**

    - _description: Unique assign user id define here_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**priority**:

- **string**

    - _description: Task priority define here_

    - _format: Enum_

    - _required: false_

    - _nullable: false_

    - _enum: [ LOW_PRIORITY, MEDIUM_PRIORITY, HIGH_PRIORITY ]_

**status**:

- **string**

    - _description: Unique system status_

    - _format: ResponseStatus_

    - _required: false_

    - _nullable: false_





**RoleRequestDTO**

**id**:

- **string**

    - _description: Unique identifier_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**name**:

- **string**

    - _description: System roles name defines here_

    - _format: string_

    - _required: false_

    - _nullable: false_

**companyId**:

- **string**

    - _description: Company Id defines here_

    - _format: uuid_

    - _required: true_

    - _nullable: false_





**GlobalResponseRoleResponseDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**RoleResponseDTO**

**id**:

- **string**

    - _description: Unique identifier_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**name**:

- **string**

    - _required: true_

    - _nullable: false_

    - _maxLength: 45_

    - _minLength: 1_

**createdDate**:

- **string**

    - _format: date-time_

    - _required: true_

    - _nullable: false_

**companyId**:

- **string**

    - _description: Company Id defines here_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**status**:

- **string**

    - _description: Unique system status_

    - _format: ResponseStatus_

    - _required: false_

    - _nullable: false_





**RolePermissionRequestDTO**

**id**:

- **string**

    - _description: Unique identifier_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**permissionId**:

- **string**

    - _description: Unique identifier for permission-Id_

    - _format: uuid_

    - _required: true_

    - _nullable: false_

**roleId**:

- **string**

    - _description: Unique identifier for role-Id_

    - _format: uuid_

    - _required: true_

    - _nullable: false_





**GlobalResponseRolePermissionResponseDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**RolePermissionResponseDTO**

**id**:

- **string**

    - _description: Unique identifier_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**roleId**:

- **string**

    - _description: Role Unique identifier _

    - _format: uuid_

    - _required: true_

    - _nullable: false_

**permissionId**:

- **string**

    - _description: Permission Unique identifier_

    - _format: uuid_

    - _required: true_

    - _nullable: false_

**status**:

- **string**

    - _description: Unique system status_

    - _format: ResponseStatus_

    - _required: false_

    - _nullable: false_





**PermissionRequestDTO**

**id**:

- **string**

    - _description: Unique identifier_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**name**:

- **string**

    - _description: permission name defines here_

    - _format: string_

    - _required: false_

    - _nullable: false_





**GlobalResponsePermissionResponseDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**PermissionResponseDTO**

**id**:

- **string**

    - _description: Unique identifier_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**name**:

- **string**

    - _required: true_

    - _nullable: false_

    - _maxLength: 45_

    - _minLength: 1_

**status**:

- **string**

    - _description: Unique system status_

    - _format: ResponseStatus_

    - _required: false_

    - _nullable: false_





**InvitationRequestDTO**

**id**:

- **string**

    - _description: Unique identifier_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**expiredAt**:

- **string**

    - _description: invitation expired date define here_

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**email**:

- **string**

    - _description: invitation sending email_

    - _format: String_

    - _required: true_

    - _nullable: false_

**roleId**:

- **string**

    - _description: invitation sending role Id_

    - _format: uuid_

    - _required: true_

    - _nullable: false_

**companyId**:

- **string**

    - _description: invitation sending company Id_

    - _format: uuid_

    - _required: true_

    - _nullable: false_

**accepted**:

- **boolean**

    - _required: false_

    - _nullable: false_





**GlobalResponseInvitationResponseDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**InvitationResponseDTO**

**id**:

- **string**

    - _description: Unique identifier_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**expiredAt**:

- **string**

    - _description: invitation expired date define here_

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**email**:

- **string**

    - _description: invitation sending email_

    - _format: String_

    - _required: true_

    - _nullable: false_

**roleId**:

- **string**

    - _description: invitation sending role Id_

    - _format: uuid_

    - _required: true_

    - _nullable: false_

**companyId**:

- **string**

    - _description: invitation sending company Id_

    - _format: uuid_

    - _required: true_

    - _nullable: false_

**status**:

- **string**

    - _description: Unique system status_

    - _format: ResponseStatus_

    - _required: false_

    - _nullable: false_

**accepted**:

- **boolean**

    - _required: false_

    - _nullable: false_





**WorkspaceUpdateDTO**

**name**:

- **string**

    - _description: Name of the workspace_

    - _format: string_

    - _required: false_

    - _nullable: false_

**description**:

- **string**

    - _description: Description of the workspace_

    - _format: string_

    - _required: false_

    - _nullable: false_

**type**:

- **string**

    - _description: Type of the workspace_

    - _format: string_

    - _required: false_

    - _nullable: false_

**companyId**:

- **string**

    - _description: Unique identifier of the company_

    - _format: uuid_

    - _required: false_

    - _nullable: false_





**GlobalResponseWorkspaceResponseDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**WorkspaceResponseDTO**

**id**:

- **string**

    - _description: Unique identifier of the workspace_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**name**:

- **string**

    - _description: Name of the workspace_

    - _format: string_

    - _required: false_

    - _nullable: false_

**description**:

- **string**

    - _description: Description of the workspace_

    - _format: string_

    - _required: false_

    - _nullable: false_

**type**:

- **string**

    - _description: Type of the workspace_

    - _format: string_

    - _required: false_

    - _nullable: false_

**createdDate**:

- **string**

    - _description: Creation date of the workspace_

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**createdBy**:

- **string**

    - _description: Unique identifier of the user who created the workspace_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**companyId**:

- **string**

    - _description: Unique identifier of the company_

    - _format: uuid_

    - _required: false_

    - _nullable: false_





**WorkspaceUsersRequestDTO**

**id**:

- **string**

    - _description: Unique identifier_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**roleId**:

- **string**

    - _description: Unique identifier for role-Id_

    - _format: uuid_

    - _required: true_

    - _nullable: false_

**workspaceId**:

- **string**

    - _description: Unique identifier for workspace-Id_

    - _format: uuid_

    - _required: true_

    - _nullable: false_

**userId**:

- **string**

    - _description: Unique identifier for user-Id_

    - _format: uuid_

    - _required: true_

    - _nullable: false_





**GlobalResponseWorkspaceUsersResponseDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**WorkspaceUsersResponseDTO**

**id**:

- **string**

    - _description: Unique identifier_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**roleId**:

- **string**

    - _description: Unique identifier for role-Id_

    - _format: uuid_

    - _required: true_

    - _nullable: false_

**workspaceId**:

- **string**

    - _description: Unique identifier for workspace-Id_

    - _format: uuid_

    - _required: true_

    - _nullable: false_

**userId**:

- **string**

    - _description: Unique identifier for user-Id_

    - _format: uuid_

    - _required: true_

    - _nullable: false_





**ProjectUpdateDTO**

**description**:

- **string**

    - _required: false_

    - _nullable: false_

**startDate**:

- **string**

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**endDate**:

- **string**

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**status**:

- **boolean**

    - _required: false_

    - _nullable: false_





**GlobalResponseProjectReadDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**ProjectReadDTO**

**id**:

- **string**

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**name**:

- **string**

    - _required: false_

    - _nullable: false_

**description**:

- **string**

    - _required: false_

    - _nullable: false_

**createdDate**:

- **string**

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**startDate**:

- **string**

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**endDate**:

- **string**

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**createdByUserId**:

- **string**

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**workspaceId**:

- **string**

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**status**:

- **boolean**

    - _required: false_

    - _nullable: false_





**NotificationDTO**

**id**:

- **string**

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**content**:

- **string**

    - _required: true_

    - _nullable: false_

    - _maxLength: 255_

    - _minLength: 1_

**notifyType**:

- **string**

    - _required: true_

    - _nullable: false_

    - _enum: [ PROJECT_INVITATION, PROJECT_CREATED, PROJECT_UPDATED ]_

**createdDate**:

- **string**

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**projectId**:

- **string**

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**mentionedBy**:

- **string**

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**mentionedTo**:

- **string**

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**read**:

- **boolean**

    - _required: false_

    - _nullable: false_





**GlobalResponseNotificationDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**CompanyDTO**

**id**:

- **string**

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**name**:

- **string**

    - _required: true_

    - _nullable: false_

    - _maxLength: 255_

    - _minLength: 1_

**createdDate**:

- **string**

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**createdBy**:

- **string**

    - _format: uuid_

    - _required: true_

    - _nullable: false_





**GlobalResponseCompanyDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**CommentUpdateDTO**

**content**:

- **string**

    - _description: The content of the comment_

    - _required: true_

    - _nullable: false_





**CommentResponseDTO**

**id**:

- **string**

    - _description: Unique identifier of the comment_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**taskId**:

- **string**

    - _description: The task id_

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**content**:

- **string**

    - _description: The content of the comment_

    - _format: string_

    - _required: false_

    - _nullable: false_

**createdDate**:

- **string**

    - _description: The created date of the comment_

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**createdBy**:

- **string**

    - _description: Unique identifier of the user who created the comment_

    - _format: uuid_

    - _required: false_

    - _nullable: false_





**GlobalResponseCommentResponseDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**ActivityLogDTO**

**id**:

- **string**

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**entityType**:

- **string**

    - _required: false_

    - _nullable: false_

    - _enum: [ USER, PROJECT, TASK, COMMENT, NOTIFICATION ]_

**entityId**:

- **string**

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**action**:

- **string**

    - _required: false_

    - _nullable: false_

    - _enum: [ CREATED, UPDATED, DELETED, VIEWED, MENTIONED ]_

**createdDate**:

- **string**

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**userId**:

- **string**

    - _format: uuid_

    - _required: false_

    - _nullable: false_





**GlobalResponseActivityLogDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**UserCreateDTO**

**firstName**:

- **string**

    - _description: First name of the user_

    - _format: string_

    - _required: true_

    - _nullable: false_

    - _maxLength: 45_

    - _minLength: 0_

**lastName**:

- **string**

    - _description: Last name of the user_

    - _format: string_

    - _required: true_

    - _nullable: false_

    - _maxLength: 45_

    - _minLength: 0_

**email**:

- **string**

    - _description: Email of the user_

    - _format: string_

    - _required: true_

    - _nullable: false_

**password**:

- **string**

    - _description: Password of the user_

    - _format: string_

    - _required: true_

    - _nullable: false_

    - _maxLength: 2147483647_

    - _minLength: 8_

**phone**:

- **string**

    - _description: Phone number of the user_

    - _format: string_

    - _required: false_

    - _nullable: false_

    - _maxLength: 15_

    - _minLength: 10_

**profileImage**:

- **string**

    - _description: Profile image of the user_

    - _format: string_

    - _required: false_

    - _nullable: false_

    - _maxLength: 2000_

    - _minLength: 0_





**WorkspaceRequestDTO**

**name**:

- **string**

    - _description: Name of the workspace_

    - _format: string_

    - _required: true_

    - _nullable: false_

    - _maxLength: 45_

    - _minLength: 3_

**description**:

- **string**

    - _description: Description of the workspace_

    - _format: string_

    - _required: false_

    - _nullable: false_

    - _maxLength: 100_

    - _minLength: 0_

**type**:

- **string**

    - _description: Type of the workspace_

    - _format: string_

    - _required: true_

    - _nullable: false_

**createdBy**:

- **string**

    - _description: Unique identifier of the user who created the workspace_

    - _format: uuid_

    - _required: true_

    - _nullable: false_

**companyId**:

- **string**

    - _description: Unique identifier of the company_

    - _format: uuid_

    - _required: true_

    - _nullable: false_





**ProjectCreateDTO**

**id**:

- **string**

    - _format: uuid_

    - _required: false_

    - _nullable: false_

**name**:

- **string**

    - _description: Name of the project_

    - _format: string_

    - _required: true_

    - _nullable: false_

    - _maxLength: 45_

    - _minLength: 3_

**description**:

- **string**

    - _description: Description of the project_

    - _format: string_

    - _required: false_

    - _nullable: false_

    - _maxLength: 100_

    - _minLength: 0_

**startDate**:

- **string**

    - _description: Start date of the project_

    - _format: date-time_

    - _required: true_

    - _nullable: false_

**endDate**:

- **string**

    - _description: End date of the project_

    - _format: date-time_

    - _required: false_

    - _nullable: false_

**status**:

- **boolean**

    - _required: false_

    - _nullable: false_





**CommentRequestDTO**

**taskId**:

- **string**

    - _description: The task id_

    - _format: uuid_

    - _required: true_

    - _nullable: false_

**content**:

- **string**

    - _description: The content of the comment_

    - _required: true_

    - _nullable: false_

**createdBy**:

- **string**

    - _description: Unique identifier of the user who created the comment_

    - _format: uuid_

    - _required: true_

    - _nullable: false_





**GlobalResponseListUserReadDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**GlobalResponseListTaskResponseDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**GlobalResponseListRoleResponseDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**GlobalResponseListRolePermissionResponseDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**GlobalResponseListPermissionResponseDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**GlobalResponseListInvitationResponseDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**GlobalResponseListWorkspaceResponseDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**GlobalResponseListWorkspaceUsersResponseDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**GlobalResponseListProjectReadDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**GlobalResponseListNotificationDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**GlobalResponseListCompanyDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**GlobalResponseListCommentResponseDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**GlobalResponseListActivityLogDTO**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**GlobalResponseVoid**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **object**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_





**GlobalResponseString**

**status**:

- **string**

    - _required: false_

    - _nullable: false_

**code**:

- **integer**

    - _format: int32_

    - _required: false_

    - _nullable: false_

**data**:

- **string**

    - _required: false_

    - _nullable: false_

**errors**:

- **object**

    - _required: false_

    - _nullable: false_






---
