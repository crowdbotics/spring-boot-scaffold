package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.service.user;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.registration.PasswordResetToken;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.registration.VerificationToken;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user.User;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user.UserDto;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.repository.registration.PasswordResetTokenRepository;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.repository.registration.VerificationTokenRepository;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.repository.user.RoleRepository;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.repository.user.UserRepository;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.web.error.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <h1>User Support Service</h1>
 * 
 * <p>Spring service that implements the {@link UserSupport} interface.</p>
 * 
 * @author crowdbotics.com
 */
@Service
@Transactional
public class UserSupportService implements UserSupport {

	/**
	 * Autowired constructor for {@link UserSupportService}.
	 *
	 * @param _passwordEncoder					{@link PasswordEncoder}
	 * @param _passwordResetTokenRepository		{@link PasswordResetTokenRepository}
	 * @param _roleRepository					{@link RoleRepository}
	 * @param _sessionRegistry					{@link SessionRegistry}
	 * @param _userRepository					{@link UserRepository}
	 * @param _verificationTokenRepository		{@link VerificationTokenRepository}
	 */
	@Autowired
	public UserSupportService(
		final PasswordEncoder _passwordEncoder
		, final PasswordResetTokenRepository _passwordResetTokenRepository
		, final RoleRepository _roleRepository
		, final SessionRegistry _sessionRegistry
		, final UserRepository _userRepository
		, final VerificationTokenRepository _verificationTokenRepository
	)
	{
		super();

		passwordEncoder = _passwordEncoder;
		passwordTokenRepository = _passwordResetTokenRepository;
		roleRepository = _roleRepository;
		sessionRegistry = _sessionRegistry;
		userRepository = _userRepository;
		verificationTokenRepository = _verificationTokenRepository;
	}

	//
	// Spring initialization
	//
	
	@PostConstruct
	public void postInitialization()
	{
	}

	//
	// API
	//
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User registerNewUserAccount( 
		final UserDto _userDto
	)
	{
		if (emailExists( _userDto.getEmailAddress() ) == true) 
		{
			throw new UserAlreadyExistException( 
				"There is an account with that email adress: " + _userDto.getEmailAddress()
			);
		}
		
		final User newUser = new User();
		newUser.setFirstName( _userDto.getFirstName() );
		newUser.setLastName( _userDto.getLastName() );
		newUser.setPassword( passwordEncoder.encode( _userDto.getPassword() ) );
		newUser.setEmailAddress( _userDto.getEmailAddress() );
//		newUser.setUsing2FA(( _userDto.isUsing2FA() );
//		newUser.setRoles( Arrays.asList( roleRepository.findByName("ROLE_USER") ) );
		
		return userRepository.saveAndFlush( newUser );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getUser( final String _verificationToken ) {
		final VerificationToken token = verificationTokenRepository.findByToken( _verificationToken );
		if (token != null) {
			return token.getUser();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VerificationToken getVerificationToken( final String _verificationToken ) {
		return verificationTokenRepository.findByToken( _verificationToken );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveRegisteredUser( final User _user ) {
		userRepository.save( _user );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteUser( final User _user ) {
		final VerificationToken verificationToken = verificationTokenRepository.findByUser( _user );

		if (verificationToken != null) {
			verificationTokenRepository.delete( verificationToken );
		}

		final PasswordResetToken passwordToken = passwordTokenRepository.findByUser( _user );

		if (passwordToken != null) {
			passwordTokenRepository.delete( passwordToken );
		}

		userRepository.delete( _user );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createVerificationTokenForUser( final User _user, final String _token ) {
		final VerificationToken newVerificationToken = new VerificationToken( _token, _user );
		
		verificationTokenRepository.saveAndFlush( newVerificationToken );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VerificationToken generateNewVerificationToken( 
		final String _existingVerificationToken 
	)
	{
		final VerificationToken foundToken = verificationTokenRepository.findByToken( _existingVerificationToken );
		foundToken.updateToken( UUID.randomUUID().toString() );
		
		return verificationTokenRepository.saveAndFlush( foundToken );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPasswordResetTokenForUser( final User _user, final String _token ) {
		final PasswordResetToken newPasswordResetToken = new PasswordResetToken( _token, _user );
		passwordTokenRepository.saveAndFlush( newPasswordResetToken );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findUserByEmail( final String _email ) {
		return userRepository.findByEmailAddress( _email );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PasswordResetToken getPasswordResetToken( final String _token ) {
		return passwordTokenRepository.findByToken( _token );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getUserByPasswordResetToken( final String _token ) {
		return passwordTokenRepository.findByToken( _token ).getUser();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<User> getUserByID( final long _id ) {
		return userRepository.findById( _id );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeUserPassword( 
		final User _user
		, final String _password
	) 
	{
		_user.setPassword( passwordEncoder.encode( _password ) );
		userRepository.save( _user );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkIfValidOldPassword( final User _user, final String _oldPassword ) {
		return passwordEncoder.matches( _oldPassword, _user.getPassword());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String validateVerificationToken( final String _token ) {
		final VerificationToken verificationToken = verificationTokenRepository.findByToken( _token );
		if (verificationToken == null) {
			return TOKEN_INVALID;
		}

		final User user = verificationToken.getUser();
		final Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			verificationTokenRepository.delete( verificationToken );
			return TOKEN_EXPIRED;
		}

		user.setEnabled( true );
		// tokenRepository.delete(verificationToken);
		userRepository.save( user );
		return TOKEN_VALID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String generateQRUrl( final User _user ) throws UnsupportedEncodingException {
		return QR_PREFIX + URLEncoder.encode(
			String.format(
				"otpauth://totp/%s:%s?secret=%s&issuer=%s"
				, APP_NAME
				, _user.getEmailAddress()
				, _user.getSecret()
				, APP_NAME
			)
			, "UTF-8"
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User updateUser2FA( final boolean _use2FA ) {
		final Authentication curAuth = SecurityContextHolder.getContext()
			.getAuthentication();
		User currentUser = (User)curAuth.getPrincipal();
		currentUser.setUsing2FA( _use2FA );
		currentUser = userRepository.saveAndFlush( currentUser );
		
		final Authentication authentication = new UsernamePasswordAuthenticationToken(
			currentUser
			, currentUser.getPassword()
			, curAuth.getAuthorities()
		);
		SecurityContextHolder.getContext().setAuthentication( authentication );
		
		return currentUser;
	}

	private boolean emailExists( final String _email ) {
		return userRepository.findByEmailAddress( _email ) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getUsersFromSessionRegistry() 
	{
		return sessionRegistry.getAllPrincipals()
			.stream()
			.filter( u -> sessionRegistry.getAllSessions( u, false ).isEmpty() == false)
			.map( object -> 
				{
					if (object instanceof User) 
					{
						return ((User)object).getEmailAddress();
					}
					else
					{
						return object.toString();
					}
				}
			)
			.collect( Collectors.toList() );

	}

	//
	// Attributes
	//

	public static final String TOKEN_INVALID = "invalidToken";
	public static final String TOKEN_EXPIRED = "expired";
	public static final String TOKEN_VALID = "valid";

	public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
	public static String APP_NAME = "SpringRegistration";

	//
	// Autowired
	//

	private final PasswordEncoder passwordEncoder;
	private final PasswordResetTokenRepository passwordTokenRepository;
	private final RoleRepository roleRepository;
	private final SessionRegistry sessionRegistry;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;

}
