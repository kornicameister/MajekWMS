package wms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "unit", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Unit extends AbstractStorageUnit {
	@Transient
	private static final long serialVersionUID = 2437063899438647082L;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
			name = "unitProduct",
			schema = "majekwms", 
			joinColumns = { 
					@JoinColumn(name = "idUnit", nullable = false, updatable = false) }, 
						inverseJoinColumns = { @JoinColumn(name = "idProduct", nullable = false, updatable = false) 
					}
			)
	private Set<Product> products = new HashSet<>();

	public Unit() {
		super(); // hibernate
	}

	public Unit(Set<Product> products) {
		super();
		this.products = products;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public Integer getQuantityOfProduct(Integer productId) {
		return -1;
	}

	public Integer getQuantityOfProduct(Product product) {
		return -1;
	}

	public void setProducts(HashSet<Product> products) {
		this.products = products;
		this.setSize(this.products.size());
	}

	public void addProduct(Product e) {
		this.products.add(e);
		this.setSize(this.products.size());
	}

	@Override
	public int hashCode() {
		return super.hashCode() * this.products.hashCode();
	}
}
