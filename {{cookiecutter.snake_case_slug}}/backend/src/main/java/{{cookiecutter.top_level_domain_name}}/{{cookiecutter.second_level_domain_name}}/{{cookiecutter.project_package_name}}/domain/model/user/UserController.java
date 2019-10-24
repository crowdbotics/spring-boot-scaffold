{{cookiecutter.license_header}}

package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.domain.model.user;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.domain.repository.ApplicationUserRepository;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.exception.DuplicateUsernameException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>User Controller</h1>
 *
 * <p>REST controller for entity {@link ApplicationUser}.</p>
 * 
 * @author crowdbotics.com
 */
@RequestMapping(
	"/users"
)
@RestController
public class UserController
{
	/**
	 * Autowired constructor for {@link UserController}.
	 *
	 * @param _applicationUserRepository    {@link ApplicationUserRepository}
	 * @param _bCryptPasswordEncoder        {@link BCryptPasswordEncoder}
	 */
	public UserController(
		final ApplicationUserRepository _applicationUserRepository
		, final BCryptPasswordEncoder _bCryptPasswordEncoder
	)
	{
		super();
		
		applicationUserRepository = _applicationUserRepository;
		bCryptPasswordEncoder = _bCryptPasswordEncoder;
	}

	//
	//
	public static final String SIGN_UP = "/sign-up";
	/**
	 * End point to sign up a new user.
	 * 
	 * <p><b>NOTE:</b> Duplicate usernames are not allowed.</p>
	 *
	 * @param _applicationUser			{@link ApplicationUser}
	 */
	@PostMapping(
		SIGN_UP
	)
	public void signUp(
		@RequestBody final ApplicationUser _applicationUser
	)
	{
		// lookup the specified username
		final ApplicationUser applicationUser 
			= applicationUserRepository.findByUsername( _applicationUser.getUsername() );

		// does the username already exist?
		if (applicationUser == null)
		{
			// No, create it
			_applicationUser.setPassword( bCryptPasswordEncoder.encode( _applicationUser.getPassword() ) );
			applicationUserRepository.save( _applicationUser );
		}
		else
		{
			// return error
			throw new DuplicateUsernameException();
		}
	}

	//
	// Autowired
	//

	private final ApplicationUserRepository applicationUserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

}
