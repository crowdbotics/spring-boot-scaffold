{{cookiecutter.license_header}}

package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.domain.model;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.common.HasLogger;
{%- if cookiecutter.has_lombok == "y" %}
import lombok.Getter;
import lombok.Setter;
{%- endif %}
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
{%- if cookiecutter.entity_id_type == "String" %}
import java.util.UUID;
{%- endif %}
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/** 
 * <h1>Abstract Entity</h1>
 *
 * @author crowdbotics.com
 */
@Access(
	AccessType.FIELD
)
{%- if cookiecutter.has_lombok == "y" %}
@Getter
{%- endif %}
@MappedSuperclass
{%- if cookiecutter.has_lombok == "y" %}
@Setter
{%- endif %}
public class AbstractEntity
	implements HasLogger
		, Identifiable
		, Persistable<{{cookiecutter.entity_id_type}}>
		, Serializable
{
	/** 
	 * No argument constructor for a {@link AbstractEntity}.
	 * 
	 * Assigns a random UUID to the {@code id} field
	 * and sets the {@code newEntity} field to {@code true}.
	 */
	public AbstractEntity()
	{
		super();

{%- if cookiecutter.entity_id_type == "Long" %}
		id = Long.valueOf( 0 );
{%- endif %}
{%- if cookiecutter.entity_id_type == "String" %}
		id = UUID.randomUUID().toString();
		newEntity = true;
{%- endif %}
	}

	/**
	 * Copy constructor for {@link AbstractEntity}.
	 * 
	 * @param _source					{@link AbstractEntity} to copy
	 */
	public AbstractEntity(
		final AbstractEntity _source
	)
	{
		super();
		
		id = _source.id;
	}

	/**
	 * Copy from another {@link AbstractEntity}.
	 * 
	 * @param _abstractEntity			{@link AbstractEntity}
	 * 										source of the copy.
	 * @return {@code AbstractEntity}
	 */
	public AbstractEntity copyFrom(
		final AbstractEntity _abstractEntity
	)
	{
		id = _abstractEntity.id;
{%- if cookiecutter.entity_id_type == "String" %}
		newEntity = false;
{%- endif %}

		return this;
	}

	//
	// Operations
	//

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
		// Are they the same object?
		if (this == _other)
		{
			// Yes...
			return true;
		}

		// Is the other object null ?
		if (_other == null)
		{
			// Yes, null never equals
			return false;
		}

		// Is the other an AbstractEntity?
		if (_other instanceof AbstractEntity == false)
		{
			// No, not equal
			return false;
		}

		// Convert and test equals
		AbstractEntity otherAbstractEntity = (AbstractEntity)_other;
		return getId().equals( otherAbstractEntity.getId() );
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return getId().hashCode();
	}

	/**
	 *
	 */
	@PostLoad
	public void postLoad()
	{
{%- if cookiecutter.entity_id_type == "String" %}
		newEntity = false;
{%- endif %}

		logger().trace(
			String.format(
				"postLoad(): [%s]"
				, (getId() != null)
					? getId().toString()
					: ""
			)
		);
	}

	/**
	 *
	 */
	@PostPersist
	public void postPersist()
	{
{%- if cookiecutter.entity_id_type == "String" %}
		newEntity = false;
{%- endif %}

		logger().trace(
			String.format(
				"postPersist(): [%s]"
				, (getId() != null)
					? getId().toString()
					: ""
			)
		);
	}

	/**
	 *
	 */
	@PostUpdate
	public void postUpdate()
	{
{%- if cookiecutter.entity_id_type == "String" %}
		newEntity = false;
{%- endif %}

		logger().trace(
			String.format(
				"postUpdate(): [%s]"
				, (getId() != null)
					? getId().toString()
					: ""
			)
		);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see Object#toString()
	 */
	@Override
	public String toString()
	{
		return String.format(
			"%s[ID: %s]"
			, getClass().getSimpleName()
			, (getId() != null)
				? getId().toString()
				: ""
		);
	}

	//
	// Attributes
	//

	//
	//
	public static final String ID = "id";
	/**
	 * ID for this POJO.
	 */
	@Column(
{%- if cookiecutter.entity_id_type == "Long" %}
		name = "id"
		, nullable = false
		, unique = true
		, updatable = false
{%- endif %}
{%- if cookiecutter.entity_id_type == "String" %}
		length = 36
		, name = "id"
		, nullable = false
		, unique = true
		, updatable = false
{%- endif %}
	)
	@Id
{%- if cookiecutter.entity_id_type == "Long" %}
	@GeneratedValue( strategy = GenerationType.IDENTITY )
{%- endif %}
	@NotNull(
		message = "Id must not be null."
	)
	private {{cookiecutter.entity_id_type}} id;
{% if cookiecutter.has_lombok == "n" %}
	@Override
	public {{cookiecutter.entity_id_type}} getId() { return id; }
	@Override
	public void setId( final {{cookiecutter.entity_id_type}} _value ) { id = _value; }
{%- endif %}

	//
	// Transient attributes
	//

	/**
	 *
	 */
{%- if cookiecutter.entity_id_type == "String" %}
	@Transient
	private boolean newEntity = false;
{%- endif %}
	@Override
	public boolean isNew()
	{
		logger().trace(
			String.format(
				"isNew(): [%s][value:%b]"
				, (getId() != null)
					? getId().toString()
					: ""
{%- if cookiecutter.entity_id_type == "Long" %}
				, getId() == 0
{%- endif %}
{%- if cookiecutter.entity_id_type == "String" %}
				, newEntity
{%- endif %}
			)
		);

{%- if cookiecutter.entity_id_type == "Long" %}
		return getId() == 0L;
{%- endif %}
{%- if cookiecutter.entity_id_type == "String" %}
		return newEntity;
{%- endif %}
	}
{%- if cookiecutter.entity_id_type == "String" %}
	public void setNewEntity(
		final boolean _value
	)
	{
		newEntity = _value;

		logger().trace(
			String.format(
				"setNewEntity(...): [%s][value:%b]"
				, (getId() != null)
					? getId().toString()
					: ""
				, newEntity
			)
		);
	}
{%- endif %}
}
