package vttp.ssf.Day18ShoppingCart.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CartService {
    
    // Takes CSV and convert to List<String>
    public List<String> deserialize(String s) {
        String[] items = s.split(",");
        List<String> cart = new LinkedList<>();

        for (String i: items) {
            cart.add(i);
        }
        return cart;
    }

    public String serialize(List<String> list) {
        return String.join(",", list);
    }
}
