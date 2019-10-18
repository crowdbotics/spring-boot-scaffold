package com.crowdbotics.sample.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <h1>MVC Configuration</h1>
 *
 * <p>...</p>
 *
 * @author crowdbotics.com
 */
@Configuration
public class MvcConfiguration
	implements WebMvcConfigurer
{
	/**
	 * {@inheritDoc}
	 *
	 * @see WebMvcConfigurer#addViewControllers(ViewControllerRegistry)
	 */
	@Override
	public void addViewControllers(
		final ViewControllerRegistry _registry
	)
	{
		_registry.addViewController( "/anonymous.html" );

		_registry.addViewController( "/login.html" );
		_registry.addViewController( "/homepage.html" );
		_registry.addViewController( "/admin/adminpage.html" );
		_registry.addViewController( "/accessDenied" );
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see WebMvcConfigurer#addResourceHandlers(ResourceHandlerRegistry)
	 */
	@Override
	public void addResourceHandlers(
		final ResourceHandlerRegistry _handlerRegistry
	)
	{
		_handlerRegistry.addResourceHandler( "/static/**" ).addResourceLocations( "/WEB-INF/view/react/build/static/" );

		_handlerRegistry.addResourceHandler( "/*.js" ).addResourceLocations( "/WEB-INF/view/react/build/" );
		_handlerRegistry.addResourceHandler( "/*.json" ).addResourceLocations( "/WEB-INF/view/react/build/" );
		_handlerRegistry.addResourceHandler( "/*.ico" ).addResourceLocations( "/WEB-INF/view/react/build/" );
		_handlerRegistry.addResourceHandler( "/index.html" ).addResourceLocations( "/WEB-INF/view/react/build/index.html" );
	}

}
