package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SimpleSavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <h1>Security Configuration</h1>
 * 
 * @author crowdbotics.com
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	/**
	 * 
	 */
	@Override
	protected void configure( HttpSecurity _http ) throws Exception {
		_http
			.oauth2Login()
			.and()
			.csrf()
				.csrfTokenRepository( CookieCsrfTokenRepository.withHttpOnlyFalse() )
				.and()
			.authorizeRequests()
				.antMatchers( "/**/*.{js,html,css}" ).permitAll()
				.antMatchers( "/", "/api/user" ).permitAll()
				.anyRequest().authenticated();
	}

	/**
	 * <p>This bean overrides the default request cache. It saves the referrer header 
	 * (misspelled referer in real life), so Spring Security can redirect back to it 
	 * after authentication. The referrer-based request cache comes in handy when you’re 
	 * developing React on http://localhost:3000 and want to be redirected back there 
	 * after logging in.</p>
	 */
	@Bean
	@Profile("dev")
	public RequestCache refererRequestCache() {
		return new HttpSessionRequestCache() {
			@Override
			public void saveRequest( HttpServletRequest request, HttpServletResponse response ) {
				String referrer = request.getHeader("referer");
				if (referrer != null) {
					request.getSession().setAttribute("SPRING_SECURITY_SAVED_REQUEST", new SimpleSavedRequest(referrer));
				}
			}
		};
	}
}
