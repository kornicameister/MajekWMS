package wms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
			name = "measure", 
			uniqueConstraints = {
					@UniqueConstraint(columnNames = {"name"})
			})
public class Measure extends AbstractEntity {
	@Transient
	private static final long serialVersionUID = 8140273816811139591L;

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
