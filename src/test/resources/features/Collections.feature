Feature: Urban Ladder Collections

  Scenario: Retrieve Oasis Collection Submenu Items

    Given User launches Collections page
    When User closes popup if present
    And User navigates to New Arrivals
    And User opens Oasis Collection
    Then Display Oasis submenu items