import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


    @RunWith(Cucumber.class)
    @CucumberOptions(
            format = { "pretty", "html:target/cucumber" },
            features = "src/test/java/features/CheckEmployeeDetails.feature"
    )
    public class RunEmployeeTest {

    }

