package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.validation.ValidEmail;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.validation.ValidPassword;
{%- if cookiecutter.has_lombok == "y" %}
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
{%- endif %}

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <h1>User DTO</h1>
 * 
 * <p>Represents the object that contains all the information required
 * later on to create and populate the {@link User} class.</p>
 * 
 * @author crowdbotics.com
 */
{%- if cookiecutter.has_lombok == "y" %}
@Getter
@NoArgsConstructor
@Setter
@ToString
{%- endif %}
public class UserDto 
{
{%- if cookiecutter.has_lombok == "n" %}
	/**
	 * No argument constructor for {@link UserDto}.
	 */
	public UserDto()
	{
		super();
	}
{%- endif %}

	//
	// Operations
	//

{%- if cookiecutter.has_lombok == "n" %}
	/**
	 * {@inheritDoc}
	 *
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append( "UserDto [firstName=" )
			.append( firstName )
			.append( ", lastName=" )
			.append( lastName )
			.append( ", password=" )
			.append( password )
			.append( ", matchingPassword=" )
			.append( matchingPassword )
			.append( ", email address=" )
			.append( emailAddress )
//			.append( ", using2FA=" )
//			.append( using2FA )
//			.append( ", role=" )
//			.append( role )
			.append( "]" )
			;
			
		return builder.toString();
	}
{%- endif %}

	//
	// Fields
	//
	
	@NotNull(
		message = "The first name field must not be null."
	)
	@Size(
		message = "The first name field '${validatedValue}' must be between {min} and {max} characters."
		, min = 1
	)
	private String firstName = "";

	@NotNull(
		message = "The last name field must not be null."
	)
	@Size(
		message = "The last name field '${validatedValue}' must be between {min} and {max} characters."
		, min = 1
	)
	private String lastName;

	@ValidPassword
	private String password = "";

	@NotNull(
		message = "The matching password field must not be null."
	)
	@Size(
		message = "The matching password field '${validatedValue}' must be between {min} and {max} characters."
		, min = 1
	)
	private String matchingPassword = "";

	@ValidEmail
	@NotNull(
		message = "The email address field must not be null."
	)
	@Size(
		message = "The email address field '${validatedValue}' must be between {min} and {max} characters."
		, min = 1
	)
	private String emailAddress = "";

//	private boolean using2FA = false;

//	private Integer role = 0;

{% if cookiecutter.has_lombok == "n" %}
	//
	// Access methods
	//
	
	public String getEmailAddress() { return emailAddress; }
	public void setEmailAddress( final String _value ) { emailAddress = _value; }

	public String getFirstName() { return firstName; }
	public void setFirstName( final String _value ) { firstName = _value; }

	public String getLastName() { return lastName; }
	public void setLastName( final String _value ) { lastName = _value; }

	public String getMatchingPassword() { return matchingPassword; }
	public void setMatchingPassword( final String _value ) { matchingPassword = _value; }

	public String getPassword() { return password; }
	public void setPassword( final String _value ) { password = _value; }

//	public Integer getRole() { return role; }
//	public void setRole( final Integer _value ) { role = _value; }

//	public boolean isUsing2FA() { return using2FA; }
//	public void setUsing2FA( boolean _value ) { using2FA = _value; }
{%- endif %}

}
