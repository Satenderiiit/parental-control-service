package com.bskyb.internettv.acceptance.glue;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "pretty",
        features = "src/test/resources/features/ParentalControlLevel.features",
        glue = "com.bskyb.internettv.acceptance.glue")
public class AcceptanceCriteriaTest {
}
