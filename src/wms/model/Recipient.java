package wms.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "client")
@DiscriminatorValue(value = "recipient")
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
