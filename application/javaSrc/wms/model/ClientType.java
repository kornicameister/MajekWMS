package wms.model;

import javax.persistence.*;

@Entity
@Table(name = "clientType",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"type"})})
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "idClientType", updatable = false, insertable = true, nullable = false)),
        @AttributeOverride(name = "name", column = @Column(name = "type", insertable = true, updatable = false, nullable = false, length = 15))})
public class ClientType extends NamedPersistenceObject {
    @Transient
    private static final long serialVersionUID = 6029469623269647910L;

    public ClientType() {
        super();
    }
}
