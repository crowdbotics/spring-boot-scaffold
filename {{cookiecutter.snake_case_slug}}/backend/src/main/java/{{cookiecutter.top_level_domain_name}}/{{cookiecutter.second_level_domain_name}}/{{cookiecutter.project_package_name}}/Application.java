package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <h1>Application</h1>
 * 
 * <p>This is the main entry point for the application</p>
 * 
 * @author crowdbotics.com
 */
@SpringBootApplication
public class Application {

	public static void main( final String [] args ) {
		SpringApplication.run( Application.class, args );
	}
}
