package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user;

{% if cookiecutter.has_lombok == "y" %}
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
{%- endif %}

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * <h1>Privilege</h1>
 * 
 * <p>Represents an entity that ...</p>
 * 
 * @author crowdbotics.com
 */
{%- if cookiecutter.has_lombok == "y" %}
@AllArgsConstructor
@Data
@NoArgsConstructor
{%- endif %}
@Entity
public class Privilege 
{
{%- if cookiecutter.has_lombok == "n" %}
	/**
	 * No argument constructor for {@link Privilege}.
	 */
	public Privilege()
	{
		super();
	}
{%- endif %}

	public Privilege(final String name) 
	{
		super();
		this.name = name;
	}

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
		
		Privilege otherPrivilege = (Privilege)_other;
		
		if (name == null)
		{
			if (otherPrivilege.name != null)
			{
				return false;
			}
		} 
		else if (name.equals( otherPrivilege.name ) == false)
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
			.append( "Privilege [name=" )
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

	/**
	 * <h1>Name</h1>
	 */
	private String name;

	/**
	 * <h1>Roles</h1>
	 */
	@ManyToMany(mappedBy = "privileges")
	private Collection<Role> roles;

{% if cookiecutter.has_lombok == "n" %}
	//
	// Access methods
	//

	public {{cookiecutter.entity_id_type}} getId() { return id; }
	public void setId( final {{cookiecutter.entity_id_type}} _value ) { id = _value; }

	public String getName() { return name; }
	public void setName( final String _value ) { name = _value; }

	public Collection<Role> getRoles() { return roles; }
	public void setRoles(final Collection<Role> _value) { this.roles = _value; }
{%- endif %}
}
