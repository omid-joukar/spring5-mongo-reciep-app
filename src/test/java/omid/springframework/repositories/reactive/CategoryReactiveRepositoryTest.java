package omid.springframework.repositories.reactive;

import omid.springframework.domain.Category;
import omid.springframework.domain.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {
    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Before
    public void setUp() throws Exception {
        categoryReactiveRepository.deleteAll().block();
    }
    @Test
    public void  tesCategorySave()throws Exception{
        Category category = new Category();
        category.setDescription("Funny");
        categoryReactiveRepository.save(category).block();
        Long count = categoryReactiveRepository.count().block();
        assertEquals(Long.valueOf(1L),count);
    }
    @Test
    public void  findByDescription()throws Exception{
        Category category = new Category();
        category.setDescription("Funny");
        categoryReactiveRepository.save(category).block();
        Category findCategory = categoryReactiveRepository.findByDescription("Funny").block();
        assertNotNull(findCategory);
    }

}