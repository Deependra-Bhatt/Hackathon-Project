Feature: Urban Ladder Bookshelves Automated Assertions

  Scenario Outline: TC01_Verify Product List Is Not Empty
    Given User launches the website 
    When User dismisses introductory popups on homepage
    And User navigates to the Bookshelves section via Storage Furniture menu
    And User applies the open storage type filter
    Then The product results list should not be empty


  Scenario Outline: TC02_Validate Top Five Bookshelves Criteria
    Given User launches the website  
    When User dismisses introductory popups on homepage
    And User navigates to the Bookshelves section via Storage Furniture menu
    And User applies the open storage type filter
    Then The results should display up to 5 items and all displayed items must contain "Bookshelf"
