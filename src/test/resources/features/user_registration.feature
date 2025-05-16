Feature: User Registration

  Scenario: Successful user registration
    Given the user is on the registration page
    When the user enters valid details
    And clicks the register button
    Then the user should be registered successfully

  Scenario: Registration without last name
    Given the user is on the registration page
    When the user enters first name and password but leaves last name blank
    And clicks the register button
    Then the registration should fail with an error message

  Scenario: Password mismatch
    Given the user is on the registration page
    When the user enters the password and confirmation password incorrectly
    And clicks the register button
    Then the registration should fail with a mismatch error message

  Scenario: Not accepting Terms & Conditions
    Given the user is on the registration page
    When the user does not accept the terms and conditions
    And clicks the register button
    Then the registration should fail with an error message









