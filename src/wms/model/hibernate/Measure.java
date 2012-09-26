package wms.model.hibernate;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "measure", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@DiscriminatorValue("Measure")
@AttributeOverride (name = "idMeasure", column = @Column(name = "idNumber"))
public class Measure extends AbstractEntity {
	@Transient
	private static final long serialVersionUID = 8140273816811139591L;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private Product product;

	@Column(nullable = false, length = 10)
	private String abbreviation;

	@Column(name = "name", nullable = false, unique = true, length = 30)
	private String name;

	public Measure() {
		super();
	}

	public Measure(String name, String abbr) {
		this.name = name;
		this.abbreviation = abbr;
	}

	public Measure(Product product, String abbreviation, String name) {
		super();
		this.product = product;
		this.abbreviation = abbreviation;
		this.name = name;
	}

	public final Product getProduct() {
		return product;
	}

	public final String getName() {
		return name;
	}

	public final void setProduct(Product product) {
		this.product = product;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	@Override
	public String toString() {
		return String.format("%s: [%s, %s, %s]", this.getClass().getName(),
				this.id, this.name, this.abbreviation);
	}
}
