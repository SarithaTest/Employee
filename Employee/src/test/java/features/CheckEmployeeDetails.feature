Feature: Check employees salaries
  As a user
  I want to get all employees details
  So that I can create the maximum paid employee

  Scenario: Read a list of employees
  Given I make a GET request to employees endpoint
  When I get a response with the total number of employees
  Then I get the name of the employee with max Id value
  And I verify the employees whose salary is greater than "5000"

  Scenario: New employee creation with salary greater thhan highest Salary
    Given I make a GET request to employees endpoint
    When I create the new user with below details with salary greater than highest salary:
    | Name            | Age|
    |ArchemedesE      | 45 |

