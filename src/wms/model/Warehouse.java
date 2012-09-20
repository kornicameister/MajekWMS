package wms.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "warehouse", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Warehouse extends AbstractEntity {
	@Transient
	private static final long serialVersionUID = 4557522901223374020L;

	@Basic
	@Column(name = "name", nullable = false, unique = true, length = 20, updatable = true)
	private String name;

	@Column(name = "description", nullable = true, length = 666)
	private String description;

	@Basic
	@Column(name = "size", nullable = false)
	private Integer size;

	@Basic
	@Column(name = "maxSize", nullable = false)
	private Integer maximumSize;

	@Basic
	@Column(name = "createdDate", nullable = false)
	private Date createdDate;

	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "type", referencedColumnName = "idNumber")
	private WarehouseType warehouseType;

	@Column(name = "type", unique = true, updatable = true, insertable = true, nullable = false)
	private Integer type;

	public Warehouse() {
		super(); // for hibernate
	}

	public Warehouse(String name, String description, Integer size,
			Integer maxSize, WarehouseType type, Date createdDate) {
		super();
		this.name = name;
		this.description = description;
		this.size = size;
		this.maximumSize = maxSize;
		this.setType(warehouseType);
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

	public Integer getType() {
		return warehouseType.getId();
	}

	public WarehouseType getWarehouseType() {
		return this.warehouseType;
	}

	@Column(nullable = false)
	public Date getCreatedDate() {
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

	public void setType(WarehouseType type) {
		this.type = type.getId();
		this.warehouseType = type;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Basic(fetch = FetchType.LAZY)
	public final Double getRatio() {
		return this.size / (double) this.maximumSize;
	}

	public String toString() {
		return String.format("%s: [%s -> %s]", this.id, this.name, this.size);
	}
}
