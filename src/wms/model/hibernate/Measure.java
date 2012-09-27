package wms.model.hibernate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "measure", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Measure extends BaseEntity {
	@Transient
	private static final long serialVersionUID = 8140273816811139591L;

	@Basic
	@Column(nullable = false, length = 10)
	private String abbreviation;

	@Basic
	@Column(name = "name", nullable = false, unique = true, length = 30)
	private String name;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Product measuredProduct;

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "product"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idProduct", unique = true, nullable = false)
	private Integer productId;

	public Measure() {
		super();
	}

	public Measure(String abbreviation, String name) {
		super();
		this.abbreviation = abbreviation;
		this.name = name;
	}

	public Measure(String abbreviation, String name, Product measuredProduct,
			Integer productId) {
		super();
		this.abbreviation = abbreviation;
		this.name = name;
		this.measuredProduct = measuredProduct;
		this.productId = productId;
	}

	public final String getAbbreviation() {
		return abbreviation;
	}

	public final String getName() {
		return name;
	}

	public final Product getMeasuredProduct() {
		return measuredProduct;
	}

	public final Integer getProductId() {
		return productId;
	}

	public final void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setMeasuredProduct(Product measuredProduct) {
		this.measuredProduct = measuredProduct;
	}

	public final void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((abbreviation == null) ? 0 : abbreviation.hashCode());
		result = prime * result
				+ ((measuredProduct == null) ? 0 : measuredProduct.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((productId == null) ? 0 : productId.hashCode());
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
		if (measuredProduct == null) {
			if (other.measuredProduct != null)
				return false;
		} else if (!measuredProduct.equals(other.measuredProduct))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Measure [");
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
		if (getMeasuredProduct() != null) {
			builder.append("getMeasuredProduct()=");
			builder.append(getMeasuredProduct());
			builder.append(", ");
		}
		if (getProductId() != null) {
			builder.append("getProductId()=");
			builder.append(getProductId());
			builder.append(", ");
		}
		builder.append("getVersion()=");
		builder.append(getVersion());
		builder.append("]");
		return builder.toString();
	}

}
