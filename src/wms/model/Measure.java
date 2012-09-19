package wms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "measure")
public class Measure implements Serializable {
	private static final long serialVersionUID = 8140273816811139591L;
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

	@Column(nullable = false, length = 10)
	public String getAbbreviation() {
		return abbreviation;
	}

	@Column(nullable = false, length = 20, unique = true)
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
		return String.format("%s: [%s, %s, %s]", this.getClass().getName(),
				this.id, this.name, this.abbreviation);
	}
}
