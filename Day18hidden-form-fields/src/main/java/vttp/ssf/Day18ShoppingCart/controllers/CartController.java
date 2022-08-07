package vttp.ssf.Day18ShoppingCart.controllers;


import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.ssf.Day18ShoppingCart.services.CartService;

@Controller
@RequestMapping(path="/cart")
public class CartController {

    @Autowired
    private CartService cartSvc;

    @PostMapping
    public String addToCart(@RequestBody MultiValueMap<String, String> form, Model model) {
        String name = form.getFirst("name");
        if ((name==null)||name.trim().length()<=0) {
            name="anonymous";
        }

        String c = form.getFirst("contents");
        List<String> cart = new LinkedList<>();
        if (!isNull(c)) {
            cart=cartSvc.deserialize(c);
        }

        String item = form.getFirst("item");
        if (!isNull(item)) {
            cart.add(item);
        }

        model.addAttribute("displayName", name.toUpperCase());
        model.addAttribute("contents", cartSvc.serialize(cart));
        model.addAttribute("cart", cart);
        model.addAttribute("empty", cart.isEmpty());
        model.addAttribute("name", name);

        return "cart";
    }

    private boolean isNull(String s) {
        return ((s==null) || s.trim().length()<=0);
    }
}
