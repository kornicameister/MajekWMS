package wms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "clientType", uniqueConstraints = { @UniqueConstraint(columnNames = { "type" }) })
public class ClientType extends BasicPersistanceObject {
	private static final long serialVersionUID = 6029469623269647910L;

	@Id
	@Column(name = "type", insertable = false, updatable = false, nullable = false, length = 15, unique = true)
	private String type;

	public ClientType() {
		super();
	}

}
