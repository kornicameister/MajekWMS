package wms.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "warehouse", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Warehouse extends AbstractStorageUnit {
	@Transient
	private static final long serialVersionUID = 4557522901223374020L;

	@Basic
	@Column(name = "createdDate", nullable = false)
	private Date createdDate;

	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "type", referencedColumnName = "idNumber")
	private WarehouseType warehouseType;

	@Column(name = "type", unique = true, updatable = true, insertable = true, nullable = false)
	private Integer idWarehouseType;

	public Warehouse() {
		super(); // for hibernate
	}

	public Warehouse(String name, String description, Integer size,
			Integer maxSize, WarehouseType type, Date createdDate) {
		super(name, description, size, maxSize);
		this.setType(type);
		this.createdDate = createdDate;
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

	public void setType(WarehouseType type) {
		this.idWarehouseType = type.getId();
		this.warehouseType = type;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
