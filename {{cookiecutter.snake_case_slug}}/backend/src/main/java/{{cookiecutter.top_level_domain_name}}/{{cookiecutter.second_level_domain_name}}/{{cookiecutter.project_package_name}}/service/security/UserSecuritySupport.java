package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.service.security;

/**
 * <h1>User Security Support</h1>
 * 
 * <p></p>
 * 
 * @author crowdbotics.com
 */
public interface UserSecuritySupport 
{
	/**
	 * 
	 * @param _id						{@link {{cookiecutter.entity_id_type}}}
	 * @param _token					{@link String}
	 * @return {@link String}
	 */
    String validatePasswordResetToken( 
		{{cookiecutter.entity_id_type}} _id,
		String _token 
	);

}
