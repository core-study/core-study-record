package com.tmon.practice;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SecurityApplicationTests {
	@Test
	public void testOAuthService() {
		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();

		resource.setUsername("guest");
		resource.setPassword("guest123");
		resource.setAccessTokenUri("http://localhost:8080/oauth/token");
		resource.setClientId("trustedclient");
		resource.setClientSecret("trustedclient123");
		resource.setGrantType("password");
		resource.setScope(Arrays.asList(new String[]{"read", "write", "truest"}));

		DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource, clientContext);

		Greet greet = restTemplate.getForObject("http://localhost:8080/", Greet.class);

		Assert.assertEquals("Hello World!", greet.getMessage());
	}
}
