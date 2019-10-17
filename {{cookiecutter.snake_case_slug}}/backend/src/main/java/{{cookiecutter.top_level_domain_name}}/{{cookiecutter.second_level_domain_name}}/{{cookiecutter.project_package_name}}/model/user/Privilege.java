package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user;

{% if cookiecutter.has_lombok == "y" %}
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
{% endif %}

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
{% if cookiecutter.has_lombok == "y" %}
@AllArgsConstructor
@Data
@NoArgsConstructor
{% endif %}
@Entity
public class Privilege 
{
	/**
	 * <h1>ID</h1>
	 * 
	 * <p>Internal ID for the user.</p>
	 */
	@Id
{% if cookiecutter.entity_id_type == "Long" %}
	@GeneratedValue
{% endif %}
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

	public Privilege(final String name) 
	{
		super();
		this.name = name;
	}

{% if cookiecutter.has_lombok == "n" %}

	public Privilege()
	{
		super();
	}

	//

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(final Collection<Role> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Privilege other = (Privilege) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Privilege [name=").append(name).append("]").append("[id=").append(id).append("]");
		return builder.toString();
	}
{% endif %}
}
