package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user;

{%- if cookiecutter.has_lombok == "y" -%}
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
{% endif %}

import javax.persistence.*;
import java.util.Collection;

/**
 * <h1>User</h1>
 * 
 * <p>Represents an entity that identifies a user of the site.</p>
 * 
 * @author crowdbotics.com
 */
{%- if cookiecutter.has_lombok == "y" -%}
@AllArgsConstructor
@Data
@NoArgsConstructor
{% endif %}
@Entity
public class User {

	/**
	 * <h1>ID</h1>
	 * 
	 * <p>Internal ID for the user.</p>
	 */
    @Id
{%- if cookiecutter.entity_id_type == "Long" -%}
    @GeneratedValue
{% endif %}
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
	private String secret;

    private boolean using2FA;

	/**
	 * <h1>Roles</h1>
	 */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
		name = "users_roles"
		, joinColumns = @JoinColumn(
			name = "user_id", referencedColumnName = "id"
		)
		, inverseJoinColumns = @JoinColumn(
			name = "role_id", referencedColumnName = "id"
		)
	)
    private Collection<Role> roles;

	
{%- if cookiecutter.has_lombok == "n" -%}

	public String getEmailAddress() { return emailAddress; }

	public {{cookiecutter.entity_id_type}} getId() { return id; }

	public String getPassword() { return password; }

	public String getSecret() { return secret; }

	public Collection<Role> getRoles() { return roles; }

	public boolean isEnabled() { return enabled; }

	public boolean isUsing2FA() { return using2FA; }

	public void setEmailAddress( final String _value ) { emailAddress = _value; }
	
	public void setEnabled( final boolean _value ) { enabled = _value; }

	public void setFirstName( final String _value ) { firstName = _value; }

	public void setLastName( final String _value ) { lastName = _value; }

	public void setPassword( final String _value ) { password = _value; }

	public void setSecret( final String _value ) { secret = _value; }

	public void setRoles( final Collection<Role> _value ) { roles = _value; }

	public void setUsing2FA( boolean _value ) { using2FA = _value; }

{% endif %}
	
}
