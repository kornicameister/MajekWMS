package wms.model.gson;

import java.util.HashSet;

import wms.model.hibernate.UnitType;
import wms.model.hibernate.Warehouse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class GInitParameters {
	private Configuration configuration;

	public GInitParameters() {
		super();
		this.configuration = new Configuration();
	}

	public GInitParameters(Configuration cfg) {
		super();
		this.configuration = cfg;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public String toGsonString() {
		return new Gson().toJson(this, GInitParameters.class);
	}

	public class Configuration {
		HashSet<Warehouse> warehouses;
		HashSet<UnitType> unitTypes;

		@SerializedName("_dc")
		String getID;

		public Configuration() {
			super();
		}

		public Configuration(HashSet<Warehouse> warehouses,
				HashSet<UnitType> unitTypes, String getID) {
			super();
			this.warehouses = warehouses;
			this.unitTypes = unitTypes;
			this.getID = getID;
		}

		public final HashSet<Warehouse> getWarehouses() {
			return warehouses;
		}

		public final HashSet<UnitType> getUnitTypes() {
			return unitTypes;
		}

		public final String getGetID() {
			return getID;
		}

		public final void setWarehouses(HashSet<Warehouse> warehouses) {
			this.warehouses = warehouses;
		}

		public final void setUnitTypes(HashSet<UnitType> unitTypes) {
			this.unitTypes = unitTypes;
		}

		public final void setGetID(String getID) {
			this.getID = getID;
		}

	}
}
