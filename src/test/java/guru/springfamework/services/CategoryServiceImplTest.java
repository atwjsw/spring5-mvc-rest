package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CategoryServiceImplTest {

    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    List<Category> categories;
    Category c1;
    Category c2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);

        c1 = new Category();
        c1.setId(1L);
        c1.setName("category1");
        c2 = new Category();
        c2.setId(2L);
        c2.setName("category2");
        categories = new ArrayList<>();
        categories.add(c1);
        categories.add(c2);
    }

    @Test
    public void getAllCategories() {
        when(categoryRepository.findAll()).thenReturn(categories);
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();
        assertNotNull(categoryDTOS);
        assertThat(categoryDTOS.size(), is(2));
    }

    @Test
    public void getCategoryByName() {
        when(categoryRepository.findByName(anyString())).thenReturn(c1);
        CategoryDTO categoryDTO = categoryService.getCategoryByName("test");
        assertNotNull(categoryDTO);
        assertThat(categoryDTO.getId(), is(1L));
        assertThat(categoryDTO.getName(), is("category1"));
    }
}