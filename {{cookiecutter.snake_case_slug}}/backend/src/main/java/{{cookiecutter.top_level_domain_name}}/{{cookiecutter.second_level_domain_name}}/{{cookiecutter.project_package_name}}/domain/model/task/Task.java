package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.domain.model.task;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.domain.model.AbstractEntity;
{%- if cookiecutter.has_lombok == "y" %}
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
{%- endif %}

import javax.persistence.Entity;
{%- if cookiecutter.entity_id_type == "Long" %}
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
{%- endif %}
import javax.persistence.Id;

/**
 * <h1>Task</h1>
 *
 * <p>...</p>
 *
 * @author crowdbotics.com
 */
{%- if cookiecutter.has_lombok == "y" %}
@Getter
@NoArgsConstructor
@Setter
{%- endif %}
@Entity
public class Task 
	extends AbstractEntity
{
{%- if cookiecutter.has_lombok == "n" %}
	/**
	 * No argument constructor for {@Link Task}.
	 */
	protected Task() 
	{
		super();
	}
{%- endif %}

	/**
	 * Description constructor for {@Link Task}.
	 * 
	 * @param _description				{@link String}
	 */
	public Task(
		final String _description
	) 
	{
		super();
		
		description = _description;
	}

	private String description;


{% if cookiecutter.has_lombok == "n" %}
	//
	// Access methods
	//

	public String getDescription() { return description; }
	public void setDescription( final String _value ) { description = _value; }
{%- endif %}
}
