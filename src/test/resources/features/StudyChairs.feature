Feature: Study Chairs Validation

  Scenario: TC01_Verify first three study chairs are fetched
  	Given User navigates to Study Chairs page
	When User dismisses introductory popups on study chairs page
    And User sorts the study chairs by popularity
    And User stores the first three study chair names
    Then Exactly three study chair names should be present

  Scenario: TC02_Verify fetched products are study chairs
 	Given User navigates to Study Chairs page
	When User dismisses introductory popups on study chairs page
    And User sorts the study chairs by popularity
    And User stores the first three study chair names
    Then All fetched products should belong to study chairs category