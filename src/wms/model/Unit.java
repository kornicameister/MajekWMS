package wms.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "unit", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Unit extends AbstractStorageUnit {
	@Transient
	private static final long serialVersionUID = 2437063899438647082L;
}
