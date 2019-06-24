package StepDefs;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.restapiexample.dummy.EmployeesInformation;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

public class EmployeeTestUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeTestUtil.class);
    private static  Response response;

    public int getEmployees(){
        RestAssured.registerParser("text/html", Parser.JSON);
        response = given().contentType(ContentType.JSON).
                when().get("http://dummy.restapiexample.com/api/v1/employees");
        System.out.println();
        return response.getStatusCode();
    }

    public int getTotalNumberOfEmployees() {
        JsonNode node = response.getBody().as(JsonNode.class);
        if(!node.isNull())
        LOGGER.debug("No results");
        return node.size();
    }

    public List<Map> getEmployeesList(String salary) {
        List<Map> employeeRecords = JsonPath.with(response.asString()).get("findAll{ a -> Integer.parseInt(a.employee_salary) > 5000}");
       // List<Map> employeeRecords = JsonPath.with(response.asString()).get("max{a->a.employee_salary}");
     return employeeRecords;
    }

    public String getEmployeeName() {
        return JsonPath.with(response.asString()).getMap("max{a->a.id}").get("employee_name").toString();
    }

    public int createNewEmployeeWithMaxSalary(EmployeesInformation empInfo) {
        String maxId = JsonPath.with(response.asString()).getMap("max{a->a.id}").get("id").toString();
       String empSal =JsonPath.with(response.asString()).getMap("max{a->a.employee_salary}").get("employee_salary").toString();
        JSONObject requestParams = new JSONObject();
        requestParams.put("id", Integer.parseInt(maxId)+1);
        requestParams.put("employee_name", empInfo.getName());
        requestParams.put("employee_age", empInfo.getAge());
        requestParams.put("employee_salary", Integer.parseInt(empSal)+1000);
        requestParams.put("profileImage","");

        response = given().contentType(ContentType.JSON).body(requestParams.toJSONString()).
                when().post("http://dummy.restapiexample.com/api/v1/create");
         System.out.println("The status code recieved: " + response.getStatusCode());
         System.out.println("Response body: " + response.body().asString());
        return response.getStatusCode();

    }
}
