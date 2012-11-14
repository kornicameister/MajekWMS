package wms.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "supply")
public class Supply extends Invoice {
    private static final long serialVersionUID = 5011897484071966063L;

    public Supply() {
        super();
    }

}
