package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.service.security;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.registration.PasswordResetToken;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user.User;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.repository.registration.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Calendar;

/**
 * <h1>User Security Support Service</h1>
 * 
 * <p>Spring service that implements the {@link UserSecuritySupport} interface.</p>
 * 
 * @author crowdbotics.com
 */
@Service
@Transactional
public class UserSecuritySupportService implements UserSecuritySupport {
	
	/**
	 * Autowired constructor for {@link UserSecuritySupportService}.
	 * 
	 * @param _passwordResetTokenRepository		{@link PasswordResetTokenRepository}
	 */
	@Autowired
	public UserSecuritySupportService(
		final PasswordResetTokenRepository _passwordResetTokenRepository
	)
	{
		super();
		
		passwordResetTokenRepository = _passwordResetTokenRepository;
	}

	//
	// Spring initialization
	//
	
	@PostConstruct
	public void postInitialization()
	{
	}

	//
	// API
	//
	
	@Override
	public String validatePasswordResetToken( final {{cookiecutter.entity_id_type}} _id, final String _token ) {
		final PasswordResetToken passToken = passwordResetTokenRepository.findByToken( _token );
		if ((passToken == null) 
{%- if cookiecutter.entity_id_type == "Long" -%}
		 || (passToken.getUser().getId() != _id)) 
{% endif %}
{%- if cookiecutter.entity_id_type == "String" -%}
		 || (passToken.getUser().getId().equals( _id ) == false)) 
{% endif %}
		{
			return "invalidToken";
		}

		final Calendar cal = Calendar.getInstance();
		if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) 
		{
			return "expired";
		}

		final User user = passToken.getUser();
		final Authentication authentication 
			= new UsernamePasswordAuthenticationToken( 
				user
				, null
				, Arrays.asList( new SimpleGrantedAuthority( "CHANGE_PASSWORD_PRIVILEGE" ) ) 
			);
		SecurityContextHolder.getContext().setAuthentication( authentication );
		
		return null;
	}

	//
	// Autowired
	//
	
	private final PasswordResetTokenRepository passwordResetTokenRepository;

}
