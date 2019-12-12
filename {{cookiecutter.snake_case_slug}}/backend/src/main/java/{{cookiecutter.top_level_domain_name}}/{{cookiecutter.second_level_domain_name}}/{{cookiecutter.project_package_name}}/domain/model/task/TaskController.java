{{cookiecutter.license_header}}

package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.domain.model.task;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.domain.repository.TaskRepository;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.exception.TaskNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * <h1>Task Controller</h1>
 *
 * <p>REST controller for entity {@link Task}.</p>
 * 
 * @author crowdbotics.com
 */
@RequestMapping(
	TaskController.TASKS
)
@RestController
public class TaskController
{
	public static final String TASKS = "/tasks";

	/**
	 * Autowired constructor for {@link TaskController}.
	 *
	 * @param _taskRepository    		{@link TaskRepository}
	 */
	public TaskController(
		final TaskRepository _taskRepository
	)
	{
		super();

		taskRepository = _taskRepository;
	}

	/**
	 * 
	 * @param _task						{@link Task}
	 */
	@PostMapping
	public void addTask(
		@RequestBody final Task _task
	)
	{
		taskRepository.save( _task );
	}

	/**
	 * 
	 * @return {@link List} containing type {@Task}
	 */
	@GetMapping
	public List<Task> getTasks()
	{
		return taskRepository.findAll();
	}

	/**
	 *
	 * @param _id						{@code {{cookiecutter.entity_id_type}}}
	 * @param _source					{@link Task}
	 */
	@PutMapping(
		"/{_id}"
	)
	public void editTask(
		@PathVariable final {{cookiecutter.entity_id_type}} _id
		, @RequestBody final Task _source
	)
	{
		final Optional<Task> optionalExistingTask = taskRepository.findById( _id );

		if (optionalExistingTask.isPresent() == true)
		{
			final Task existingTask = optionalExistingTask.get();
			existingTask.setDescription( _source.getDescription() );
			taskRepository.save( existingTask );
		}
		else
		{
			throw new TaskNotFoundException();
		}
	}

	/**
	 *
	 * @param _id						{@link {{cookiecutter.entity_id_type}}}
	 */
	@DeleteMapping(
		"/{_id}"
	)
	public void deleteTask(
		@PathVariable final {{cookiecutter.entity_id_type}} _id
	)
	{
		final Optional<Task> optionalExistingTask = taskRepository.findById( _id );

		if (optionalExistingTask.isPresent() == true)
		{
			taskRepository.delete( optionalExistingTask.get() );
		}
		else
		{
			throw new TaskNotFoundException();
		}
	}

	//
	// Autowired
	//

	private final TaskRepository taskRepository;

}
