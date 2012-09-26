package wms.model.hibernate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "storageType", discriminatorType = DiscriminatorType.STRING)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((maximumSize == null) ? 0 : maximumSize.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof AbstractStorageUnit))
			return false;
		AbstractStorageUnit other = (AbstractStorageUnit) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (maximumSize == null) {
			if (other.maximumSize != null)
				return false;
		} else if (!maximumSize.equals(other.maximumSize))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		return true;
	}

}
