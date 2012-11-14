package wms.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue(value = "return")
public class Return extends Invoice {
    private static final long serialVersionUID = - 651809809879219226L;

    public Return() {
        super();
    }

}
