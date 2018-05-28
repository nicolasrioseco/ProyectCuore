package CuoreMaven_Cucumber.ProyectCuore;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/casos_cucumber/definicionCasos.feature",
        glue = "Ejecutor",
        tags = {},
        monochrome = true,
        strict = false,
        plugin = { "pretty",
        		"html:C:\\CuoreData\\Reportes",
                "json:C:\\CuoreData\\Reportes/cucumber.json ",
                "junit:C:\\CuoreData\\Reportes/cucumber.xml "}
        )

public class RunTest 
{  
} 