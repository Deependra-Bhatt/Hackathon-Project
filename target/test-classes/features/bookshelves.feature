Feature: Urban Ladder Bookshelves Automated Assertions

  Scenario Outline: TC01_Verify Product List Is Not Empty
    Given User launches the website on "<browser>"
    When User dismisses any introductory popups
    And User navigates to the Bookshelves section via Storage Furniture menu
    And User applies the open storage type filter
    Then The product results list should not be empty

    Examples: 
      | browser |
      | Chrome  |

  Scenario Outline: TC02_Validate Top Five Bookshelves Criteria
    Given User launches the website on "<browser>"
    When User dismisses any introductory popups
    And User navigates to the Bookshelves section via Storage Furniture menu
    And User applies the open storage type filter
    Then The results should display up to 5 items and all displayed items must contain "Bookshelf"

    Examples: 
      | browser |
      | Chrome  |