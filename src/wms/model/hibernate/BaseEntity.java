package wms.model.hibernate;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * This is a base class for all entities defined in MajekWMS that are known to
 * share same properties.
 * 
 * @author kornicameister
 * @created 20-09-2012
 * @file BaseEntity.java for project MajekWMS
 * @type BaseEntity
 * 
 */

@MappedSuperclass
abstract public class BaseEntity implements Serializable {
	@Transient
	private static final long serialVersionUID = 8641451013192983600L;

	@Version
	@Column(name = "version")
	private Integer version = 0;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedOn")
	private Date updatedOn;

	public BaseEntity() {
		super();
		this.updatedOn = new Date();
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public int getVersion() {
		return this.version;
	}

	@SuppressWarnings("unused")
	private void setVersion(final Integer version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((updatedOn == null) ? 0 : updatedOn.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BaseEntity))
			return false;

		return true;
	}

}