package com.slmanagement.salesmanagement.services;

import com.slmanagement.salesmanagement.entities.Category;
import com.slmanagement.salesmanagement.exceptions.BusinessRuleException;
import com.slmanagement.salesmanagement.repositories.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService {
    // creates at runtime, dependency injection, without = possible null pointer
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category save(Category category) {
        handleValidateDuplicateCategory(category);
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category category) {
        Category handleGetCategory = handleCheckCategory(id);
        handleValidateDuplicateCategory(category);
        BeanUtils.copyProperties(category, handleGetCategory, "codigo");
        return categoryRepository.save(handleGetCategory);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    private Category handleCheckCategory(Long id) {
        Optional<Category> category = findById(id);
        if (category.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }

        return category.get();
    }

    private void handleValidateDuplicateCategory(Category category) {
        Category categoryFound = categoryRepository.findByName(category.getName());

        if (categoryFound != null && !Objects.equals(categoryFound.getId(), category.getId())) {
            throw new BusinessRuleException(
                    String.format("A categoria %s já existe", category.getName().toUpperCase())
            );
        }
    }
}
