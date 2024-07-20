package pl.coderslab.charity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.orElseThrow(() -> new RuntimeException("Category not found " + id));
    //pozytytwny kiedy dostaje w odpowiedzi category
        //został rzucony exception
        //TEST TODO
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
    //verify
    //sprawdzic wywowałnie z id który dałem na wejściu

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    //verify
    }
}
