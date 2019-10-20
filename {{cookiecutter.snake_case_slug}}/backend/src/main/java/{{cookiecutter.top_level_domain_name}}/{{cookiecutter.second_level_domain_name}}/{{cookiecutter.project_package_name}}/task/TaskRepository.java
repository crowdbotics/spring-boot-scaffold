package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * <h1>Task Repository</h1>
 *
 * <p>Spring data repository for {@link Task}.</p>
 * 
 * @author crowdbotics.com
 */
public interface TaskRepository 
	extends JpaRepository<Task, {{cookiecutter.entity_id_type}}>
		, JpaSpecificationExecutor<Task>
{
}
