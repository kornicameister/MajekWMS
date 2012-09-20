package wms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "warehouse")
public class Warehouse extends AbstractEntity {
	@Transient
	private static final long serialVersionUID = 4557522901223374020L;

	@Column(name = "name", nullable = false, unique = true, length = 20, updatable = true)
	private String name;

	@Column(name = "description", nullable = true, length = 666)
	private String description;

	@Column(name = "size", nullable = false)
	private Integer size;

	@Column(name = "maxSize", nullable = false)
	private Integer maximumSize;

	@Column(name = "type", nullable = false)
	private Integer type;

	@Column(name = "createdDate", nullable = false)
	private Long createdDate;

	public Warehouse() {
		super(); // for hibernate
	}

	public Warehouse(String name, String description, Integer size,
			Integer maxSize, Integer type, Long createdDate, Long lastUpdated) {
		super();
		this.name = name;
		this.description = description;
		this.size = size;
		this.maximumSize = maxSize;
		this.type = type;
		this.createdDate = createdDate;
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
		return maximumSize;
	}

	@Column(nullable = false)
	public Integer getType() {
		return type;
	}

	@Column(nullable = false)
	public Long getCreatedDate() {
		return createdDate;
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
		this.maximumSize = maxSize;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}

	public final Double getRatio() {
		return this.size / (double) this.maximumSize;
	}

	public String toString() {
		return String.format("%s: [%s -> %s]", this.id, this.name, this.size);
	}
}
