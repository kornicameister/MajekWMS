package wms.model.invoice.type;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import wms.model.invoice.Invoice;

@Entity
@Table(name = "invoice")
@DiscriminatorValue(value = "1")
public class Supply extends Invoice {
	private static final long serialVersionUID = 5011897484071966063L;

	public Supply() {
		super();
	}

}
