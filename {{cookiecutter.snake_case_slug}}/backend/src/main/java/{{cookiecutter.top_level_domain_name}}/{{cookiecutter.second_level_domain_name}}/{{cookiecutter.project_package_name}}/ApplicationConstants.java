{{cookiecutter.license_header}}

package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}};

/**
 * <h1>Application Constants</h1>
 *
 * <p>Global contants referenced by all.</p>
 *
 * @author crowdbotics.com
 */
public class ApplicationConstants 
{
	public static final String SIGN_UP_URL = "/users/sign-up";

	public static final String HEADER_STRING = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";

{%- if cookiecutter.spring_boot_authentication == "jwt" %}
	/*
	 * Constants for JWT authentication
	 */
	public static final String SECRET = "Cr0wdb0t1c515gr3at";
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
{%- endif %}
}
