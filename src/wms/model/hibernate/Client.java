package wms.model.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "client", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Client extends AbstractEntity {
	@Transient
	private static final long serialVersionUID = 1283426340575080285L;

	@Basic
	@Column(name = "name", nullable = false, unique = true, length = 45, updatable = true)
	private String name;

	@Basic
	@Column(name = "company", nullable = false, unique = true, length = 45, updatable = true)
	private String company;

	@Basic
	@Column(name = "description", nullable = true, length = 200)
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
	private Set<Invoice> invoices = new HashSet<>(0);

	public Client() {
		super(); // hibernate
	}

	public Client(String name, String company, String description) {
		super();
		this.name = name;
		this.company = company;
		this.description = description;
	}
	
	public Client(String name, String company, String description, Set<Invoice> invoices) {
		super();
		this.name = name;
		this.company = company;
		this.description = description;
		this.invoices = invoices;
	}

	public String getName() {
		return name;
	}

	public String getCompany() {
		return company;
	}

	public Set<Invoice> getInvoices() {
		return invoices;
	}

	public String getDescription() {
		return description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setInvoices(Set<Invoice> invoices) {
		this.invoices = invoices;
	}

}
