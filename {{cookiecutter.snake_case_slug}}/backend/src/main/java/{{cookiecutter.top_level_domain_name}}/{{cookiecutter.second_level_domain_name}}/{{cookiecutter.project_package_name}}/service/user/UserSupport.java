package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.service.user;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.registration.PasswordResetToken;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.registration.VerificationToken;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user.User;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user.UserDto;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.web.error.UserAlreadyExistException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

/**
 * <h1>User Support</h1>
 * 
 * <p>...</p>
 * 
 * @author crowdbotics.com
 */
public interface UserSupport 
{

    User registerNewUserAccount( UserDto _userDto ) throws UserAlreadyExistException;

    User getUser( String _verificationToken );

    void saveRegisteredUser( User _user );

    void deleteUser( User _user );

    void createVerificationTokenForUser( User _user, String _token );

    VerificationToken getVerificationToken( String _verificationToken );

    VerificationToken generateNewVerificationToken( String _token );

    void createPasswordResetTokenForUser( User _user, String _token );

    User findUserByEmail( String _emailAddress );

    PasswordResetToken getPasswordResetToken( String _token );

    User getUserByPasswordResetToken(String _token );

    Optional<User> getUserByID( {{cookiecutter.entity_id_type}} _id );

    void changeUserPassword( User _user, String _password );

    boolean checkIfValidOldPassword( User _user, String _password );

    String validateVerificationToken( String _token );

    String generateQRUrl( User _user ) throws UnsupportedEncodingException;

    User updateUser2FA( boolean _use2FA );

    List<String> getUsersFromSessionRegistry();

}
