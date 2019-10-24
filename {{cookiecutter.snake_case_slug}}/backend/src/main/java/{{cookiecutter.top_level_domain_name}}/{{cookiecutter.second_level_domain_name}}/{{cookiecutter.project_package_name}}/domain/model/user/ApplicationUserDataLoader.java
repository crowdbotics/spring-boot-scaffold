{{cookiecutter.license_header}}

package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.domain.model.user;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.domain.repository.ApplicationUserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <h1>Application User Data Loader</h1>
 *
 * <p>Loads initial entries in the {@link ApplicationUser} table.</p>
 *
 * @author crowdbotics.com
 */
@Component
public class ApplicationUserDataLoader
	implements ApplicationRunner
{
	/**
	 * Autowired constructor for {@link ApplicationUserDataLoader}.
	 *
	 * @param _applicationUserRepository    {@link ApplicationUserRepository}
	 * @param _bCryptPasswordEncoder        {@link BCryptPasswordEncoder}
	 */
	public ApplicationUserDataLoader(
		final ApplicationUserRepository _applicationUserRepository
		, final BCryptPasswordEncoder _bCryptPasswordEncoder
	)
	{
		super();
		
		applicationUserRepository = _applicationUserRepository;
		bCryptPasswordEncoder = _bCryptPasswordEncoder;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run(
		final ApplicationArguments _arguments 
	) 
	{
		final ApplicationUser applicationUser = applicationUserRepository.findByUsername( ADMIN_USERNAME );

		// Does the username already exist?
		if (applicationUser == null)
		{
			// No, create it
			final ApplicationUser newApplicationUser = new ApplicationUser();
			newApplicationUser.setUsername( ADMIN_USERNAME );
			newApplicationUser.setPassword( bCryptPasswordEncoder.encode( ADMIN_PASSWORD ) );

			applicationUserRepository.save( newApplicationUser );
		}
	}
	
	//
	// Attributes
	//

	public static final String ADMIN_PASSWORD = "password";
	public static final String ADMIN_USERNAME = "admin@crowdbotics.com";

	//
	// Autowired
	//
	
	private final ApplicationUserRepository applicationUserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
}
