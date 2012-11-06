package wms.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "invoice")
@DiscriminatorValue(value = "2")
public class Receipt extends Invoice {
	private static final long serialVersionUID = 5011897484071966063L;

	public Receipt() {
		super();
	}

}
