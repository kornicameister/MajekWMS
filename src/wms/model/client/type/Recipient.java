package wms.model.client.type;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import wms.model.client.Client;

@Entity
@Table(name = "client")
@DiscriminatorValue(value = "2")
public class Recipient extends Client {
	private static final long serialVersionUID = -6775441124171801207L;

	public Recipient() {
		super();
	}

	@Override
	public String toString() {
		return "Recipient [toString()=" + super.toString() + "]";
	}

}
