package wms.model;

import javax.persistence.*;

@Entity
@Table(name = "clientType",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"type"})})
public class ClientType extends BasicPersistentObject {
    private static final long serialVersionUID = 6029469623269647910L;

    @Id
    @Column(name = "type", insertable = false, updatable = false, nullable = false, length = 15, unique = true)
    private String type = null;

    public ClientType() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (! super.equals(o)) return false;

        ClientType that = (ClientType) o;

        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
