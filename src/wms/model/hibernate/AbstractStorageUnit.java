package wms.model.hibernate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
abstract public class AbstractStorageUnit extends AbstractEntity {
	@Transient
	private static final long serialVersionUID = -681353835122119325L;

	@Basic
	@Column(name = "name", nullable = false, unique = true, length = 20, updatable = true)
	protected String name;

	@Column(name = "description", nullable = true, length = 666)
	private String description;

	@Basic
	@Column(name = "size", nullable = false)
	private Integer size;

	@Basic
	@Column(name = "maxSize", nullable = false)
	private Integer maximumSize;

	public AbstractStorageUnit() {
		super();
	}

	public AbstractStorageUnit(String name, String description, Integer size,
			Integer maximumSize) {
		super();
		this.name = name;
		this.description = description;
		this.size = size;
		this.maximumSize = maximumSize;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Integer getSize() {
		return size;
	}

	public Integer getMaxSize() {
		return maximumSize;
	}

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

	@Basic(fetch = FetchType.LAZY)
	public final Double getRatio() {
		return this.size / (double) this.maximumSize;
	}

	public String toString() {
		return String.format("%s :: %s: [%s -> %s]", this.getClass().getName(),
				this.id, this.name, this.size);
	}

	@Override
	public boolean equals(Object o) {
		boolean results = super.equals(o);

		if (results) {
			return this.name.equals(((AbstractStorageUnit) o).name);
		}

		return true;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() * this.name.hashCode();
	}
}
