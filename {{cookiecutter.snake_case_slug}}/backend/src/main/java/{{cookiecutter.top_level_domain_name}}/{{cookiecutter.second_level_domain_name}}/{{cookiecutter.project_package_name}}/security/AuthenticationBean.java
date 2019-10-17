package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.security;

{% if cookiecutter.has_lombok == "y" %}
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
{%- endif %}

/**
 * <h1>Active User Store</h1>
 * 
 * <p>...</p>
 * 
 * @author crowdbotics.com
 */
{%- if cookiecutter.has_lombok == "y" %}
@Getter
@Setter
@ToString
{%- endif %}
public class AuthenticationBean
{
	/**
	 * Default constructor for {@link AuthenticationBean}.
	 * 
	 * @param _message					{@link String}
	 */
	public AuthenticationBean(
		final String _message
	)
	{
		message = _message;
	}

	//
	// Operations
	//

{% if cookiecutter.has_lombok == "n" %}
	/**
	 * {@inheritDoc}
	 *
	 * @see Object#toString()
	 */
	@Override
	public String toString() 
	{
		return String.format(
			"AuthenticationBean [message=%s]"
			, message
		);
	}
{%- endif %}

	//
	// Attributes
	//
	
	/**
	 * <h1>Message</h1>
	 */
	private String message;

{% if cookiecutter.has_lombok == "n" %}
	//
	// Access methods
	//
	
	public String getMessage() { return message; }
	public void setMessage( final String _value ) { message = _value; }
{%- endif %}
}
