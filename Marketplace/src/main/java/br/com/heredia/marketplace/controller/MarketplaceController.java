package br.com.heredia.marketplace.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
	
	DecimalFormat fmt = new DecimalFormat("0.00");

	@Autowired
	private ProductRepository productRepository;
	
	private List<Product> cart = new ArrayList<Product>();
	
	Double totalTaxes = 0.00D;
    Double totalPrice = 0.00D;
	
	@RequestMapping(value = "/addToCart/{id}", method = RequestMethod.GET)
    public String addToCart(@PathVariable String id) {
		Product product = productRepository.findById(Long.parseLong(id)).get();
		
		Double taxes = 0.00D;
  	  
	  	if(product.getImported()) {
	  		taxes += product.getPrice()*0.05D;
	  	}
	  	if(product.getType().equals("music") || product.getType().equals("beauty")) {
	  		taxes += product.getPrice()*0.1D;
	  	}
	  	
	  	if(taxes>0) {
	  		taxes = Math.ceil(taxes / 0.05D) * 0.05D;
		  	totalTaxes += taxes;
		  	product.setPrice(new BigDecimal(product.getPrice()+taxes).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
	  	} 
	  	
	  	totalPrice += product.getPrice();
	  	
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
		totalTaxes = 0.00D;
	    totalPrice = 0.00D;
		return "redirect:/";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String listProducts(Model model) {
          List<Product> listProducts = productRepository.findAll();
          if (listProducts != null) {
                model.addAttribute("products", listProducts);
          }
          
          model.addAttribute("totalTaxes", fmt.format(totalTaxes));
          model.addAttribute("totalPrice", fmt.format(new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_EVEN).doubleValue()));
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
