package wms.model.client;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "client")
@DiscriminatorValue(value = "2")
public class Recipient extends Client {
	private static final long serialVersionUID = -6775441124171801207L;

	public Recipient() {
		super();
	}

}
