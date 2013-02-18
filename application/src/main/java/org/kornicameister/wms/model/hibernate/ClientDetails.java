package org.kornicameister.wms.model.hibernate;

import com.google.gson.annotations.SerializedName;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.kornicameister.wms.annotations.HideAssociation;

import javax.persistence.*;

@Entity
@Table(name = "clientDetails")
public class ClientDetails extends BasicPersistentObject {
    @Transient
    private static final long serialVersionUID = -4305913399009774547L;

    @Id
    @Column(name = "idClient", updatable = false, insertable = true, nullable = false)
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "client"))
    @SerializedName(value = "id")
    private Long clientId;

    @Basic
    @Column(name = "phone", length = 15, insertable = true, updatable = true, nullable = false)
    private String phone;

    @Basic
    @Column(name = "account", length = 33, insertable = true, updatable = true, nullable = false)
    private String account;

    @Basic
    @Column(name = "nip", length = 15, insertable = true, updatable = true, nullable = false)
    private String nip;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @HideAssociation
    private Client client;

    public ClientDetails() {
        super();
    }

    public synchronized final Long getId() {
        return clientId;
    }

    public synchronized final void setId(Long clientId) {
        this.clientId = clientId;
    }

    public synchronized final String getPhone() {
        return phone;
    }

    public synchronized final void setPhone(String phone) {
        this.phone = phone;
    }

    public synchronized final String getAccount() {
        return account;
    }

    public synchronized final void setAccount(String account) {
        this.account = account;
    }

    public synchronized final String getNip() {
        return nip;
    }

    public synchronized final void setNip(String nip) {
        this.nip = nip;
    }

    public synchronized final Client getClient() {
        return client;
    }

    public synchronized final void setClient(Client client) {
        this.client = client;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((account == null) ? 0 : account.hashCode());
        result = prime * result + ((nip == null) ? 0 : nip.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof ClientDetails))
            return false;
        ClientDetails other = (ClientDetails) obj;
        if (account == null) {
            if (other.account != null)
                return false;
        } else if (!account.equals(other.account))
            return false;
        if (nip == null) {
            if (other.nip != null)
                return false;
        } else if (!nip.equals(other.nip))
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ClientDetails");
        sb.append("{clientId=").append(clientId);
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", account='").append(account).append('\'');
        sb.append(", nip='").append(nip).append('\'');
        sb.append(", client=").append(client);
        sb.append('}');
        return sb.toString();
    }
}
