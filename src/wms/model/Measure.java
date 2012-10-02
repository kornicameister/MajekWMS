package wms.model;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	protected Integer idMeasure;

	@Basic
	@Column(nullable = false, length = 10)
	private String abbreviation;

	@Basic
	@Column(name = "name", nullable = false, unique = true, length = 30)
	private String name;

	@OneToMany(mappedBy = "productMeasure", fetch = FetchType.LAZY)
	private Set<Product> products;

	public Measure() {
		super();
	}

	public Measure(String abbreviation, String name) {
		super();
		this.abbreviation = abbreviation;
		this.name = name;
	}

	public Measure(Integer idMeasure, String abbreviation, String name,
			Set<Product> products) {
		super();
		this.idMeasure = idMeasure;
		this.abbreviation = abbreviation;
		this.name = name;
		this.products = products;
	}

	public final Integer getIdMeasure() {
		return idMeasure;
	}

	public final void setIdMeasure(Integer idMeasure) {
		this.idMeasure = idMeasure;
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

	public final Set<Product> getProducts() {
		return products;
	}

	public final void setProducts(Set<Product> products) {
		this.products = products;
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
		if (getProducts() != null) {
			builder.append("getProducts()=");
			builder.append(getProducts());
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
				+ ((idMeasure == null) ? 0 : idMeasure.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((products == null) ? 0 : products.hashCode());
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
		if (idMeasure == null) {
			if (other.idMeasure != null)
				return false;
		} else if (!idMeasure.equals(other.idMeasure))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		return true;
	}

}
