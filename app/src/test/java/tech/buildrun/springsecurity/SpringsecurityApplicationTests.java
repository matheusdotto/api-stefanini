package tech.buildrun.springsecurity;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringsecurityApplicationTests {

	@Test
	void contextLoads() {
	}

	@Disabled
	@Test
	void mainMethodTest() {
		SpringsecurityApplication.main(new String[] {});
	}

}
