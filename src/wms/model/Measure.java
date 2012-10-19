package wms.model;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "measure", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Measure extends BaseEntity {
	@Transient
	private static final long serialVersionUID = 8140273816811139591L;

	@Id
	@Column(name = "idMeasure", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	protected Long id;

	@Basic
	@Column(nullable = false, length = 10)
	private String abbreviation;

	@Basic
	@Column(name = "name", nullable = false, unique = true, length = 30)
	private String name;

	public Measure() {
		super();
	}

	public Measure(String abbreviation, String name) {
		super();
		this.abbreviation = abbreviation;
		this.name = name;
	}

	public Measure(Long idMeasure, String abbreviation, String name,
			Set<Product> products) {
		super();
		this.id = idMeasure;
		this.abbreviation = abbreviation;
		this.name = name;
	}

	public final Long getIdMeasure() {
		return id;
	}

	public final void setIdMeasure(Long idMeasure) {
		this.id = idMeasure;
	}

	public final String getAbbreviation() {
		return abbreviation;
	}

	public final void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Measure [hashCode()=");
		builder.append(hashCode());
		builder.append(", ");
		if (getIdMeasure() != null) {
			builder.append("getIdMeasure()=");
			builder.append(getIdMeasure());
			builder.append(", ");
		}
		if (getAbbreviation() != null) {
			builder.append("getAbbreviation()=");
			builder.append(getAbbreviation());
			builder.append(", ");
		}
		if (getName() != null) {
			builder.append("getName()=");
			builder.append(getName());
			builder.append(", ");
		}
		if (getUpdatedOn() != null) {
			builder.append("getUpdatedOn()=");
			builder.append(getUpdatedOn());
			builder.append(", ");
		}
		builder.append("getVersion()=");
		builder.append(getVersion());
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((abbreviation == null) ? 0 : abbreviation.hashCode());
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Measure))
			return false;
		Measure other = (Measure) obj;
		if (abbreviation == null) {
			if (other.abbreviation != null)
				return false;
		} else if (!abbreviation.equals(other.abbreviation))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
