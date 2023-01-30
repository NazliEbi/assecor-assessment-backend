package com.springboot.project.api.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ColorTest {
	
	@Test
	@DisplayName("number Of Color")
	void testNumberOfColor_whenDifferentColorIsProvided() {
		
		String color = "blau";
		int expectedKey= 1;
		int result = Color.numberOfColor(color);
		Assertions.assertEquals(expectedKey, result,()-> "The numberOfColor() methode did not produce expected result.");
	}
	
	@Test
	@DisplayName("Color of number")
	void testFrom_Color_whenNumberIsProvided() {

		String givenKey= "1";
		String expectedColor = "blau";
		
		Color actualResult = Color.from(Integer.decode(givenKey));
		Assertions.assertEquals(expectedColor, actualResult.toString(),()-> "The from() methode did not produce expected result.");
	}

}
