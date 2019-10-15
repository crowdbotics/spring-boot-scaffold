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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

	/**
	 * 
	 */
	@RequestMapping( 
		method = RequestMethod.POST
		, value = "/user/registration"
	)
	@ResponseBody
	public GenericResponse registerUserAccount( 
		@Valid final UserDto _userDto
		, final HttpServletRequest request
	) 
	{
		LOGGER.debug("Registering user account with information: {}", _userDto );

		final User registered = userSupport.registerNewUserAccount( _userDto );
		
		applicationEventPublisher.publishEvent( new OnRegistrationCompleteEvent(
			registered
			, request.getLocale()
			, getAppUrl( request )
		) );
		
		return new GenericResponse( "success" );
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
	public String confirmRegistration(
		final HttpServletRequest request
		, final Model model
		, @RequestParam( "token" ) final String token
	)
		throws UnsupportedEncodingException
	{
		Locale locale = request.getLocale();
		final String result = userSupport.validateVerificationToken(token);
		if (result.equals("valid")) {
			final User user = userSupport.getUser(token);
			// if (user.isUsing2FA()) {
			// model.addAttribute("qr", userSupport.generateQRUrl(user));
			// return "redirect:/qrcode.html?lang=" + locale.getLanguage();
			// }
			authWithoutPassword(user);
			model.addAttribute("message", messageSource.getMessage("message.accountVerified", null, locale));
			return "redirect:/console.html?lang=" + locale.getLanguage();
		}

		model.addAttribute("message", messageSource.getMessage("auth.message." + result, null, locale));
		model.addAttribute("expired", "expired".equals(result));
		model.addAttribute("token", token);
		
		return "redirect:/badUser.html?lang=" + locale.getLanguage();
	}

	// user activation - verification

	/**
	 * 
	 */
	@RequestMapping(value = "/user/resendRegistrationToken", method = RequestMethod.GET)
	@ResponseBody
	public GenericResponse resendRegistrationToken(final HttpServletRequest request, @RequestParam("token") final String existingToken) {
		final VerificationToken newToken = userSupport.generateNewVerificationToken(existingToken);
		final User user = userSupport.getUser(newToken.getToken());
		mailSender.send(constructResendVerificationTokenEmail(getAppUrl(request), request.getLocale(), newToken, user));
		return new GenericResponse( messageSource.getMessage(
			"message.resendToken"
			, null
			, request.getLocale()
		));
	}

	/**
	 * 
	 */
	// Reset password
	@RequestMapping(value = "/user/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse resetPassword(final HttpServletRequest request, @RequestParam("email") final String userEmail) {
		final User user = userSupport.findUserByEmail(userEmail);
		if (user != null) {
			final String token = UUID.randomUUID().toString();
			userSupport.createPasswordResetTokenForUser(user, token);
			mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, user));
		}
		return new GenericResponse(messageSource.getMessage("message.resetPasswordEmail", null, request.getLocale()));
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
	public String showChangePasswordPage(final Locale locale, final Model model, @RequestParam("id") final long id, @RequestParam("token") final String token) {
		final String result = userSecuritySupport.validatePasswordResetToken(id, token);
		if (result != null) {
			model.addAttribute("message", messageSource.getMessage("auth.message." + result, null, locale));
			return "redirect:/login?lang=" + locale.getLanguage();
		}
		return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/user/savePassword", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse savePassword(final Locale locale, @Valid PasswordDto passwordDto) {
		final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userSupport.changeUserPassword(user, passwordDto.getNewPassword());
		return new GenericResponse( messageSource.getMessage( "message.resetPasswordSuc", null, locale ) );
	}

	/**
	 * 
	 */
	// change user password
	@RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse changeUserPassword(final Locale locale, @Valid PasswordDto passwordDto) {
		final User user = userSupport.findUserByEmail(
			((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmailAddress()
		);
		
		if (userSupport.checkIfValidOldPassword( user, passwordDto.getOldPassword() ) == false) {
			throw new InvalidOldPasswordException();
		}
		userSupport.changeUserPassword(user, passwordDto.getNewPassword());
		
		return new GenericResponse(messageSource.getMessage("message.updatePasswordSuc", null, locale));
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/user/update/2fa", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse modifyUser2FA(@RequestParam("use2FA") final boolean use2FA) throws UnsupportedEncodingException {
		final User user = userSupport.updateUser2FA(use2FA);
		if (use2FA) {
			return new GenericResponse(userSupport.generateQRUrl(user));
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
