package wms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "client", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Client extends BaseEntity {
	@Transient
	private static final long serialVersionUID = 1283426340575080285L;

	@Id
	@Column(name = "idClient", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	protected Integer idClient;

	@Basic
	@Column(name = "name", nullable = false, unique = true, length = 45, updatable = true)
	private String name;

	@Basic
	@Column(name = "company", nullable = false, unique = true, length = 45, updatable = true)
	private String company;

	@Basic
	@Column(name = "description", nullable = true, length = 200)
	private String description;

	@OneToMany(mappedBy = "invoiceClient", fetch = FetchType.LAZY)
	private Set<Invoice> invoices = new HashSet<>(0);
	
	@ManyToMany
	@JoinTable(name = "productClient",
			joinColumns = {@JoinColumn(name = "fkClient", referencedColumnName="idClient")},
			inverseJoinColumns = {@JoinColumn(name = "fkProduct", referencedColumnName="idProduct")})
	private Set<Product> clientsProducts = new HashSet<>(0);

	public Client() {
		super(); // hibernate
	}

	public Client(String name, String company, String description) {
		super();
		this.name = name;
		this.company = company;
		this.description = description;
	}

	public Client(String name, String company, String description,
			Set<Invoice> invoices) {
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
		this.clientsProducts = products;
		this.invoices = invoices;
	}

	public Client(Integer idClient, String name, String company,
			String description, Set<Product> products, Set<Invoice> invoices) {
		super();
		this.idClient = idClient;
		this.name = name;
		this.company = company;
		this.description = description;
		this.clientsProducts = products;
		this.invoices = invoices;
	}

	public final Integer getIdClient() {
		return idClient;
	}

	public final void setIdClient(Integer idClient) {
		this.idClient = idClient;
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
		return clientsProducts;
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
		this.clientsProducts = products;
	}

	public final void setInvoices(Set<Invoice> invoices) {
		this.invoices = invoices;
	}

}