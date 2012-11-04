package wms.model.client.type;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import wms.model.client.Client;

@Entity
@Table(name = "client")
@DiscriminatorValue(value = "1")
public class Supplier extends Client {
	private static final long serialVersionUID = -3487771558214472341L;

	public Supplier() {
		super();
	}

}
