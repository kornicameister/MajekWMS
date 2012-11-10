package wms.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "client")
@DiscriminatorValue(value = "supplier")
public class Supplier extends Client {
	private static final long serialVersionUID = -3487771558214472341L;

	public Supplier() {
		super();
	}

	@Override
	public String toString() {
		return "Supplier [toString()=" + super.toString() + "]";
	}

}
