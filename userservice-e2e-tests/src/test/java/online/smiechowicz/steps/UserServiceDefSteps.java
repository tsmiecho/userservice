package online.smiechowicz.steps;

import online.smiechowicz.steps.serenity.UserServiceSteps;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;
import online.smiechowicz.domain.UpdateUserRequest;
import online.smiechowicz.domain.User;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.List;
import java.util.UUID;

import static online.smiechowicz.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;


public class UserServiceDefSteps {

    @Steps
    private UserServiceSteps userServiceSteps;

    @Given("a user is prepared")
    public void givenUserIsCreated() {
        userServiceSteps.createUser("Cezary", UUID.randomUUID() + "@gmail.com");
    }

    @Given("a user with the same email is prepared")
    public void givenUserWithTheSameEmailIsCreated() {
        final String email = Serenity.sessionVariableCalled("email");
        userServiceSteps.createUser("Cezary", email);
    }

    @Given("delete request is prepared")
    public void givenDeleteRequestIsCreated() {
        // empty step
    }

    @Given("a request to fetch user by id is created")
    public void givenFetchByIdRequestIsCreated() {
        // empty step
    }

    @Given("update of the user is prepared")
    public void givenSecondUserIsCreated() {
        userServiceSteps.updateUser("Janusz", UUID.randomUUID() + "@gmail.com");
    }

    @When("request to save user")
    public void whenDirectRequestToSaveUser() {
        userServiceSteps.createUserFromSessionParams();
    }

    @When("request to get users is sent")
    public void whenDirectRequestToGetUsers() {
        userServiceSteps.getUsers();
    }

    @When("request to update the user is sent")
    public void whenRequestToUpdateUser() {
        userServiceSteps.updateUserFromSessionParams();
    }

    @When("request to delete the user is sent")
    public void whenDirectRequestToDeleteUser() {
        userServiceSteps.deleteUserFromSessionParams();
    }

    @When("request to fetch user by id")
    public void whenDirectRequestToFetchUserById() {
        userServiceSteps.fetchUserByIdFromSessionParams();
    }

    @Then("response contains valid user")
    public void thenResponseContainsValidUser() {
        Response resp = Serenity.sessionVariableCalled(RESPONSE);
        User user = resp.getBody().as(User.class);
        Serenity.setSessionVariable(USER_ID).to(user.getId());
        String name = Serenity.sessionVariableCalled(NAME);
        String email = Serenity.sessionVariableCalled(EMAIL);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getId()).isNotBlank();
    }

    @Then("response contains no content status")
    public void thenResponseContainsNoContentStatus() {
        Response resp = Serenity.sessionVariableCalled(RESPONSE);
        assertThat(resp.statusCode()).isEqualTo(204);
        assertThat(resp.getBody().print()).isBlank();
    }

    @Then("response contains conflict status")
    public void thenResponseContainsConflictStatus() {
        Response resp = Serenity.sessionVariableCalled(RESPONSE);
        assertThat(resp.statusCode()).isEqualTo(409);
        assertThat(resp.getBody().print()).isNotBlank();
    }

    @Then("response contains not found status")
    public void thenResponseContainsNotFoundStatus() {
        Response resp = Serenity.sessionVariableCalled(RESPONSE);
        assertThat(resp.statusCode()).isEqualTo(404);
        assertThat(resp.getBody().print()).isBlank();
    }

    @Then("response contains the updated user")
    public void thenResponseContainsUpdatedUser() {
        Response resp = Serenity.sessionVariableCalled(RESPONSE);
        User user = resp.getBody().as(User.class);
        UpdateUserRequest userRequest = Serenity.sessionVariableCalled(UPDATE_USER_REQUEST);
        String userId = Serenity.sessionVariableCalled(USER_ID);
        assertThat(user.getName()).isEqualTo(userRequest.getName());
        assertThat(user.getEmail()).isEqualTo(userRequest.getEmail());
        assertThat(user.getId()).isEqualTo(userId);
    }

    @Then("response contains collection of users")
    public void thenResponseContainsCollectionOfUsers() {
        Response resp = Serenity.sessionVariableCalled(RESPONSE);
        assertThat(resp.getBody().as(List.class).size()).isGreaterThanOrEqualTo(3);
    }
}
