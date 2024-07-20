package pl.coderslab.charity.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.repository.CategoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void getAllCategoriesShouldReturnCategoryList() {
        when(categoryRepository.findAll()).thenReturn(List.of(new Category(1L, "test"), new Category(2L, null)));
        List<Category> category = categoryService.getAllCategories();
        assertEquals(2, category.size());
        assertEquals("test", category.get(0).getName());
        verify(categoryRepository).findAll();

    }




}