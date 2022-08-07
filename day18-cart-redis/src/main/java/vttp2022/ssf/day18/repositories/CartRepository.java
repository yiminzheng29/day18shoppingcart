package vttp2022.ssf.day18.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.ssf.day18.models.Cart;
import vttp2022.ssf.day18.models.Item;

@Repository
public class CartRepository {

	@Autowired @Qualifier("redislab")
	private RedisTemplate<String, String> template;

	public Optional<Cart> get(String name) {

		if (!template.hasKey(name))
			return Optional.empty();

		List<Item> contents = new LinkedList<>();
		ListOperations<String, String> listOps = template.opsForList();
		long size = listOps.size(name);
		for (long i = 0; i < size; i++) {
			String str = listOps.index(name, i);
			contents.add(Item.create(str));
		}

		Cart cart = new Cart(name);
		cart.setContents(contents);
		return Optional.of(cart);
	}

	public void save(Cart cart) {
		String name = cart.getName();
		List<Item> contents = cart.getContents();
		ListOperations<String, String> listOps = template.opsForList();
		long l = listOps.size(name);
		if (l > 0)
			listOps.trim(name, 0, l);
		listOps.leftPushAll(name, 
				contents.stream()
					.map(v -> v.toJson().toString()).toList()
		);
	}
}
