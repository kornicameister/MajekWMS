package wms.model.hibernate;

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
	protected Integer idMeasure;

	@Basic
	@Column(nullable = false, length = 10)
	private String abbreviation;

	@Basic
	@Column(name = "name", nullable = false, unique = true, length = 30)
	private String name;

	private Product product;

	public Measure() {
		super();
	}

	public Measure(Integer idMeasure, Product product, String abbreviation,
			String name) {
		super();
		this.idMeasure = idMeasure;
		this.product = product;
		this.abbreviation = abbreviation;
		this.name = name;
	}

	public final Integer getIdMeasure() {
		return idMeasure;
	}

	public final Product getProduct() {
		return product;
	}

	public final String getAbbreviation() {
		return abbreviation;
	}

	public final String getName() {
		return name;
	}

	public final void setIdMeasure(Integer idMeasure) {
		this.idMeasure = idMeasure;
	}

	public final void setProduct(Product product) {
		this.product = product;
	}

	public final void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public final void setName(String name) {
		this.name = name;
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
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Measure [");
		if (idMeasure != null) {
			builder.append("idMeasure=");
			builder.append(idMeasure);
			builder.append(", ");
		}
		if (product != null) {
			builder.append("product=");
			builder.append(product);
			builder.append(", ");
		}
		if (abbreviation != null) {
			builder.append("abbreviation=");
			builder.append(abbreviation);
			builder.append(", ");
		}
		if (name != null) {
			builder.append("name=");
			builder.append(name);
		}
		builder.append("]");
		return builder.toString();
	}

}
