package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.web;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.security.AuthenticationBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>Basic Authentication Controller</h1>
 * 
 * <p>...</p>
 * 
 * @author crowdbotics.com
 */
@CrossOrigin(
	origins = { 
		"http://localhost:3000"
		, "http://localhost:4200" 
	}
)
@RestController
public class BasicAuthenticationController
{
	/**
	 * 
	 */
	@GetMapping(
		path = "/basicauth"
	)
	public AuthenticationBean basicAuthentication() 
	{
		return new AuthenticationBean( "You are authenticated" );
	}   
}
