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

    User registerNewUserAccount( UserDto _userDti ) throws UserAlreadyExistException;

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

    User findUserByEmail(String email);

    PasswordResetToken getPasswordResetToken(String token);

    User getUserByPasswordResetToken(String token);

    Optional<User> getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);

    String generateQRUrl(User user) throws UnsupportedEncodingException;

    User updateUser2FA(boolean use2FA);

    List<String> getUsersFromSessionRegistry();

}