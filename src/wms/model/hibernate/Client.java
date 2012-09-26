package wms.model.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "client", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@DiscriminatorValue("Client")
@AttributeOverride (name = "idClient", column = @Column(name = "idNumber"))
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
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
			name = "productClient", 
			schema = "majekwms", 
			joinColumns = { @JoinColumn(name = "idProduct", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "idClient", nullable = false, updatable = false) })
	private Set<Product> products = new HashSet<>(0);
	
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

	public Client(String name, String company, String description,
			Set<Product> products, Set<Invoice> invoices) {
		super();
		this.name = name;
		this.company = company;
		this.description = description;
		this.products = products;
		this.invoices = invoices;
	}

	public final String getName() {
		return name;
	}

	public final String getCompany() {
		return company;
	}

	public final String getDescription() {
		return description;
	}

	public final Set<Product> getProducts() {
		return products;
	}

	public final Set<Invoice> getInvoices() {
		return invoices;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setCompany(String company) {
		this.company = company;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final void setProducts(Set<Product> products) {
		this.products = products;
	}

	public final void setInvoices(Set<Invoice> invoices) {
		this.invoices = invoices;
	}

}
