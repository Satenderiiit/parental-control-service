package com.bskyb.internettv.third.party;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TechnicalFailureExceptionTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldThrowTitleNotFoundExceptionWithMessage_whenMovieTitleIsNotFound() throws Exception {
        expectedException.expect(TechnicalFailureException.class);
        expectedException.expectMessage("System error");
        throw new TechnicalFailureException("System error");
    }
}