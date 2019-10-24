{{cookiecutter.license_header}}

package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.domain.model;

/**
 * <h1>Application User</h1>
 *
 * <p>Interface for any entity that has an ID field.</p>
 *
 * @author crowdbotics.com
 */
public interface Identifiable
{
	/**
	 * Return the contents of the ID field.
	 * 
	 * @return {@code {{cookiecutter.entity_id_type}}}.
	 */
	{{cookiecutter.entity_id_type}} getId();
	
	/**
	 * Assign the ID field.
	 * 
	 * @param _value {@{{cookiecutter.entity_id_type}}}
	 */
	void setId( {{cookiecutter.entity_id_type}} _value );
}
