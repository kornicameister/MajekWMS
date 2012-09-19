package wms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "warehouse")
public class Warehouse implements Serializable {
	private static final long serialVersionUID = 4557522901223374020L;
	private Long id;
	private String name, description;
	private Integer size, maxSize, type;
	private Long createdDate, lastUpdated;

	public Warehouse() {
		super(); // for hibernate
	}

	public Warehouse(String name, String description, Integer size,
			Integer maxSize, Integer type, Long createdDate, Long lastUpdated) {
		super();
		this.name = name;
		this.description = description;
		this.size = size;
		this.maxSize = maxSize;
		this.type = type;
		this.createdDate = createdDate;
		this.lastUpdated = lastUpdated;
	}

	@Id
	@Column(name = "idWarehouse")
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(unique = true, length = 20, nullable = false)
	public String getName() {
		return name;
	}

	@Column(length = 666, nullable = false)
	public String getDescription() {
		return description;
	}

	@Column(nullable = false)
	public Integer getSize() {
		return size;
	}

	@Column(nullable = false)
	public Integer getMaxSize() {
		return maxSize;
	}

	@Column(nullable = false)
	public Integer getType() {
		return type;
	}

	@Column(nullable = false)
	public Long getCreatedDate() {
		return createdDate;
	}

	@Column(nullable = false)
	public Long getLastUpdated() {
		return lastUpdated;
	}

	// Setter part
	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}

	public void setLastUpdated(Long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String toString() {
		return String.format("%s: [%s -> %s]", this.id, this.name, this.size);
	}
}
