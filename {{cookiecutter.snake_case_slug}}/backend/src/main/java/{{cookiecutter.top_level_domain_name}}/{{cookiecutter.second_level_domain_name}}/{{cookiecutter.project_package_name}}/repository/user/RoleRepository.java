package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.repository.user;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * <h1>Role Repository</h1>
 * 
 * @author crowdbotics.com
 */
public interface RoleRepository 
	extends JpaRepository<Role, {{cookiecutter.entity_id_type}}> 
		, JpaSpecificationExecutor<Role> 
{
	/**
	 * 
	 */
	Role findByName( String _name );

	/**
	 * {@inheritDoc}
	 */
	@Override
	void delete( Role _role );

}
