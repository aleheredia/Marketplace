package br.com.heredia.marketplace.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.heredia.marketplace.entity.Product;
import br.com.heredia.marketplace.repository.ProductRepository;

@Controller
@RequestMapping("/")
public class MarketplaceController {

	@Autowired
	private ProductRepository productRepository;
	
	private List<Product> cart = new ArrayList<Product>();
	
	Double totalTaxes = 0.00;
    Double totalPrice = 0.00;
	
	@RequestMapping(value = "/addToCart/{id}", method = RequestMethod.GET)
    public String addToCart(@PathVariable String id) {
		Product product = productRepository.findById(Long.parseLong(id)).get();
		
		Double taxes = 0.00;
  	  
	  	if(product.getImported()) {
	  		taxes += product.getPrice()*0.05;
	  	}
	  	if(product.getType().equals("music") || product.getType().equals("beauty")) {
	  		taxes += product.getPrice()*0.1;
	  	}
	  	
	  	totalTaxes += taxes;
	  	totalPrice += product.getPrice()+taxes;
	  	product.setPrice(product.getPrice()+taxes);
	  	
	  	cart.add(product);
		return "redirect:/";
    }
	
	@RequestMapping(value = "/products", method = RequestMethod.GET)
    public String listRecordedProducts(Model model) {
          List<Product> listProducts = productRepository.findAll();
          if (listProducts != null) {
                model.addAttribute("products", listProducts);
          }
          return "products";
    }
	
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
    public String clearCart() {
		this.cart = new ArrayList<Product>();
		totalTaxes = 0.00;
	    totalPrice = 0.00;
		return "redirect:/";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String listProducts(Model model) {
          List<Product> listProducts = productRepository.findAll();
          if (listProducts != null) {
                model.addAttribute("products", listProducts);
          }
          
          model.addAttribute("totalTaxes", totalTaxes);
          model.addAttribute("totalPrice", totalPrice);
          model.addAttribute("cart", cart);
          return "market";
    }
	
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public String addProduct(Product product) {
    	if(product.getImported()==null) product.setImported(false);
    	productRepository.save(product);
          return "redirect:/products";
    }
}
