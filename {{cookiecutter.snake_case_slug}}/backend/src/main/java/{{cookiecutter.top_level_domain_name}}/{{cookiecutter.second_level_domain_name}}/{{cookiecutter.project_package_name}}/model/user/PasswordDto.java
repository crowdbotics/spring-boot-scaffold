package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.validation.ValidPassword;
{%- if cookiecutter.has_lombok == "y" %}
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
{%- endif %}

/**
 * <h1>Password DTO</h1>
 * 
 * <p>Represents the object that contains all the information required
 * to change a user's password.</p>
 * 
 * @author crowdbotics.com
 */
{%- if cookiecutter.has_lombok == "y" %}
@Getter
@NoArgsConstructor
@Setter
{%- endif %}
public class PasswordDto 
{
{%- if cookiecutter.has_lombok == "n" %}
	/**
	 * No argument constructor for {@link PasswordDto}.
	 */
	public PasswordDto()
	{
		super();
	}
{%- endif %}

	//
	// Operations
	//

	//
	// Fields
	//
	
	@ValidPassword
	private String newPassword;

	private String oldPassword;

{% if cookiecutter.has_lombok == "n" %}
	//
	// Access methods
	//
	
	public String getOldPassword() { return oldPassword; }
	public void setOldPassword( final String _value ) { oldPassword = _value; }

	public String getNewPassword() { return newPassword; }
	public void setNewPassword( final String _value ) { newPassword = _value; }
{%- endif %}
}
