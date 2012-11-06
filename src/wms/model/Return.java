package wms.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "invoice")
@DiscriminatorValue(value = "3")
public class Return extends Invoice {
	private static final long serialVersionUID = -651809809879219226L;

	public Return() {
		super();
	}

}
