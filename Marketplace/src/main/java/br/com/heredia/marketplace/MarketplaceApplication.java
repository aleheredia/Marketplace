package br.com.heredia.marketplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.heredia.marketplace.entity.Product;
import br.com.heredia.marketplace.repository.ProductRepository;

@SpringBootApplication
@EntityScan(basePackages = { "br.com.heredia.marketplace.entity" })
@EnableJpaRepositories(basePackages = { "br.com.heredia.marketplace.repository" })
@ComponentScan(basePackages = {"br.com.heredia.marketplace.controller"})
public class MarketplaceApplication implements CommandLineRunner{
	
	@Autowired
	private ProductRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}
	
	@Override
    public void run(String... args) {
        repository.save(new Product("Predadores", "book", 12.49, false));
        repository.save(new Product("Greatest Hits", "music", 14.99, false));
        repository.save(new Product("Milky Way", "food", 0.85, false));
        
        repository.save(new Product("Godiva Box", "food", 10.00, true));
        repository.save(new Product("Channel #5", "beauty", 47.50, true));
        repository.save(new Product("Flower by Kenzo", "beauty", 27.99, true));
        
        repository.save(new Product("Malbec", "beauty", 18.99, false));
        repository.save(new Product("Aspirin bottle", "medical", 9.75, false));
        repository.save(new Product("Lindt Box", "food", 11.25, true));
	}

}
