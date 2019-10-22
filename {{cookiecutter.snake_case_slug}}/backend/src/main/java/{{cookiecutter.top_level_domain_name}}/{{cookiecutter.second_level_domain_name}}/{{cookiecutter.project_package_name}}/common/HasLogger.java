package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>Has Logger</h1>
 * 
 * <p>HasLogger is a feature interface that provides Logging capability for anyone
 * implementing it where logger needs to operate in serializable environment
 * without being static.</p>
 *
 * @author crowdbotics.com
 */
public interface HasLogger
{
	/**
	 * Return the logger for this object.
	 *
	 * @return {@link Logger}
	 */
	default Logger logger() 
	{
		return LoggerFactory.getLogger( getClass() );
	}
}
