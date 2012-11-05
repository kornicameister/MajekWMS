package wms.model.basic;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import wms.controller.base.annotations.HideField;

@MappedSuperclass
public class BasicPersistanceObject implements Serializable {
	private static final long serialVersionUID = -2635091318105029639L;

	@Version
	@Column(name = "version")
	@HideField
	private Integer version = 0;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedOn")
	@HideField
	private Date updatedOn;

	public BasicPersistanceObject() {
		super();
		this.updatedOn = new Date();
	}

	public synchronized final Integer getVersion() {
		return version;
	}

	public synchronized final void setVersion(Integer version) {
		this.version = version;
	}

	public synchronized final Date getUpdatedOn() {
		return updatedOn;
	}

	public synchronized final void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Override
	public String toString() {
		return "BasicObject [version=" + version + ", updatedOn=" + updatedOn
				+ "]";
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
		if (!(obj instanceof BasicPersistanceObject))
			return false;
		BasicPersistanceObject other = (BasicPersistanceObject) obj;
		if (updatedOn == null) {
			if (other.updatedOn != null)
				return false;
		} else if (!updatedOn.equals(other.updatedOn))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

}
