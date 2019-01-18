package com.bskyb.internettv.service;

import com.bskyb.internettv.third.party.MovieService;
import com.bskyb.internettv.third.party.TechnicalFailureException;
import com.bskyb.internettv.third.party.TitleNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class ParentalControlServiceImplTest {

    private static final String CUSTOMER_PARENTAL_LEVEL_CODE_U = "U";
    private static final String CUSTOMER_PARENTAL_LEVEL_CODE_PG = "PG";
    private static final String CUSTOMER_PARENTAL_LEVEL_CODE_12 = "12";
    private static final String CUSTOMER_PARENTAL_LEVEL_CODE_15 = "15";
    private static final String CUSTOMER_PARENTAL_LEVEL_CODE_18 = "18";
    private static final String TEST_MOVIE_ID = "M1211";
    private static final String MOVIE_PARENTAL_LEVEL_CODE_U = "U";
    private static final String MOVIE_PARENTAL_LEVEL_CODE_PG = "PG";
    private static final String MOVIE_PARENTAL_LEVEL_CODE_12 = "12";
    private static final String MOVIE_PARENTAL_LEVEL_CODE_15 = "15";
    private static final String MOVIE_PARENTAL_LEVEL_CODE_18 = "18";

    private ParentalControlService parentalControlService;

    @Mock
    private MovieService movieService;

    @Before
    public void setUp() throws Exception {
        parentalControlService = new ParentalControlServiceImpl(movieService);
    }

    @Test
    public void shouldReturnFalse_whenMovieParentalCodeLevelServiceThrowTechnicalFailure() throws Exception {
        Mockito.when(movieService.getParentalControlLevel(anyString())).thenThrow(TechnicalFailureException.class);
        assertFalse(parentalControlService.canWatchMovie(CUSTOMER_PARENTAL_LEVEL_CODE_18, TEST_MOVIE_ID));
    }

    @Test(expected = TitleNotFoundException.class)
    public void shouldThrowTitleNotFoundException() throws Exception {
        Mockito.when(movieService.getParentalControlLevel(anyString())).thenThrow(TitleNotFoundException.class);
        parentalControlService.canWatchMovie(CUSTOMER_PARENTAL_LEVEL_CODE_18, TEST_MOVIE_ID);
    }

    @Test
    public void shouldReturnFalse_whenMovieParentalCodeLevelServiceReturnEmptyOrBlankAsCode() throws Exception {
        Mockito.when(movieService.getParentalControlLevel(anyString())).thenReturn("");
        assertFalse(parentalControlService.canWatchMovie(CUSTOMER_PARENTAL_LEVEL_CODE_18, TEST_MOVIE_ID));
    }

    @Test
    public void shouldReturnTrue_whenMovieParentalCodeLevelReturnAsU_andCustomerProvidedParentalCodeIsU() throws Exception {
        Mockito.when(movieService.getParentalControlLevel(anyString())).thenReturn(MOVIE_PARENTAL_LEVEL_CODE_U);
        assertTrue(parentalControlService.canWatchMovie(CUSTOMER_PARENTAL_LEVEL_CODE_U, TEST_MOVIE_ID));
    }

    @Test
    public void shouldReturnTrue_whenMovieParentalCodeLevelReturnAsPG_andCustomerProvidedParentalCodeIsPG() throws Exception {
        Mockito.when(movieService.getParentalControlLevel(anyString())).thenReturn(MOVIE_PARENTAL_LEVEL_CODE_PG);
        assertTrue(parentalControlService.canWatchMovie(CUSTOMER_PARENTAL_LEVEL_CODE_PG, TEST_MOVIE_ID));
    }

    @Test
    public void shouldReturnTrue_whenMovieParentalCodeLevelReturnAsPG_andCustomerProvidedParentalCodeIs12() throws Exception {
        Mockito.when(movieService.getParentalControlLevel(anyString())).thenReturn(MOVIE_PARENTAL_LEVEL_CODE_PG);
        assertTrue(parentalControlService.canWatchMovie(CUSTOMER_PARENTAL_LEVEL_CODE_12, TEST_MOVIE_ID));
    }

    @Test
    public void shouldReturnTrue_whenMovieParentalCodeLevelReturnAsPG_andCustomerProvidedParentalCodeIs15() throws Exception {
        Mockito.when(movieService.getParentalControlLevel(anyString())).thenReturn(MOVIE_PARENTAL_LEVEL_CODE_PG);
        assertTrue(parentalControlService.canWatchMovie(CUSTOMER_PARENTAL_LEVEL_CODE_15, TEST_MOVIE_ID));
    }

    @Test
    public void shouldReturnTrue_whenMovieParentalCodeLevelReturnAsPG_andCustomerProvidedParentalCodeIs18() throws Exception {
        Mockito.when(movieService.getParentalControlLevel(anyString())).thenReturn(MOVIE_PARENTAL_LEVEL_CODE_PG);
        assertTrue(parentalControlService.canWatchMovie(CUSTOMER_PARENTAL_LEVEL_CODE_18, TEST_MOVIE_ID));
    }

    @Test
    public void shouldReturnFalse_whenMovieParentalCodeLevelReturnAsPG_andCustomerProvidedParentalCodeIsU() throws Exception {
        Mockito.when(movieService.getParentalControlLevel(anyString())).thenReturn(MOVIE_PARENTAL_LEVEL_CODE_PG);
        assertFalse(parentalControlService.canWatchMovie(CUSTOMER_PARENTAL_LEVEL_CODE_U, TEST_MOVIE_ID));
    }

    @Test
    public void shouldReturnFalse_whenMovieParentalCodeLevelReturnAs12_andCustomerProvidedParentalCodeIsPG() throws Exception {
        Mockito.when(movieService.getParentalControlLevel(anyString())).thenReturn(MOVIE_PARENTAL_LEVEL_CODE_12);
        assertFalse(parentalControlService.canWatchMovie(CUSTOMER_PARENTAL_LEVEL_CODE_PG, TEST_MOVIE_ID));
    }

    @Test
    public void shouldReturnFalse_whenMovieParentalCodeLevelReturnAs15_andCustomerProvidedParentalCodeIsPG() throws Exception {
        Mockito.when(movieService.getParentalControlLevel(anyString())).thenReturn(MOVIE_PARENTAL_LEVEL_CODE_15);
        assertFalse(parentalControlService.canWatchMovie(CUSTOMER_PARENTAL_LEVEL_CODE_PG, TEST_MOVIE_ID));
    }

    @Test
    public void shouldReturnFalse_whenMovieParentalCodeLevelReturnAs18_andCustomerProvidedParentalCodeIsPG() throws Exception {
        Mockito.when(movieService.getParentalControlLevel(anyString())).thenReturn(MOVIE_PARENTAL_LEVEL_CODE_18);
        assertFalse(parentalControlService.canWatchMovie(CUSTOMER_PARENTAL_LEVEL_CODE_PG, TEST_MOVIE_ID));
    }

}