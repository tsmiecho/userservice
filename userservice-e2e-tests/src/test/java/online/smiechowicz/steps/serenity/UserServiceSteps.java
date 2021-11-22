package online.smiechowicz.steps.serenity;

import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import online.smiechowicz.domain.CreateUserRequest;
import online.smiechowicz.domain.PathConstants;
import online.smiechowicz.domain.UpdateUserRequest;

import static io.restassured.http.ContentType.JSON;
import static net.serenitybdd.rest.RestRequests.given;
import static online.smiechowicz.Constants.*;
import static online.smiechowicz.PropertyHandler.BACKEND_HOST;

public class UserServiceSteps {

    public void createUser(String name, String email){
        Serenity.setSessionVariable(NAME).to(name);
        Serenity.setSessionVariable(EMAIL).to(email);
        final CreateUserRequest userRequest = CreateUserRequest.builder().email(email).name(name).build();
        Serenity.setSessionVariable(CREATE_USER_REQUEST).to(userRequest);
    }

    public void createUserFromSessionParams() {
        final CreateUserRequest userRequest = Serenity.sessionVariableCalled(CREATE_USER_REQUEST);
        Response resp = given()
                .log()
                .all()
                .body(userRequest)
                .contentType(JSON)
                .when()
                .post(BACKEND_HOST + PathConstants.USERS)
                .prettyPeek();
        Serenity.setSessionVariable(RESPONSE).to(resp);
    }

    public void updateUser(String name, String email) {
        Serenity.setSessionVariable(NAME).to(name);
        Serenity.setSessionVariable(EMAIL).to(email);
        final UpdateUserRequest userRequest = UpdateUserRequest.builder().email(email).name(name).build();
        Serenity.setSessionVariable(UPDATE_USER_REQUEST).to(userRequest);
    }

    public void updateUserFromSessionParams() {
        final UpdateUserRequest userRequest = Serenity.sessionVariableCalled(UPDATE_USER_REQUEST);
        final String userId = Serenity.sessionVariableCalled(USER_ID);
        Response resp = given()
                .log()
                .all()
                .body(userRequest)
                .contentType(JSON)
                .when()
                .put(BACKEND_HOST + PathConstants.USER_BY_ID, userId)
                .prettyPeek();
        Serenity.setSessionVariable(RESPONSE).to(resp);
    }

    public void deleteUserFromSessionParams() {
        final String userId = Serenity.sessionVariableCalled(USER_ID);
        Response resp = given()
                .log()
                .all()
                .contentType(JSON)
                .when()
                .delete(BACKEND_HOST + PathConstants.USER_BY_ID, userId)
                .prettyPeek();
        Serenity.setSessionVariable(RESPONSE).to(resp);
    }

    public void fetchUserByIdFromSessionParams() {
        final String userId = Serenity.sessionVariableCalled(USER_ID);
        Response resp = given()
                .log()
                .all()
                .contentType(JSON)
                .when()
                .get(BACKEND_HOST + PathConstants.USER_BY_ID, userId)
                .prettyPeek();
        Serenity.setSessionVariable(RESPONSE).to(resp);
    }

    public void getUsers() {
        Response resp = given()
                .log()
                .all()
                .contentType(JSON)
                .when()
                .get(BACKEND_HOST + PathConstants.USERS)
                .prettyPeek();
        Serenity.setSessionVariable(RESPONSE).to(resp);
    }
}
