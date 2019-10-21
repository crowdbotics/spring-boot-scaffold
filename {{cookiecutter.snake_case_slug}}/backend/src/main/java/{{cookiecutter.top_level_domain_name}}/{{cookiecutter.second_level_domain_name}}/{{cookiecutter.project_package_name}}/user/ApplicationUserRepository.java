package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * <h1>Application User Repository</h1>
 *
 * <p>Spring data repository for {@link ApplicationUser}.</p>
 * 
 * @author crowdbotics.com
 */
public interface ApplicationUserRepository 
	extends JpaRepository<ApplicationUser,  {{cookiecutter.entity_id_type}}> 
		, JpaSpecificationExecutor<ApplicationUser>
{
    ApplicationUser findByUsername( final String _username );
}
