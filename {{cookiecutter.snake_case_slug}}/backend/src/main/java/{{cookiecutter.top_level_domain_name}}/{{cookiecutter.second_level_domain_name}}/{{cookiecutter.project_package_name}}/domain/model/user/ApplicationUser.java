package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.domain.model.user;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.domain.model.AbstractEntity;
{% if cookiecutter.has_lombok == "y" %}
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
{%- endif %}

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <h1>Application User</h1>
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
public class ApplicationUser
	extends AbstractEntity
{
{%- if cookiecutter.has_lombok == "n" %}
	/**
	 * No argument constructor for {@Link ApplicationUser}.
	 */
	protected ApplicationUser() 
	{
		super();
	}
{%- endif %}

	//
	// Fields
	//

	private String username;

	private String password;

{% if cookiecutter.has_lombok == "n" %}
	//
	// Access methods
	//

	public String getUsername() { return username; }
	public void setUsername( final String _value ) { username = _value; }

	public String getPassword() { return password; }
	public void setPassword( final String _value ) { password = _value; }

{%- endif %}
}
