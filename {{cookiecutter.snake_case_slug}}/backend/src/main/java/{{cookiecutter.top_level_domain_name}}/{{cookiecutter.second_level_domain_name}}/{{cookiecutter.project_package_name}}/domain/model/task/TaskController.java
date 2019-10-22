package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.domain.model.task;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.domain.repository.TaskRepository;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <h1>Task Controller</h1>
 *
 * <p>REST controller for entity {@link Task}.</p>
 * 
 * @author crowdbotics.com
 */
@RequestMapping(
	"/tasks"
)
@RestController
public class TaskController
{
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
	 * @param _task						{@link Task}
	 */
	@PutMapping(
		"/{_id}"
	)
	public void editTask(
		@PathVariable final {{cookiecutter.entity_id_type}} _id
		, @RequestBody final Task _task
	)
	{
		Task existingTask = taskRepository.findById( _id ).get();
		Assert.notNull( existingTask, "Task not found" );
		existingTask.setDescription( _task.getDescription() );
		taskRepository.save( existingTask );
	}

	/**
	 *
	 * @param _id						{@code long}
	 */
	@DeleteMapping(
		"/{_id}"
	)
	public void deleteTask(
		@PathVariable final {{cookiecutter.entity_id_type}} _id
	)
	{
		Task taskToDel = taskRepository.findById( _id ).get();
		taskRepository.delete( taskToDel );
	}

	//
	// Autowired
	//

	private final TaskRepository taskRepository;

}
