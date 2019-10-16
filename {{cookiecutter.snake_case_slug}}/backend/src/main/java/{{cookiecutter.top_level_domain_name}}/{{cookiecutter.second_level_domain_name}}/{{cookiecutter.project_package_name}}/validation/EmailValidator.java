package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>Email Validator</h1>
 * 
 * <p>Validator for correct email address format.</p>
 * 
 * @author crowdbotics.com
 */
public class EmailValidator 
	implements ConstraintValidator<ValidEmail, String> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(final ValidEmail constraintAnnotation) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(final String username, final ConstraintValidatorContext context) {
		return (validateEmail(username));
	}

	private boolean validateEmail(final String email) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	//
	// Attributes
	//
	
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
}
