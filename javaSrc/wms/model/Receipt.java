package wms.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue(value = "receipt")
public class Receipt extends Invoice {
    private static final long serialVersionUID = 5011897484071966063L;

    public Receipt() {
        super();
    }

}
