package {{cookiecutter.custom_domain_extension}}.{{cookiecutter.custom_domain}}.{{cookiecutter.custom_project_package}}.repository.user;

import {{cookiecutter.custom_domain_extension}}.{{cookiecutter.custom_domain}}.{{cookiecutter.custom_project_package}}.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * <h1>User Repository</h1>
 * 
 * @author crowdbotics.com
 */
public interface UserRepository 
	extends JpaRepository<User, {{cookiecutter.entity_id_type}}> 
		, JpaSpecificationExecutor<User>
{
}
