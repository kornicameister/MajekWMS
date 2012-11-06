package wms.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "clientType", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@AttributeOverrides(value = {
		@AttributeOverride(name = "id", column = @Column(name = "idClientType", updatable = false, insertable = true, nullable = false)),
		@AttributeOverride(name = "name", column = @Column(name = "name", insertable = true, updatable = false, nullable = false, length = 10, unique = true)) })
public class ClientType extends NamedPersistenceObject {
	private static final long serialVersionUID = 6029469623269647910L;

	public ClientType() {
		super();
	}

}
