package fs19.java.backend.presentation.shared.status;

import lombok.Getter;

/**
 * These statuses can use only internal usages, and when it passes to end user please convert it into global status
 */
@Getter
public enum ResponseStatus {

    //success status
    PENDING_PROCESS(0),
    SUCCESSFULLY_SAVED(1),
    SUCCESSFULLY_DELETED(2),
    SUCCESSFULLY_FOUND(3),
    SUCCESSFULLY_CREATED(4),
    SUCCESSFULLY_UPDATED(5),
  
    //Error status
    INVALID_INFORMATION(-1000),
    DELETE_REQUEST_FAILED(-1001),
    ROLE_NAME_NOT_FOUND(-1002),
    ROLE_ID_NOT_FOUND(-1003),
    INVALID_INFORMATION_ROLE_DETAILS_NOT_FOUND(-1004),
    RECORD_ALREADY_CREATED(-1005),
    PERMISSION_NAME_NOT_FOUND(-1006),
    INVALID_INFORMATION_PERMISSION_DETAILS_NOT_FOUND(-1007),
    PERMISSION_ID_NOT_FOUND(-1008),
    ROLE_RESULT_NOT_FOUND(-1009),
    PERMISSION_RESULT_NOT_FOUND(-1010);

    final private int status;

    ResponseStatus(int status) {
        this.status = status;
    }
}

