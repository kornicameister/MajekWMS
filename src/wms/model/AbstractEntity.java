package wms.model;

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

/**
 * This is a base class for all entities defined in MajekWMS that are known to
 * share same properties.
 * 
 * @author kornicameister
 * @created 20-09-2012
 * @file AbstractEntity.java for project MajekWMS
 * @type AbstractEntity
 * 
 */

@MappedSuperclass
public class AbstractEntity implements Serializable {
	@Transient
	private static final long serialVersionUID = 8641451013192983600L;

	@Id
	@Column(name = "idNumber")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "increment", strategy = "increment")
	protected Integer id;

	@Version
	@Column(name = "version")
	private Integer version = 0;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedOn")
	private Date updatedOn;

	public AbstractEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
}
