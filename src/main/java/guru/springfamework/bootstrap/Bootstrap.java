package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategory();
        loadCustomer();
        loadVendor();
    }

    private void loadCategory() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Category Data Loaded = " + categoryRepository.count());
    }

    private void loadCustomer() {
        Customer joe = new Customer();
        joe.setFirstName("Joe");
        joe.setLastName("Buck");

        Customer john = new Customer();
        john.setFirstName("John");
        john.setLastName("Smith");

        Customer michael = new Customer();
        michael.setFirstName("Michael");
        michael.setLastName("Weston");

        customerRepository.save(joe);
        customerRepository.save(john);
        customerRepository.save(michael);

        System.out.println("Customer Data Loaded = " + customerRepository.count());
    }

    private void loadVendor() {

        Vendor exoticFruits = new Vendor();
        exoticFruits.setName("Exotic Fruits Company");

        Vendor homeFruits = new Vendor();
        homeFruits.setName("Home Fruits");

        Vendor funFreshFruits = new Vendor();
        funFreshFruits.setName("Fun Fresh Fruits Ltd.");

        Vendor nutsForNuts = new Vendor();
        nutsForNuts.setName("Nuts for Nuts Company");

        Vendor franksFreshFruitsFromFrance = new Vendor();
        franksFreshFruitsFromFrance.setName("Franks Fresh Fruits from France Ltd.");

        vendorRepository.save(exoticFruits);
        vendorRepository.save(homeFruits);
        vendorRepository.save(funFreshFruits);
        vendorRepository.save(nutsForNuts);
        vendorRepository.save(franksFreshFruitsFromFrance);

        System.out.println("Vendor Data Loaded = " + vendorRepository.count());

    }
}
