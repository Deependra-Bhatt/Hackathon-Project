Feature: Urban Ladder Collections

  Scenario: Retrieve Oasis Collection Submenu Items

    Given User launches Collections page
    When User closes popup if present
    And User navigates to New Arrivals
    Then Display Oasis submenu items