package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.web;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.registration.VerificationToken;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user.PasswordDto;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user.Privilege;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user.User;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user.UserDto;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.registration.OnRegistrationCompleteEvent;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.service.security.UserSecuritySupport;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.service.user.UserSupport;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.web.error.InvalidOldPasswordException;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.web.utility.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <h1>Registration Controller</h1>
 * 
 * <p>Spring REST controller for user registration.</p>
 * 
 * @author crowdbotics.com
 */
@Controller
public class RegistrationController {

	/**
	 * Autowired constructor for {@link RegistrationController}.
	 *
	 * @param _applicationEventPublisher		{@link ApplicationEventPublisher}
	 * @param _authenticationManager			{@link AuthenticationManager}
	 * @param _environment						{@link Environment}
	 * @param _mailSender						{@link JavaMailSender}
	 * @param _messageSource					{@link MessageSource}
	 * @param _userSecuritySupport				{@link UserSecuritySupport}
	 * @param _userSupport						{@link UserSupport}
	 */
	@Autowired
	public RegistrationController(
		final ApplicationEventPublisher _applicationEventPublisher
		, final AuthenticationManager _authenticationManager
		, final Environment _environment
		, final JavaMailSender _mailSender
		, final MessageSource _messageSource
		, final UserSecuritySupport _userSecuritySupport
		, final UserSupport _userSupport
	)
	{
		super();

		applicationEventPublisher = _applicationEventPublisher;
		authenticationManager = _authenticationManager;
		environment = _environment;
		mailSender = _mailSender;
		messageSource = _messageSource;
		userSecuritySupport = _userSecuritySupport;
		userSupport = _userSupport;
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
	
	// Registration

	//
	//
	public static final String USER_REGISTRATION = "/user/registration";
	/**
	 * 
	 */
	@RequestMapping( 
		method = RequestMethod.POST
		, value = USER_REGISTRATION
	)
	@ResponseBody
	public GenericResponse registerUserAccount( 
		@RequestBody @Valid final UserDto _userDto
		, final HttpServletRequest request
	) 
	{
		LOGGER.debug( "Registering user account with information: {}", _userDto );

		final User registeredUser = userSupport.registerNewUserAccount( _userDto );
		
		applicationEventPublisher.publishEvent( new OnRegistrationCompleteEvent(
			registeredUser
			, request.getLocale()
			, getAppUrl( request )
		) );
		
		return new GenericResponse( "success" );
	}

	//
	//
	public static final String REGISTRATION_CONFIRM = "/registrationConfirm";
	/**
	 * 
	 */
	@RequestMapping(
		method = RequestMethod.GET
		, value = REGISTRATION_CONFIRM
	)
	public String confirmRegistration(
		final HttpServletRequest _httpServletRequest
		, final Model _model
		, @RequestParam( "token" ) final String _token
	)
		throws UnsupportedEncodingException
	{
		final Locale locale = _httpServletRequest.getLocale();
		final String result = userSupport.validateVerificationToken( _token );
		if (result.equals( "valid" ) == true)
		{
			final User user = userSupport.getUser( _token );
			// if (user.isUsing2FA()) {
			// model.addAttribute("qr", userSupport.generateQRUrl(user));
			// return "redirect:/qrcode.html?lang=" + locale.getLanguage();
			// }
			authWithoutPassword( user );
			_model.addAttribute(
				"message"
				, messageSource.getMessage(
					"message.accountVerified"
					, null
					, locale
				)
			);

			return "redirect:/console.html?lang=" + locale.getLanguage();
		}

		_model.addAttribute(
			"message"
			, messageSource.getMessage(
				"auth.message." + result
				, null
				, locale
			)
		);
		_model.addAttribute(
			"expired"
			, "expired".equals( result )
		);
		_model.addAttribute(
			"token"
			, _token
		);
		
		return "redirect:/badUser.html?lang=" + locale.getLanguage();
	}

	// user activation - verification

	/**
	 * 
	 */
	@RequestMapping(
		method = RequestMethod.GET
		, value = "/user/resendRegistrationToken"
	)
	@ResponseBody
	public GenericResponse resendRegistrationToken(
		final HttpServletRequest _request
		, @RequestParam( "token" ) final String _existingToken
	)
	{
		final VerificationToken newToken = userSupport.generateNewVerificationToken( _existingToken );
		final User user = userSupport.getUser( newToken.getToken() );
		
		mailSender.send( constructResendVerificationTokenEmail( 
			getAppUrl( _request )
			, _request.getLocale()
			, newToken
			, user
		) );
		
		return new GenericResponse( messageSource.getMessage(
			"message.resendToken"
			, null
			, _request.getLocale()
		));
	}

	/**
	 * Reset password
	 */
	@RequestMapping(
		method = RequestMethod.POST
		, value = "/user/resetPassword"
	)
	@ResponseBody
	public GenericResponse resetPassword(
		final HttpServletRequest _request
		, @RequestParam( "email" ) final String _userEmail
	)
	{
		final User user = userSupport.findUserByEmail( _userEmail );
		
		if (user != null) 
		{
			final String token = UUID.randomUUID().toString();
			userSupport.createPasswordResetTokenForUser( user, token );
			mailSender.send( constructResetTokenEmail(
				getAppUrl( _request )
				, _request.getLocale()
				, token
				, user
			) );
		}
		
		return new GenericResponse( messageSource.getMessage(
			"message.resetPasswordEmail"
			, null
			, _request.getLocale()
		) );
	}

	/**
	 * 
	 */
	@RequestMapping(
		method = RequestMethod.GET
		, value = "/user/changePassword"
	)
	public String showChangePasswordPage(
		final Locale _locale
		, final Model _model
		, @RequestParam("id") final {{cookiecutter.entity_id_type}} _id
		, @RequestParam("token") final String _token
	) 
	{
		final String result = userSecuritySupport.validatePasswordResetToken( _id, _token );
		if (result != null) 
		{
			_model.addAttribute( "message", messageSource.getMessage( 
				"auth.message." + result
				, null
				, _locale
			) );

			return "redirect:/login?lang=" + _locale.getLanguage();
		}

		return "redirect:/updatePassword.html?lang=" + _locale.getLanguage();
	}

	/**
	 * 
	 */
	@RequestMapping(
		method = RequestMethod.POST
		, value = "/user/savePassword"
	)
	@ResponseBody
	public GenericResponse savePassword(
		final Locale _locale
		, @RequestBody @Valid final PasswordDto _passwordDto
	) 
	{
		final User user = (User)SecurityContextHolder
			.getContext()
			.getAuthentication()
			.getPrincipal()
			;
			
		userSupport.changeUserPassword( user, _passwordDto.getNewPassword() );
		
		return new GenericResponse( messageSource.getMessage( 
			"message.resetPasswordSuc"
			, null
			, _locale 
		) );
	}

	/**
	 * change user password
	 */
	@RequestMapping(
		method = RequestMethod.POST
		, value = "/user/updatePassword"
	)
	@ResponseBody
	public GenericResponse changeUserPassword(
		final Locale _locale
		, @RequestBody @Valid final PasswordDto _passwordDto
	)
	{
		final User user = userSupport.findUserByEmail(
			((User)SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal()
			).getEmailAddress()
		);
		
		if (userSupport.checkIfValidOldPassword( user, _passwordDto.getOldPassword() ) == false) 
		{
			throw new InvalidOldPasswordException();
		}
		
		userSupport.changeUserPassword( user, _passwordDto.getNewPassword());
		
		return new GenericResponse( messageSource.getMessage(
			"message.updatePasswordSuc"
			, null
			, _locale
		) );
	}

	/**
	 * 
	 */
	@RequestMapping(
		method = RequestMethod.POST
		, value = "/user/update/2fa"
	)
	@ResponseBody
	public GenericResponse modifyUser2FA(
		@RequestParam("use2FA") final boolean _use2FA
	)
		throws UnsupportedEncodingException 
	{
		final User user = userSupport.updateUser2FA( _use2FA );
	
		if (_use2FA == true)
		{
			return new GenericResponse( userSupport.generateQRUrl( user ) );
		}
		
		return null;
	}

	// ============== NON-API ============

	private SimpleMailMessage constructResendVerificationTokenEmail(
		final String _contextPath
		, final Locale _locale
		, final VerificationToken _newToken
		, final User _user
	)
	{
		final String confirmationUrl = _contextPath + "/registrationConfirm.html?token=" + _newToken.getToken();
		final String message = messageSource.getMessage(
			"message.resendToken",
			null,
			_locale
		);
		return constructEmail(
			"Resend Registration Token",
			message + " \r\n" + confirmationUrl,
			_user
		);
	}

	private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final User user) {
		final String url = contextPath + "/user/changePassword?id=" + user.getId() + "&token=" + token;
		final String message = messageSource.getMessage("message.resetPassword", null, locale);
		return constructEmail("Reset Password", message + " \r\n" + url, user);
	}

	/**
	 * Construct an email message from the specified parameters.
	 * 
	 * @param _subject					{@link String}
	 * @param _body						{@link String}
	 * @param _user						{@link User}
	 * @return {@link SimpleMailMessage}
	 */
	private SimpleMailMessage constructEmail(
		final String _subject
		, final String _body
		, final User _user
	)
	{
		final SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setSubject( _subject );
		mailMessage.setText( _body );
		mailMessage.setTo( _user.getEmailAddress() );
		mailMessage.setFrom( environment.getProperty( "email.fromEmailAddress" ) );
		
		return mailMessage;
	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
		try {
			request.login(username, password);
		} catch (ServletException e) {
			LOGGER.error("Error while login ", e);
		}
	}

	public void authWithAuthManager(HttpServletRequest request, String username, String password) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		authToken.setDetails(new WebAuthenticationDetails(request));
		Authentication authentication = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
	}

	/**
	 * 
	 */
	public void authWithoutPassword(
		final User _user
	)
	{
		final List<Privilege> privileges = _user.getRoles()
			.stream()
			.map( role -> role.getPrivileges() )
			.flatMap( list -> list.stream() )
			.distinct()
			.collect( Collectors.toList() )
			;
		final List<GrantedAuthority> authorities = privileges
			.stream()
			.map( p -> new SimpleGrantedAuthority( p.getName() ) )
			.collect( Collectors.toList() )
			;

		final Authentication authentication = new UsernamePasswordAuthenticationToken(
			_user
			, null
			, authorities
		);

		SecurityContextHolder.getContext().setAuthentication( authentication );
	}
	
	//
	// Attributes
	//
	private final Logger LOGGER = LoggerFactory.getLogger( getClass() );

	//
	// Autowired
	//

	private final ApplicationEventPublisher applicationEventPublisher;
	private final AuthenticationManager authenticationManager;
	private final Environment environment;
	private final JavaMailSender mailSender;
	private final MessageSource messageSource;
	private final UserSecuritySupport userSecuritySupport;
	private final UserSupport userSupport;

}
