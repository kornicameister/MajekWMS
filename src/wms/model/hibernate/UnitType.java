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
@Table(name = "warehouseType", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"name", "abbreviation" }) })
@DiscriminatorValue("UnitType")
@AttributeOverride (name = "idUnitType", column = @Column(name = "idNumber"))
public class UnitType extends AbstractEntity {
	@Transient
	private static final long serialVersionUID = -7479798313966564213L;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "unit", cascade = CascadeType.ALL)
	private Unit unit;
	
	@Column(name = "abbreviation", length = 6, nullable = false, unique = true, insertable = true, updatable = true)
	private String abbreviation;

	@Column(name = "description", length = 120, insertable = true, updatable = true)
	private String description;

	@Column(name = "name", length = 20, nullable = false, unique = true, insertable = true, updatable = true)
	private String name;

	public UnitType() {
		super();
	}

	public UnitType(String name, String abbreviation, String description) {
		super();
		this.name = name;
		this.abbreviation = abbreviation;
		this.description = description;
	}
	
	public UnitType(Unit unit, String name, String abbreviation, String description) {
		super();
		this.unit = unit;
		this.name = name;
		this.abbreviation = abbreviation;
		this.description = description;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}

	public String getDescription() {
		return description;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		boolean result = super.equals(o);
		UnitType that = (UnitType) o;

		if (result) {
			return this.name.equals(that.name)
					&& this.abbreviation.equals(that.abbreviation);
		}

		return true;
	}

	@Override
	public int hashCode() {
		return super.hashCode() * this.name.hashCode()
				* this.abbreviation.hashCode();
	}

}
