package com.bskyb.internettv.third.party;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class TitleNotFoundExceptionTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldThrowTitleNotFoundExceptionWithMessage_whenMovieTitleIsNotFound() throws Exception {
        expectedException.expect(TitleNotFoundException.class);
        expectedException.expectMessage("The movie service could not find the given movie");
        throw new TitleNotFoundException("The movie service could not find the given movie");
    }
}