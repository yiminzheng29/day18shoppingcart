package vttp2022.ssf.day18.models;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Cart {
	private String name;
	private List<Item> contents = new LinkedList<>();

	public Cart(String name) { }

	public String getName() { return name; }

	public void add(Item item) { 
		String n = item.getName().toLowerCase();
		List<Item> sameItem = contents.stream()
			.filter(v -> item.getName().toLowerCase().equals(n))
			.limit(1)
			.toList();
		if (sameItem.size() > 0) {
			Item i = sameItem.get(0);
			i.setQuantity(i.getQuantity() + item.getQuantity());
		} else
			contents.add(item);

	}
	public List<Item> getContents() { return contents; }
	public void setContents(List<Item> contents) {  this.contents = contents; }

	public JsonObject toJson() {
		JsonArrayBuilder arr = Json.createArrayBuilder();
		contents.stream()
			.map(i -> i.toJson())
			.forEach(i -> arr.add(i));
		return Json.createObjectBuilder()
			.add("name", name)
			.add("contents", arr)
			.build();
	}

	public static Cart create(String jsonString) {
		Reader reader = new StringReader(jsonString);
		JsonReader jr = Json.createReader(reader);
		JsonObject c = jr.readObject();
		Cart cart = new Cart(c.getString("name"));
		List<Item> contents = c.getJsonArray("contents").stream()
			.map(v -> (JsonObject)v)
			//.map(v -> Item.create(v))
			.map(Item::create)
			.toList();
		cart.setContents(contents);
		return cart;
	}
}
