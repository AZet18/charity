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

    @Test
    void getCategoryByIdShouldReturnCategoryIfExist() {
        Long categoryId = 1L;
        Category category = new Category(1L, "test3");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Category categoryResult = categoryService.getCategoryById(1L);
        assertEquals(category, categoryResult);
        verify(categoryRepository).findById(categoryId);
    }

    @Test
    void getCategoryByIdShouldThrowExceptionCategoryWhenNotFound() {
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> categoryService.getCategoryById(id));
        assertEquals("Category not found " + id, exception.getMessage());
        verify(categoryRepository).findById(id);
    }

    @Test
    void saveCategoryShouldReturnSavedCategory() {
        Category category = new Category(1L, "test4");
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
}