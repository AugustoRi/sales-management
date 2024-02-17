package com.slmanagement.salesmanagement.controllers;

import com.slmanagement.salesmanagement.dtos.requests.CategoryRequestDTO;
import com.slmanagement.salesmanagement.dtos.responses.CategoryResponseDTO;
import com.slmanagement.salesmanagement.entities.Category;
import com.slmanagement.salesmanagement.services.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags = "Categories")
@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "List all categories", nickname = "findAllCategories")
    @GetMapping
    public List<CategoryResponseDTO> findAll() {
        return categoryService
                .findAll()
                .stream()
                .map(CategoryResponseDTO::handleConvertToCategoryDTO)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "List categories by id", nickname = "findCategoryById")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);

        return category
                .map(cat -> ResponseEntity.ok(CategoryResponseDTO.handleConvertToCategoryDTO(cat)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Save category", nickname = "saveCategory")
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> save(@Valid @RequestBody CategoryRequestDTO categoryDto) {
        Category handleSaveCategory = categoryService.save(categoryDto.handleConvertToEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryResponseDTO.handleConvertToCategoryDTO(handleSaveCategory));
    }

    @ApiOperation(value = "Update category", nickname = "updateCategory")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO categoryDto) {
        Category handleUpdatedCategory = categoryService.update(id, categoryDto.handleConvertToEntity(id));
        return ResponseEntity.ok(CategoryResponseDTO.handleConvertToCategoryDTO(handleUpdatedCategory));
    }

    @ApiOperation(value = "Delete Category", nickname = "deleteCategory")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
