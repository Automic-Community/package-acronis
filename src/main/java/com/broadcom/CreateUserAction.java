package com.broadcom;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.exceptions.AcronisException;

/**
 * This class used to create a user.
 */
@Action(title = "Create User", name = "CREATE_USER", path = "ACRONIS")
public class CreateUserAction extends AbstractAcronisAction {

    @ActionInputParam(name = "UC4RB_AC_TENANT_ID", required = true, tooltip = "Provide the Tenant id. E.g: "
            + "6f2e420b-bd8c-4ade-b3bb-4942d7c89032", label = "Tenant Id")
    String tenantId;

    @ActionInputParam(name = "UC4RB_AC_USER_LOGIN", required = true, tooltip = "A login of the user account. E.g: "
            + "test@gmail.com", label = "Login")
    String login;

    @ActionInputParam(name = "UC4RB_AC_USER_EMAIL", required = true, tooltip =
            "A login of the user accountThe contact information of an account. Requires the email parameter. E.g: "
                    + "test@gmail.com", label = "Email")
    String email;

    @ActionInputParam(name = "UC4RB_AC_USER_FIRST_NAME", tooltip = "Provide the user fisrt name. E.g: test", label =
            "First Name")
    String userFirstName;

    @ActionInputParam(name = "UC4RB_AC_USER_LAST_NAME", tooltip = "Provide the user last name. E.g: test", label =
            "Last Name")
    String userLASTName;

    @ActionInputParam(name = "UC4RB_AC_USER_LANGUAGE", tooltip = "Language", label = "Language")
    String language = "English";

    @Override
    protected void executeSpecific() throws AcronisException {

    }

    @Override
    protected String getActionName() {
        return null;
    }

}
