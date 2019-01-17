package com.bskyb.internettv.acceptance.glue;

import com.bskyb.internettv.service.ParentalControlService;
import com.bskyb.internettv.third.party.MovieService;
import com.bskyb.internettv.third.party.TitleNotFoundException;
import cucumber.api.java8.En;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ParentalControlLevelStepDefinitions implements En {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestRestTemplate restTemplate;
    /** To make the bean hard referenced, then Spring will initialize the bean.**/
    @Autowired
    @MockBean
    private MovieService movieService;
    @Autowired
    private ParentalControlService parentalControlService;

    private ResponseEntity<Boolean> responseEntity;
    private String customerParentalLevel;

    public ParentalControlLevelStepDefinitions() {

        Given("^I am customer who have set parental control level (.*)$", (String parentalControlLevel) -> {
            customerParentalLevel = parentalControlLevel;
        });

        When("^I request to watch equal level movie (.*)$", (String movieId) -> {
            given(movieService.getParentalControlLevel(anyString())).willReturn("PG");
            responseEntity = getResponseEntity(movieId);
        });

        When("^I request to watch higher level movie (.*)$", (String movieId) -> {
            given(movieService.getParentalControlLevel(anyString())).willReturn("18");
            responseEntity = getResponseEntity(movieId);
        });

        When("^I request to watch anonymous level movie (.*)$", (String movieId) -> {
            given(movieService.getParentalControlLevel(anyString())).willThrow(TitleNotFoundException.class);
            responseEntity = getResponseEntity(movieId);
        });

        Then("^I get decision to watch$", () -> {
            assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
            assertThat(responseEntity.getBody(), is(true));
        });

        Then("^I get decision not to watch$", () -> {
            assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
            assertThat(responseEntity.getBody(), is(false));
        });

        Then("^I get error the movie service could not find the given movie$", () -> {
            assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
        });

    }

    private ResponseEntity getResponseEntity(String movieId) {
        HttpEntity<String> request = new HttpEntity<>(generateHeader());
        return restTemplate.exchange("/pcl/movie/v1/watch/eligibility/" + customerParentalLevel + "/" + movieId, HttpMethod.GET, request, Boolean.class);
    }

    private MultiValueMap<String, String> generateHeader() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Accept", "application/json");
        return headers;
    }
}
