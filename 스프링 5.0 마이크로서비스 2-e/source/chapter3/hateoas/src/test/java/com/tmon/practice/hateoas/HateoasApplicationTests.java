package com.tmon.practice.hateoas;

import com.tmon.practice.hateoas.model.Greet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HateoasApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void greetingTest() {
		Greet body = restTemplate.getForObject("/greeting", Greet.class);
		assertThat(body.getMessage(),is("Hello HATEOAS"));
	}

}
