package wms.model.basic;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.Expose;

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
abstract public class PersistenceObject implements Serializable {
	@Transient
	private static final long serialVersionUID = 8641451013192983600L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	@Expose
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version = 0;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedOn")
	private Date updatedOn;

	public PersistenceObject() {
		super();
		this.updatedOn = new Date();
	}

	public synchronized Long getId() {
		return id;
	}

	public synchronized final void setId(Long id) {
		this.id = id;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (!(obj instanceof PersistenceObject))
			return false;
		PersistenceObject other = (PersistenceObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
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

	@Override
	public String toString() {
		return "BaseEntity [id=" + id + ", version=" + version + ", updatedOn="
				+ updatedOn + "]";
	}

}
