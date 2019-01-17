package com.bskyb.internettv.controller;

import com.bskyb.internettv.service.ParentalControlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ParentControlControllerTest {

    @Mock
    private ParentalControlService parentalControlService;
    @InjectMocks
    private ParentControlController parentControlController;

    @Test
    public void getControlAccessShouldInvokeParentalControlService_whenValidInputIsProvidedToApi() throws Exception {
        parentControlController.getControlAccess("PG", "M112200");
        verify(parentalControlService).canWatchMovie(anyString(), anyString());
    }
}