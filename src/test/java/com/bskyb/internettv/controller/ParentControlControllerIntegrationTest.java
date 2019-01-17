package com.bskyb.internettv.controller;

import com.bskyb.internettv.service.ParentalControlService;
import com.bskyb.internettv.third.party.MovieService;
import com.bskyb.internettv.third.party.TechnicalFailureException;
import com.bskyb.internettv.third.party.TitleNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class ParentControlControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ParentalControlService parentalControlService;
    @MockBean
    private MovieService movieService;

    @Test
    public void getControlAccessShouldReturnBadRequest_whenCustomerProvideInValidParentalControlLevel_ForRequestedMovieId() throws Exception {
        given(movieService.getParentalControlLevel(anyString())).willReturn("U");
        mockMvc.perform(MockMvcRequestBuilders.get("/pcl/movie/v1/watch/eligibility/PG**/M1211"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getControlAccessShouldReturnBadRequest1_whenCustomerProvideInCorrectParentalControlLevel_ForRequestedMovieId() throws Exception {
        given(movieService.getParentalControlLevel(anyString())).willReturn("U");
        mockMvc.perform(MockMvcRequestBuilders.get("/pcl/movie/v1/watch/eligibility/PG18/M1211"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getControlAccessShouldReturnBadRequest1_whenCustomerProvideValidParentalControlLevel_ButInValidMovieId() throws Exception {
        given(movieService.getParentalControlLevel(anyString())).willReturn("U");
        mockMvc.perform(MockMvcRequestBuilders.get("/pcl/movie/v1/watch/eligibility/12/M1**211"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getControlAccessShouldReturnNotFound_whenCustomerDoNotProvideValidParameters() throws Exception {
        given(movieService.getParentalControlLevel(anyString())).willReturn("U");
        mockMvc.perform(MockMvcRequestBuilders.get("/pcl/movie/v1/watch/eligibility/"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void getControlAccessShouldReturnNot1Found_whenCustomerProvideControlLevel_andDoNotSupplyMovieId() throws Exception {
        given(movieService.getParentalControlLevel(anyString())).willReturn("U");
        mockMvc.perform(MockMvcRequestBuilders.get("/pcl/movie/v1/watch/eligibility/18"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void getControlAccessShouldReturnTrue_whenMovieParentalControlLevelIsLessThan_CustomerParentalControlLevel_ForRequestedMovieId() throws Exception {
        given(movieService.getParentalControlLevel(anyString())).willReturn("U");
        mockMvc.perform(MockMvcRequestBuilders.get("/pcl/movie/v1/watch/eligibility/PG/M1211"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void getControlAccessShouldReturnTrue_whenMovieParentalControlLevelIsEqualTo_CustomerParentalControlLevel_ForRequestedMovieId() throws Exception {
        given(movieService.getParentalControlLevel(anyString())).willReturn("PG");
        mockMvc.perform(MockMvcRequestBuilders.get("/pcl/movie/v1/watch/eligibility/PG/M1211"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void getControlAccessShouldReturnFalse_whenMovieParentalControlLevelIsGreaterThan_CustomerParentalControlLevel_ForRequestedMovieId() throws Exception {
        given(movieService.getParentalControlLevel(anyString())).willReturn("18");
        mockMvc.perform(MockMvcRequestBuilders.get("/pcl/movie/v1/watch/eligibility/PG/M1211"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    public void getControlAccessShouldReturnFalse_whenMovieServiceHasTechnicalFailure() throws Exception {
        given(movieService.getParentalControlLevel(anyString())).willThrow(TechnicalFailureException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/pcl/movie/v1/watch/eligibility/PG/M1211"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    public void getControlAccessShouldReturnNotFound_whenMovieServiceCouldNotFindTheGivenMovie() throws Exception {
        given(movieService.getParentalControlLevel(anyString())).willThrow(TitleNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/pcl/movie/v1/watch/eligibility/PG/M1211"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void getControlAccessShouldReturnInteralServerError_whenMovieServiceFailForAnyRunTimeException() throws Exception {
        given(movieService.getParentalControlLevel(anyString())).willThrow(RuntimeException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/pcl/movie/v1/watch/eligibility/PG/M1211"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}