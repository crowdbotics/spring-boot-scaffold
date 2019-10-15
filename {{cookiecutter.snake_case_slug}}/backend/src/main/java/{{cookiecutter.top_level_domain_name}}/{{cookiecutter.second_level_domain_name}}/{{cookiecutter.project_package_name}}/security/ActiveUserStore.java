package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.security;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Active User Store</h1>
 * 
 * <p>...</p>
 * 
 * @author crowdbotics.com
 */
public class ActiveUserStore {

	/**
	 * No argument constructor for {@link ActiveUserStore}.
	 */
	public ActiveUserStore() 
	{
		super();
	}

	//
	// Attributes
	//

	public List<String> users = new ArrayList<String>();
	public List<String> getUsers() { return users; }
	public void setUsers( final List<String> _value ) { users = _value; }

}
