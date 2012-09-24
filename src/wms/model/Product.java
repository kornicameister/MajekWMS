package wms.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "unit", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Product extends AbstractEntity {
	private static final long serialVersionUID = 1246737308278979025L;
	
	/**
	 * This class inherits some fields that only
	 * has wrong mapping set, find the way to change and we are home
	 */
}
