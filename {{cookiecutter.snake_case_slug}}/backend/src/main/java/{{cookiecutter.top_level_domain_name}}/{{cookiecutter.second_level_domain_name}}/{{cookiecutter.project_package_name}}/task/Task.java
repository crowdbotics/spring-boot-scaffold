package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.task;

{% if cookiecutter.has_lombok == "y" %}
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
{%- endif %}

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

{%- if cookiecutter.has_lombok == "y" %}
@Getter
@NoArgsConstructor
@Setter
{%- endif %}
@Entity
public class Task 
{
{%- if cookiecutter.has_lombok == "n" %}
	protected Task() 
	{
		super();
	}
{%- endif %}

	public Task(
		final String _description
	) 
	{
		description = _description;
	}

	/**
	 * <h1>ID</h1>
	 * 
	 * <p>Internal ID for the task.</p>
	 */
    @Id
{%- if cookiecutter.entity_id_type == "Long" %}
	@GeneratedValue( strategy = GenerationType.IDENTITY )
{%- endif %}
    private {{cookiecutter.entity_id_type}} id;

	private String description;


{% if cookiecutter.has_lombok == "n" %}
	//
	// Access methods
	//

	public long getId() { return id; }

	public String getDescription() { return description; }
	public void setDescription( final String _value ) { description = _value; }
{%- endif %}
}
