package org.kornicameister.wms.model.hibernate;

import javax.persistence.*;


@Entity
@Table(name = "invoiceType",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"type"})})
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "idInvoiceType", updatable = false, insertable = true, nullable = false)),
        @AttributeOverride(name = "name", column = @Column(name = "type", insertable = true, updatable = false, nullable = false, length = 10, unique = true))})
public class InvoiceType extends NamedPersistenceObject {
    @Transient
    private static final long serialVersionUID = -7345851338532573657L;

    public InvoiceType() {
        super();
    }
}
