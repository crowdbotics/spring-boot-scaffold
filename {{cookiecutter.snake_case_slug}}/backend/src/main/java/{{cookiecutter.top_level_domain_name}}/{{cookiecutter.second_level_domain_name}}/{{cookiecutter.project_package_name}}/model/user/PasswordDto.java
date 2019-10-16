package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.validation.ValidPassword;

public class PasswordDto {

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword( final String _value ) {
		oldPassword = _value;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword( final String _value ) {
		newPassword = _value;
	}

	//
	// Attributes
	//
	
	@ValidPassword
	private String newPassword;

	private String oldPassword;

}
