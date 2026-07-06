Feature: Urban Ladder Gift Card Validation
 
  Scenario Outline: Navigate and Fill Gift Card Form
    Given User launches the website on "<browser>"
    And User navigates to Gift Cards
 
    Examples:
      | browser |
      | Chrome  |
 
  Scenario Outline: Verify Invalid Email Error Message
    Given User launches the website on "<browser>"
    And User navigates to Gift Cards 
    When User fills details
    Then Validation error message should be displayed
 
    Examples:
      | browser |
      | Chrome  |
 