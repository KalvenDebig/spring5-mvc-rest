package kalven.springframework.bootstrap;

import kalven.springframework.domain.Category;
import kalven.springframework.repositories.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/16/23
 */
@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;

    public Bootstrap(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category fruits = new Category();
        fruits.setName("fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fish = new Category();
        fish.setName("Fish");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fish);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);
        System.out.println("Data Loaded: " + categoryRepository.count());
    }
}
