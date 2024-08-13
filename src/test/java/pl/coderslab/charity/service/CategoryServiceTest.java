package pl.coderslab.charity.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    private static final String CATEGORY_NAME = "test";
    private static final Long CATEGORY_ID_1 = 1L;
    private static final Long CATEGORY_ID_2 = 2L; ;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;


    @Test
    void getAllCategoriesShouldReturnCategoryList() {
        when(categoryRepository.findAll()).thenReturn(getCategories());
        List<Category> categories = categoryService.getAllCategories();
        assertEquals(2, categories.size());
        assertEquals(CATEGORY_NAME, categories.get(0).getName());
        verify(categoryRepository).findAll();
    }

    @Test
    void getCategoryByIdShouldReturnCategoryIfExists() {
        Category category = new Category(CATEGORY_ID_1, CATEGORY_NAME);
        when(categoryRepository.findById(CATEGORY_ID_1)).thenReturn(Optional.of(category));
        Category categoryResult = categoryService.getCategoryById(CATEGORY_ID_1);
        assertEquals(category, categoryResult);
        verify(categoryRepository).findById(CATEGORY_ID_1);
    }

    @Test
    void getCategoryByIdShouldThrowExceptionCategoryWhenNotFound() {
        when(categoryRepository.findById(CATEGORY_ID_1)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> categoryService.getCategoryById(CATEGORY_ID_1));
        assertEquals("Category not found " + CATEGORY_ID_1, exception.getMessage());
        verify(categoryRepository).findById(CATEGORY_ID_1);
    }

    @Test
    void saveCategoryShouldReturnSavedCategory() {
        Category category = new Category(CATEGORY_ID_1, CATEGORY_NAME);
        when(categoryRepository.save(category)).thenReturn(category);
        Category result = categoryService.saveCategory(category);
        assertEquals(category, result);
        verify(categoryRepository).save(category);
    }

    @Test
    void deleteCategoryShouldCallDeletedById() {
        Long categoryId = 1L;
        categoryService.deleteCategory(categoryId);
        verify(categoryRepository).deleteById(categoryId);

    }

    private List<Category> getCategories() {
        return List.of(new Category(CATEGORY_ID_1, CATEGORY_NAME), new Category(CATEGORY_ID_2, null));
    }
}