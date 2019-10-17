package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * <h1>Role</h1>
 * 
 * <p>Represents an entity that holds the role for a user.</p>
 * 
 * @author crowdbotics.com
 */
@Entity
public class Role
{
{%- if cookiecutter.has_lombok == "n" %}
	/**
	 * No argument constructor for {@link Role}.
	 */
	public Role()
	{
		super();
	}
	
	/**
	 * All fields constructor for {@link }.
	 * 
	 */
	public Role(
		final String _name
	)
	{
		super();
	
		name = _name;
	}

{%- endif %}

	//
	// Operations
	//

{%- if cookiecutter.has_lombok == "n" %}
	/**
	 * {@inheritDoc}
	 *
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(
		final Object _other
	)
	{
		if (this == _other) 
		{
			return true;
		}
		else if (_other == null)
		{
			return false;
		}
		else if (getClass() != _other.getClass())
		{
			return false;
		}
		
		final Role otherRole = (Role)_other;
		
		if (name.equals( otherRole.name ) == false)
		{
			return false;
		}
		
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) 
			? 0 
			: name.hashCode()
		);
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see Object#toString()
	 */
	@Override
	public String toString() 
	{
		final StringBuilder builder = new StringBuilder();
		builder
			.append( "Role [name=" )
			.append( name )
			.append( "]" )
			.append( "[id=" )
			.append( id )
			.append( "]" )
			;
			
		return builder.toString();
	}
{%- endif %}

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

	private String name;

	@ManyToMany(mappedBy = "roles")
	private Collection<User> users;

	@ManyToMany
	@JoinTable(name = "roles_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
	private Collection<Privilege> privileges;

{% if cookiecutter.has_lombok == "n" %}
	//
	// Access methods
	//

	public {{cookiecutter.entity_id_type}} getId() { return id; }
	public void setId( final {{cookiecutter.entity_id_type}} _value ) { id = _value; }

	public String getName() { return name; }
	public void setName( final String _value ) { this.name = _value; }

	public Collection<User> getUsers() { return users; }
	public void setUsers( final Collection<User> _value ) { this.users = _value; }

	public Collection<Privilege> getPrivileges() { return privileges; }
	public void setPrivileges( final Collection<Privilege> _value ) { this.privileges = _value; }
{%- endif %}
}
