package wms.model.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "warehouseType", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"name", "abbreviation" }) })
public class WarehouseType extends AbstractEntity {
	private static final long serialVersionUID = -7479798313966564213L;

	@Column(name = "abbreviation", length = 6, nullable = false, unique = true, insertable = true, updatable = true)
	private String abbreviation;

	@Column(name = "description", length = 120, insertable = true, updatable = true)
	private String description;

	@Column(name = "name", length = 20, nullable = false, unique = true, insertable = true, updatable = true)
	private String name;

	public WarehouseType() {
		super();
	}

	public WarehouseType(String name, String abbreviation, String description) {
		super();
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
		WarehouseType that = (WarehouseType) o;

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
