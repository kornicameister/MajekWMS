package wms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "measure")
public class Measure {
	private Long id;
	private String abbreviation;
	private String name;

	public Measure() {
		// for Hibernate
	}

	public Measure(String name, String abbr) {
		this.name = name;
		this.abbreviation = abbr;
	}

	@Id
	@Column(name = "idMeasure")
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public Long getId() {
		return id;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("measure: [%s, %s, %s]", this.id, this.name,
				this.abbreviation);
	}
}
