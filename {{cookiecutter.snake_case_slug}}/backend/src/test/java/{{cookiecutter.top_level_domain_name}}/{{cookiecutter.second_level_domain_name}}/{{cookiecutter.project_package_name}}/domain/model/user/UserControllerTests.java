{{cookiecutter.license_header}}

package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.domain.model.user;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.config.SecurityConfiguration;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <h1>User Controller Tests</h1>
 *
 * @author crowdbotics.com
 */
@RunWith(
	SpringRunner.class
)
@SpringBootTest(
	webEnvironment = WebEnvironment.RANDOM_PORT
)
public class UserControllerTests 
{
	//
	// Tests
	//

	/**
	 *
	 */
	@Test
	public void contextLoads()
	{
		assertThat( userController ).isNotNull();
	}

	/**
	 *
	 */
	@Test
	public void registerUserAccount()
		throws Exception
	{
		final String baseUrl = "http://localhost:" + port + UserController.SIGN_UP;
		final URI uri = new URI( baseUrl );

		final HttpHeaders headers = new HttpHeaders();
		headers.set( "X-COM-PERSIST", "true" );

		final UserDto newUserDto = new UserDto();
		newUserDto.setEmailAddress( "fflintstone@rockday.com" );
		newUserDto.setFirstName( "Fred" );
		newUserDto.setLastName( "Flintstone" );
		newUserDto.setPassword( "Ilik3pebb!es" );
		newUserDto.setMatchingPassword( "Ilik3pebb!es" );

		HttpEntity<UserDto> request = new HttpEntity<>( newUserDto, headers );

		ResponseEntity<String> result = restTemplate
			.withBasicAuth(
				SecurityConfiguration.USERNAME
				, SecurityConfiguration.PASSWORD
			)
			.postForEntity(
				uri
				, request
				, String.class
			)
			;
		assertThat( result.getStatusCode() ).isEqualTo( HttpStatus.OK );
	}

//	/**
//	 *
//	 */
////	@Test
//	public void registration_confirm()
//		throws Exception
//	{
//		final String baseUrl = "http://localhost:" + port + RegistrationController.REGISTRATION_CONFIRM;
//		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl( baseUrl )
//			.queryParam("token", "hello" )
//			;
//
//		ResponseEntity<String> result = restTemplate
//			.withBasicAuth(
//				SecurityConfiguration.USERNAME
//				, SecurityConfiguration.PASSWORD
//			)
//			.getForEntity(
//				builder.build().encode().toUri()
//				, String.class
//			)
//			;
//		assertThat( result.getStatusCode() ).isEqualTo( HttpStatus.OK );
//	}

	//
	// Operations
	//

	@Before
	public void beforeEachTest()
	{
	}

	//
	// Attributes
	//

	//
	// Autowired
	//
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UserController userController;

}
