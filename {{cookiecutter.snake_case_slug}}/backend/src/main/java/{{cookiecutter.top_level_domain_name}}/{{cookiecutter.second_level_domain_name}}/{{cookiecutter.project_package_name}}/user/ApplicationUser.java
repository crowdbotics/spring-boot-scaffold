package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.user;

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
public class ApplicationUser 
{
{%- if cookiecutter.has_lombok == "n" %}
	protected ApplicationUser() 
	{
		super();
	}
{%- endif %}

	/**
	 * <h1>ID</h1>
	 * 
	 * <p>Internal ID for the user.</p>
	 */
	@Id
{%- if cookiecutter.entity_id_type == "Long" %}
	@GeneratedValue( strategy = GenerationType.IDENTITY )
{%- endif %}
	private {{cookiecutter.entity_id_type}} id;

	private String username;

	private String password;

{% if cookiecutter.has_lombok == "n" %}
	//
	// Access methods
	//

	public long getId() { return id; }

	public String getUsername() { return username; }
	public void setUsername( final String _value ) { username = _value; }

	public String getPassword() { return password; }
	public void setPassword( final String _value ) { password = _value; }

{%- endif %}
}
