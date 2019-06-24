package StepDefs;


import com.restapiexample.dummy.EmployeesInformation;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class EmployeeRelatedTestSteps {

    EmployeeTestUtil employeeTestUtil = new EmployeeTestUtil();


    @Given("^I make a GET request to employees endpoint$")
    public void makeGetEmployeesRequest(){
        assertEquals("No results are generated ",employeeTestUtil.getEmployees(), 200); ;
        }

    @Then("^I verify the employees whose salary is greater than \"([^\"]*)\"$")
    public void iVerifyTheEmployeesWhoseSalaryIsGreaterThan(String salary)  {
        List<Map> employeeRecords = employeeTestUtil.getEmployeesList(salary);
        assertNotNull("No records found "+employeeRecords);
        IntStream.range(0, employeeRecords.size()).forEach(id -> System.out.println("Employee names with Salary > 5000 are "+employeeRecords.get(id).get("employee_name")));


    }

    @When("^I get a response with the total number of employees$")
    public void iGetAResponseWithTheTotalNumberOfEmployees()  {
        int employeeTotal = employeeTestUtil.getTotalNumberOfEmployees();
        assertTrue("No results found", employeeTotal>0);
        System.out.println(" The total number of employees = "+employeeTotal);
    }

    @And("^I get the name of the employee with max Id value$")
    public void iGetTheNameOfTheEmployeeWithMaxIdValue()  {
        String employeeName = employeeTestUtil.getEmployeeName();
        assertNotNull("Employee is not present" +employeeName);
        System.out.println("Employee name with maximum Id is "+employeeName);
    }

    @When("^I create the new user with below details with salary greater than highest salary:$")
     public void iCreateTheNewUserWithBelowDetailsWithSalaryGreaterThanHighestSalary(List<EmployeesInformation> empInfo) {
        int statusCode = 0 ;
        for(EmployeesInformation testData : empInfo)
         statusCode = employeeTestUtil.createNewEmployeeWithMaxSalary(testData);
         assertEquals(statusCode, "201");


    }
}
