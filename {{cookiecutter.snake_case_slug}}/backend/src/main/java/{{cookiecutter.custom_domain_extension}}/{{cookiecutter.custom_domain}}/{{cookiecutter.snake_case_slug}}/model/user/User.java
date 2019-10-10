package {{cookiecutter.custom_domain_extension}}.{{cookiecutter.custom_domain}}.{{cookiecutter.custom_project_package}}.model.user;

{%- if cookiecutter.has_lombok == "y" -%}
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
{% endif %}

import javax.persistence.Entity;
import javax.persistence.Id;

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
	 * <h1>Name</h1>
	 * 
	 * <p>This is the name of the user.</p>
	 */
    private String name;
    
	/**
	 * <h1>Email Address</h1>
	 * 
	 * <p>This is the email address for the user.</p>
	 */
    private String emailAddress;
}
