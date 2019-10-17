package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user;

{% if cookiecutter.has_lombok == "y" %}
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
{%- endif %}

import javax.persistence.*;
import java.util.Collection;

/**
 * <h1>User</h1>
 * 
 * <p>Represents an entity that identifies a user of the site.</p>
 * 
 * @author crowdbotics.com
 */
{%- if cookiecutter.has_lombok == "y" %}
@AllArgsConstructor
@Data
@NoArgsConstructor
{%- endif %}
@Entity
public class User 
{
{%- if cookiecutter.has_lombok == "n" %}
	/**
	 * No argument constructor for {@link User}.
	 */
	public User()
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
	
	/**
	 * <h1>ID</h1>
	 * 
	 * <p>Internal ID for the user.</p>
	 */
    @Id
{%- if cookiecutter.entity_id_type == "Long" %}
    @GeneratedValue
{%- endif %}
    private {{cookiecutter.entity_id_type}} id;
	
	/**
	 * <h1>Email Address</h1>
	 * 
	 * <p>This is the email address for the user.</p>
	 */
    private String emailAddress = "";

	/**
	 * <h1>Enabled</h1>
	 * 
	 * <p>Controls allowing the user to login.</p>
	 */
	private boolean enabled = true;

	/**
	 * <h1>First Name</h1>
	 * 
	 * <p>This is the first name of the user.</p>
	 */
    private String firstName = "";
    
	/**
	 * <h1>Last Name</h1>
	 * 
	 * <p>This is the last name of the user.</p>
	 */
    private String lastName = "";
    
	/**
	 * <h1>Password</h1>
	 */
	@Column(
		length = 60
	)
	private String password = "";

	/**
	 * <h1>Secret</h1>
	 */
	private String secret = "";

 	/**
	 * <h1>Using 2FA</h1>
	 */
   private boolean using2FA = false;

	/**
	 * <h1>Roles</h1>
	 */
    @JoinTable(
		joinColumns = @JoinColumn(
			name = "user_id"
			, referencedColumnName = "id"
		)
		, inverseJoinColumns = @JoinColumn(
			name = "role_id"
			, referencedColumnName = "id"
		)
		, name = "users_roles"
	)
    @ManyToMany(
		fetch = FetchType.EAGER
	)
    private Collection<Role> roles;
	
{% if cookiecutter.has_lombok == "n" %}
	//
	// Access methods
	//

	public String getEmailAddress() { return emailAddress; }
	public void setEmailAddress( final String _value ) { emailAddress = _value; }

	public String getFirstName() { return firstName; }
	public void setFirstName( final String _value ) { firstName = _value; }

	public {{cookiecutter.entity_id_type}} getId() { return id; }
	public void setId( final {{cookiecutter.entity_id_type}} _value ) { id = _value; }

	public String getLastName() { return lastName; }
	public void setLastName( final String _value ) { lastName = _value; }

	public String getPassword() { return password; }
	public void setPassword( final String _value ) { password = _value; }

	public String getSecret() { return secret; }
	public void setSecret( final String _value ) { secret = _value; }

	public Collection<Role> getRoles() { return roles; }
	public void setRoles( final Collection<Role> _value ) { roles = _value; }

	public boolean isEnabled() { return enabled; }
	public void setEnabled( final boolean _value ) { enabled = _value; }

	public boolean isUsing2FA() { return using2FA; }
	public void setUsing2FA( boolean _value ) { using2FA = _value; }

{%- endif %}
	
}
