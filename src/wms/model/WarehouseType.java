package wms.model;

import java.io.Serializable;

public class WarehouseType implements Serializable {
	private static final long serialVersionUID = -7479798313966564213L;

	private Long id;
	private String abbreviation, description;

	public WarehouseType() {
		super(); // hibernate
	}

	public WarehouseType(Long id, String abbreviation, String description) {
		super();
		this.id = id;
		this.abbreviation = abbreviation;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getDescription() {
		return description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
