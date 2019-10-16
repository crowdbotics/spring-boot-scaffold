package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.web.utility;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>Generic Response</h1>
 * 
 * <p>...</p>
 * 
 * @author crowdbotics.com
 */
public class GenericResponse {
	/**
	 * 
	 */
	public GenericResponse(final String message) {
		super();
		this.message = message;
	}

	/**
	 * 
	 */
	public GenericResponse(final String message, final String error) {
		super();
		this.message = message;
		this.error = error;
	}

	/**
	 * 
	 */
	public GenericResponse(List<ObjectError> allErrors, String error) {
		this.error = error;
		String temp = allErrors.stream().map(e -> {
			if (e instanceof FieldError) {
				return "{\"field\":\"" + ((FieldError) e).getField() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
			} else {
				return "{\"object\":\"" + e.getObjectName() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
			}
		}).collect(Collectors.joining(","));
		this.message = "[" + temp + "]";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(final String error) {
		this.error = error;
	}

	//
	// Attributes
	//

	private String message;
	private String error;

}
