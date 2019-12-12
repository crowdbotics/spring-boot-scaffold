{{cookiecutter.license_header}}

package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.domain.model.user;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.domain.repository.ApplicationUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <h1>User Controller Tests</h1>
 *
 * @author crowdbotics.com
 */
@AutoConfigureMockMvc
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
	public void signUp()
		throws Exception
	{
		final ApplicationUser newApplicationUser = new ApplicationUser();
		newApplicationUser.setUsername( "fflintstone@rockdale.com" );
		newApplicationUser.setPassword( "password" );

		mvc.perform(
			MockMvcRequestBuilders.post(
				String.format(
					"%s%s"
					, UserController.USERS
					, UserController.SIGN_UP
				)
			)
			.contentType( MediaType.APPLICATION_JSON )
			.content( objectMapper.writeValueAsString( newApplicationUser ) )
		)
		.andExpect( status().isOk() )
		;
	}

	/**
	 *
	 */
	@Test
	public void signUp_duplicate_conflict()
		throws Exception
	{
		final String endPoint = String.format(
			"%s%s"
			, UserController.USERS
			, UserController.SIGN_UP
		);

		final ApplicationUser newApplicationUser = new ApplicationUser();
		newApplicationUser.setUsername( "fflintstone@rockdale.com" );
		newApplicationUser.setPassword( "password" );

		mvc.perform(
			MockMvcRequestBuilders.post( endPoint )
				.contentType( MediaType.APPLICATION_JSON )
				.content( objectMapper.writeValueAsString( newApplicationUser ) )
		)
			.andExpect( status().isOk() )
		;

		/*
		 * Attempt to add user again
		 */
		mvc.perform(
			MockMvcRequestBuilders.post( endPoint )
				.contentType( MediaType.APPLICATION_JSON )
				.content( objectMapper.writeValueAsString( newApplicationUser ) )
		)
			.andExpect( status().isConflict() )
		;
	}

	//
	// Operations
	//

	@Before
	public void beforeEachTest()
	{
		applicationUserRepository.deleteAll();
		applicationUserRepository.flush();
	}

	//
	// Attributes
	//

	//
	// Autowired
	//
	
	@Autowired
	private ApplicationUserRepository applicationUserRepository;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserController userController;

}
