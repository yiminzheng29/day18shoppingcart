package vttp2022.ssf.day18.models;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Item {
	private String name;
	private Integer quantity;

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Integer getQuantity() { return quantity; }
	public void setQuantity(Integer quantity) { this.quantity = quantity; }

	public static Item create(String jsonStr) {
		StringReader reader = new StringReader(jsonStr);
		JsonReader r = Json.createReader(reader);
		return create(r.readObject());
	}
	public static Item create(JsonObject jo) {
		Item item = new Item();
		item.setName(jo.getString("name"));
		item.setQuantity(jo.getInt("quantity"));
		return item;
	}

	public JsonObject toJson() {
		return Json.createObjectBuilder()
			.add("name", name)
			.add("quantity", quantity)
			.build();
	}
}
