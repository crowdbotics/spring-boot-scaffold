package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.registration;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user.User;

{% if cookiecutter.has_lombok == "y" %}
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
{%- endif %}

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * <h1>Password Reset Token</h1>
 *
 * <p>Represents an entity that holds the necessary information for
 * a user to reset their password.</p>
 *
 * @author crowdbotics.com
 */
{%- if cookiecutter.has_lombok == "y" %}
@AllArgsConstructor
@Data
@NoArgsConstructor
{%- endif %}
@Entity
public class PasswordResetToken 
{
{%- if cookiecutter.has_lombok == "n" %}
	/**
	 * No argument constructor for {@link PasswordResetToken}.
	 */
	public PasswordResetToken()
	{
		super();
	}
{%- endif %}

	/**
	 * Token constructor for {@link PasswordResetToken}.
	 * 
	 * @param _token					{@link String}
	 */
	public PasswordResetToken(
		final String _token
	) 
	{
		super();

		token = _token;
		expiryDate = calculateExpiryDate( EXPIRATION );
	}

	/**
	 * Token/User constructor for {@link VerificationToken}.
	 * 
	 * @param _token					{@link String}
	 */
	public PasswordResetToken(
		final String _token
		, final User _user
	)
	{
		super();
		
		token = _token;
		user = _user;
		expiryDate = calculateExpiryDate( EXPIRATION );
	}
	
	//
	// Operations
	//

	//
	// Attributes
	//

	private static final int EXPIRATION = 60 * 24;
	
	//
	// Fields
	//

	/**
	 * <h1>ID</h1>
	 * 
	 * <p>Internal ID for the user.</p>
	 */
	@Id
{%- if cookiecutter.entity_id_type == "Long" %}
	@GeneratedValue(strategy = GenerationType.AUTO)
{%- endif %}
	private {{cookiecutter.entity_id_type}} id;

	/**
	 * <h1>Token</h1>
	 * 
	 * <p>Token used to identify this password reset.</p>
	 */
	private String token;

	/**
	 * <h1>User</h1>
	 * 
	 * <p>User requesting the password reset.</p>
	 */
	@JoinColumn(
		foreignKey = @ForeignKey(
			name = "FK_VERIFY_USER"
		)
		, name = "user_id"
		, nullable = false
	)
	@OneToOne(
		fetch = FetchType.EAGER
		, targetEntity = User.class
	)
	private User user;

	/**
	 * <h1>Expiry Date</h1>
	 * 
	 * <p>Time interval the reset is allowed.</p>
	 */
	private Date expiryDate;

	//
	// Operations
	//

	private Date calculateExpiryDate(final int expiryTimeInMinutes) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

	public void updateToken(final String token) {
		this.token = token;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

{% if cookiecutter.has_lombok == "n" %}
	/**
	 * {@inheritDoc}
	 *
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(
		final Object _other
	)
	{
		if (this == _other) 
		{
			return true;
		}
		else if (_other == null)
		{
			return false;
		}
		else if (getClass() != _other.getClass())
		{
			return false;
		}
		
		final PasswordResetToken otherPasswordResetToken = (PasswordResetToken)_other;
		
		if (expiryDate == null) 
		{
			if (otherPasswordResetToken.expiryDate != null)
			{
				return false;
			}
		} 
		else if (expiryDate.equals( otherPasswordResetToken.expiryDate ) == false)
		{
			return false;
		}
		
		if (token == null) 
		{
			if (otherPasswordResetToken.token != null) 
			{
				return false;
			}
		} 
		else if (token.equals( otherPasswordResetToken.token ) == false) 
		{
			return false;
		}
		
		if (user == null) 
		{
			if (otherPasswordResetToken.user != null) 
			{
				return false;
			}
		} 
		else if (user.equals( otherPasswordResetToken.user ) == false) 
		{
			return false;
		}
		
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see Object#toString()
	 */
	@Override
	public String toString() 
	{
		final StringBuilder builder = new StringBuilder();
		builder
			.append( "Token [String=" )
			.append( token )
			.append( "]" )
			.append( "[Expires" )
			.append( expiryDate )
			.append( "]" )
			;
			
		return builder.toString();
	}
{% endif %}

	//

{% if cookiecutter.has_lombok == "n" %}
	//
	// Access methods
	//
	
	public {{cookiecutter.entity_id_type}} getId() { return id; }
	public void setId( final {{cookiecutter.entity_id_type}} _value ) { id = _value; }

	public String getToken() { return token; }
	public void setToken( final String _value ) { token = _value; }

	public User getUser() { return user; }
	public void setUser( final User _value ) { user = _value; }

	public Date getExpiryDate() { return expiryDate; }
	public void setExpiryDate( final Date _value ) { expiryDate = _value; }
{% endif %}
}
