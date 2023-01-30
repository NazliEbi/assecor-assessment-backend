package com.springboot.project.api;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@Disabled
@SpringBootTest
@ActiveProfiles("DEV")
@TestPropertySource({"/application.yaml"})
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}
