package fs19.java.backend.presentation.shared.response;

import fs19.java.backend.presentation.shared.status.ResponseStatus;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Work as response Handler for define error codes
 */
public class ResponseHandler {

    /**
     * Convert repose status to Error
     *
     * @param status
     * @return
     */
    public static List<ErrorItem> convertResponseStatusToError(ResponseStatus status) {
        List<ErrorItem> errors = new ArrayList<>();
        switch (status) {
            case ROLE_NAME_NOT_FOUND ->
                    errors.add(new ErrorItem("Please Define a valid Role name", "" + status.getStatus()));
            case ROLE_ID_NOT_FOUND ->
                    errors.add(new ErrorItem("Please Define a valid Role Id", "" + status.getStatus()));
            case INVALID_INFORMATION_ROLE_DETAILS_NOT_FOUND ->
                    errors.add(new ErrorItem("No Role Result found!", "" + status.getStatus()));
            case RECORD_ALREADY_CREATED ->
                    errors.add(new ErrorItem("Already Result found With Given Name", "" + status.getStatus()));
            case PERMISSION_NAME_NOT_FOUND ->
                    errors.add(new ErrorItem("No Permission Name found!", "" + status.getStatus()));
            case PERMISSION_ID_NOT_FOUND ->
                    errors.add(new ErrorItem("No Permission ID found!", "" + status.getStatus()));
            case INVALID_INFORMATION_PERMISSION_DETAILS_NOT_FOUND ->
                    errors.add(new ErrorItem("No Permission Result found!", "" + status.getStatus()));
            case ROLE_RESULT_NOT_FOUND -> errors.add(new ErrorItem("Role Result not found!", "" + status.getStatus()));
            case PERMISSION_RESULT_NOT_FOUND ->
                    errors.add(new ErrorItem(" Permission Result not found!", "" + status.getStatus()));
            case ROLE_PERMISSION_RESULT_NOT_FOUND ->
                    errors.add(new ErrorItem(" Role-Permission Result not found!", "" + status.getStatus()));
            case ROLE_PERMISSION_ID_NOT_FOUND ->
                    errors.add(new ErrorItem(" Role-Permission Id not found!", "" + status.getStatus()));
            case ROLE_PERMISSION_ID_RECORD_ALREADY_EXIST ->
                    errors.add(new ErrorItem(" Role-Permission Record-Already Exist", "" + status.getStatus()));
            case INVALID_INFORMATION_ROLE_PERMISSION_DETAILS_NOT_FOUND ->
                    errors.add(new ErrorItem(" Invalid Information Role-Permission Record Not Found", "" + status.getStatus()));
            case TASK_NAME_NOT_FOUND -> errors.add(new ErrorItem(" Task Name Not Found", "" + status.getStatus()));
            case TASK_LEVEL_CREATED_USER_NOT_FOUND ->
                    errors.add(new ErrorItem(" Task Created-user Not Found", "" + status.getStatus()));
            case TASK_LEVEL_ASSIGNED_USER_NOT_FOUND ->
                    errors.add(new ErrorItem(" Task Assigned-user Not Found", "" + status.getStatus()));
            case TASK_ID_NOT_FOUND -> errors.add(new ErrorItem(" Task Id Not Found", "" + status.getStatus()));
            case INVALID_INFORMATION_TASK_DETAILS_NOT_FOUND ->
                    errors.add(new ErrorItem(" Task Record Not Found", "" + status.getStatus()));
            case INVITATION_EMAIL_NOT_FOUND ->
                    errors.add(new ErrorItem(" Can't create an Invitation, Email Not Found", "" + status.getStatus()));
            case INVITATION_ROLE_ID_NOT_FOUND ->
                    errors.add(new ErrorItem(" Can't create an Invitation, Role Id Not Found", "" + status.getStatus()));
            case INVITATION_ROLE_NOT_FOUND ->
                    errors.add(new ErrorItem(" Can't create an Invitation, Role Information Not Found", "" + status.getStatus()));
            case INVITATION_COMPANY_NOT_FOUND ->
                    errors.add(new ErrorItem(" Can't create an Invitation, Company Information Not Found", "" + status.getStatus()));
            case INVALID_INFORMATION_INVITATION_DETAILS_NOT_CREATED ->
                    errors.add(new ErrorItem(" Can't create an Invitation, Something missing in request", "" + status.getStatus()));
            case INVITATION_WORKSPACE_ID_NOT_FOUND ->
                    errors.add(new ErrorItem(" Can't create an Invitation, Company Id Not Found", "" + status.getStatus()));
            case INVITATION_ID_NOT_FOUND ->
                    errors.add(new ErrorItem(" Invitation Id Not Found", "" + status.getStatus()));
            case INVALID_INFORMATION_INVITATION_DETAILS_NOT_DELETED ->
                    errors.add(new ErrorItem(" Can't delete an Invitation, Company Information Not Found", "" + status.getStatus()));
            case INVALID_INFORMATION_INVITATION_DETAILS_NOT_FOUND ->
                    errors.add(new ErrorItem(" Invitation Information Not Found", "" + status.getStatus()));
            case COMPANY_ID_NOT_FOUND -> errors.add(new ErrorItem(" Company Id Not Found", "" + status.getStatus()));
            case COMPANY_NAME_NOT_FOUND ->
                    errors.add(new ErrorItem(" Company name Not Found", "" + status.getStatus()));
            case USER_ID_NOT_FOUND -> errors.add(new ErrorItem("User Id Not Found", "" + status.getStatus()));
            case PROJECT_ID_NOT_FOUND -> errors.add(new ErrorItem("Project Id is invalid", "" + status.getStatus()));
            case INVITATION_ALREADY_ACCEPTED ->
                    errors.add(new ErrorItem("Invitation Already Accepted", "" + status.getStatus()));


        }
        return errors;
    }

    /**
     * get response status according to expected code and responses
     *
     * @param expectedCode
     * @param status
     * @return
     */
    public static HttpStatus getResponseCode(HttpStatus expectedCode, ResponseStatus status) {
        switch (status) {
            case ROLE_NAME_NOT_FOUND, ROLE_ID_NOT_FOUND, INVALID_INFORMATION_ROLE_DETAILS_NOT_FOUND,
                 PERMISSION_NAME_NOT_FOUND, INVALID_INFORMATION_PERMISSION_DETAILS_NOT_FOUND,
                 PERMISSION_ID_NOT_FOUND, ROLE_RESULT_NOT_FOUND, PERMISSION_RESULT_NOT_FOUND,
                 ROLE_PERMISSION_RESULT_NOT_FOUND, ROLE_PERMISSION_ID_NOT_FOUND,
                 INVALID_INFORMATION_ROLE_PERMISSION_DETAILS_NOT_FOUND, TASK_NAME_NOT_FOUND,
                 TASK_LEVEL_CREATED_USER_NOT_FOUND, TASK_LEVEL_ASSIGNED_USER_NOT_FOUND, TASK_ID_NOT_FOUND,
                 INVALID_INFORMATION_TASK_DETAILS_NOT_FOUND, INVITATION_EMAIL_NOT_FOUND, INVITATION_ROLE_ID_NOT_FOUND,
                 INVITATION_ROLE_NOT_FOUND, INVITATION_COMPANY_NOT_FOUND,
                 INVALID_INFORMATION_INVITATION_DETAILS_NOT_CREATED, INVITATION_WORKSPACE_ID_NOT_FOUND,
                 INVITATION_ID_NOT_FOUND, INVALID_INFORMATION_INVITATION_DETAILS_NOT_DELETED,
                 INVALID_INFORMATION_INVITATION_DETAILS_NOT_FOUND, COMPANY_ID_NOT_FOUND,
                 COMPANY_NAME_NOT_FOUND, USER_ID_NOT_FOUND, PROJECT_ID_NOT_FOUND -> {

                return HttpStatus.NOT_FOUND;
            }
            case RECORD_ALREADY_CREATED, ROLE_PERMISSION_ID_RECORD_ALREADY_EXIST, INVITATION_ALREADY_ACCEPTED -> {
                return HttpStatus.NOT_ACCEPTABLE;
            }

        }
        return expectedCode;

    }
}
