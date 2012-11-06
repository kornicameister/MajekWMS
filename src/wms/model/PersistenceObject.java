package wms.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

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
abstract public class PersistenceObject extends BasicPersistanceObject {
	@Transient
	private static final long serialVersionUID = 8641451013192983600L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	@Expose
	private Long id;

	public PersistenceObject() {
		super();
	}

	public synchronized Long getId() {
		return id;
	}

	public synchronized final void setId(Long id) {
		this.id = id;
	}

}
