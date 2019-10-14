package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.repository.user;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user.User;
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
