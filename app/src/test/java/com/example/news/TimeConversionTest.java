package com.example.news;

import com.example.news.util.TimeConversion;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TimeConversionTest {

    //Update to current date to test.
    String expectedTime = "2020-09-22";

    @Test
    public void timeConversion_isCorrect() {
        assertEquals(expectedTime, TimeConversion.fullDateToShortAPIDate(new Date()));
    }
}
