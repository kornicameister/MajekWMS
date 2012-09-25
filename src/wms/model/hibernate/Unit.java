package wms.model.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "unit", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "name" }),
		@UniqueConstraint(columnNames = { "idNumber" }) })
public class Unit extends AbstractStorageUnit {
	@Transient
	private static final long serialVersionUID = 2437063899438647082L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idWarehouse", nullable = false, updatable = true)
	private Warehouse warehouse;

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "unit"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idUnitType", unique = true, nullable = false)
	private Integer unitTypeId;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private UnitType unitType;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
			name = "unitProduct", 
			schema = "majekwms", 
			joinColumns = { @JoinColumn(name = "idUnit", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "idProduct", nullable = false, updatable = false) })
	private Set<Product> products = new HashSet<>();

	public Unit() {
		super(); // hibernate
	}

	public Unit(UnitType unitType, Set<Product> products) {
		super();
		this.unitType = unitType;
		this.products = products;
	}

	public Unit(Warehouse warehouse, UnitType unitType, Set<Product> products) {
		super();
		this.warehouse = warehouse;
		this.unitType = unitType;
		this.products = products;
	}
	
	public final Warehouse getWarehouse() {
		return warehouse;
	}

	public final void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
	public final void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(HashSet<Product> products) {
		this.products = products;
		this.setSize(this.products.size());
	}

	public UnitType getUnitType() {
		return unitType;
	}

	public void setUnitType(UnitType unitType) {
		this.unitType = unitType;
	}

	public Integer getUnitTypeId() {
		return this.unitTypeId;
	}

	public void setUnitTypeId(Integer id) {
		this.unitTypeId = id;
	}

	@Override
	public int hashCode() {
		return super.hashCode() * this.products.hashCode();
	}
}
