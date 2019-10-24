{{cookiecutter.license_header}}

package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <h1>Duplicate Username Exception</h1>
 *
 * @author crowdbotics.com
 */
@ResponseStatus(
	reason="Username is already in use."
	, value = HttpStatus.CONFLICT
)
public class DuplicateUsernameException
	extends RuntimeException
{
}
