package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.registration;

import com.crowdbotics.sample.model.user.User;

{%- if cookiecutter.has_lombok == "y" -%}
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
{% endif %}

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
{% if cookiecutter.has_lombok == "y" %}
@AllArgsConstructor
@Data
@NoArgsConstructor
{% endif %}
@Entity
public class PasswordResetToken {

	private static final int EXPIRATION = 60 * 24;

	@Id
{% if cookiecutter.entity_id_type == "Long" %}
	@GeneratedValue(strategy = GenerationType.AUTO)
{% endif %}
	private {{cookiecutter.entity_id_type}} id;

	private String token;

	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	private Date expiryDate;

	public PasswordResetToken(final String token) {
		super();

		this.token = token;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	public PasswordResetToken(final String token, final User user) {
		super();

		this.token = token;
		this.user = user;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

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

	//

{% if cookiecutter.has_lombok == "n" %}
	public PasswordResetToken() 
	{
		super();
	}

	//
	public Long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(final String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(final Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
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
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PasswordResetToken other = (PasswordResetToken) obj;
		if (expiryDate == null) {
			if (other.expiryDate != null) {
				return false;
			}
		} else if (!expiryDate.equals(other.expiryDate)) {
			return false;
		}
		if (token == null) {
			if (other.token != null) {
				return false;
			}
		} else if (!token.equals(other.token)) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Token [String=").append(token).append("]").append("[Expires").append(expiryDate).append("]");
		return builder.toString();
	}
{% endif %}

}