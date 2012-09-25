package wms.model.gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import wms.model.hibernate.Warehouse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;

public class GInitParameters {
	@Since(0.1)
	private HashSet<Warehouse> warehouses;

	@Since(0.1)
	@SerializedName("_dc")
	private String getID;

	public GInitParameters() {
		super();
		this.warehouses = new HashSet<>(0);
	}

	public final HashSet<Warehouse> getWarehouses() {
		return warehouses;
	}

	public final String getGetID() {
		return getID;
	}

	public void setWarehouses(final HashSet<Warehouse> warehouses) {
		this.warehouses = warehouses;
	}

	public void setWarehouses(List<Warehouse> readWarehouse) {
		HashSet<Warehouse> tmp = new HashSet<>(
				((ArrayList<Warehouse>) readWarehouse));
		if (tmp != null) {
			this.warehouses = tmp;
		}
	}

	public void setGETId(String string) {
		this.getID = string;
	}

	public String toGsonString() {
		return new Gson().toJson(this, GInitParameters.class);
	}
}
