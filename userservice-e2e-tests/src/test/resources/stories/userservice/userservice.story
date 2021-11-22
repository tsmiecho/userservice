
Scenario: Update user
Meta:@regression
Given a user is prepared
When request to save user
Then response contains valid user
Given update of the user is prepared
When request to update the user is sent
Then response contains the updated user

Scenario: Delete user
Meta:@regression
Given a user is prepared
When request to save user
Then response contains valid user
Given delete request is prepared
When request to delete the user is sent
Then response contains no content status
Given a request to fetch user by id is created
When request to fetch user by id
Then response contains not found status

Scenario: Create several users
Meta:@regression
Given a user is prepared
When request to save user
Then response contains valid user
Given a user is prepared
When request to save user
Then response contains valid user
Given a user is prepared
When request to save user
Then response contains valid user
When request to get users is sent
Then response contains collection of users

Scenario: Create user with the same email
Meta:@regression
Given a user is prepared
When request to save user
Then response contains valid user
Given a user with the same email is prepared
When request to save user
Then response contains conflict status
