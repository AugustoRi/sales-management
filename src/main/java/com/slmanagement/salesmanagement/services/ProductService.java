package com.slmanagement.salesmanagement.services;

import com.slmanagement.salesmanagement.entities.Product;
import com.slmanagement.salesmanagement.exceptions.BusinessRuleException;
import com.slmanagement.salesmanagement.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;

    public List<Product> findAll(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public Optional<Product> findById(Long id, Long categoryId) {
        handleValidateCategoryOfProduct(categoryId);
        return productRepository.findByProductId(id, categoryId);
    }

    public Product save(Long categoryId, Product product) {
        handleValidateCategoryOfProduct(categoryId);
        handleValidateDuplicateProduct(product);
        return productRepository.save(product);
    }

    public Product update(Long categoryId, Long id, Product product) {
        Product handleGetProduct = handleCheckProduct(id, categoryId);
        handleValidateCategoryOfProduct(categoryId);
        handleValidateDuplicateProduct(product);
        BeanUtils.copyProperties(product, handleGetProduct, "codigo");
        return productRepository.save(handleGetProduct);
    }

    public void delete(Long id, Long categoryId) {
        Product product = handleCheckProduct(id, categoryId);
        productRepository.delete(product);
    }

    protected void handleUpdateQuantityInStock(Product product) {
        productRepository.save(product);
    }

    protected Product handleCheckProduct(Long id) {
        if (id == null) {
            throw new BusinessRuleException("O código do produto não pode ser nulo.");
        }

        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new BusinessRuleException(String.format("O produto de código %s informado não existe.", id));
        }

        return product.get();
    }

    private Product handleCheckProduct(Long id, Long categoryId) {
        Optional<Product> product = findById(id, categoryId);

        if (product.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }

        return product.get();
    }

    private void handleValidateCategoryOfProduct(Long categoryId) {
        if (categoryId == null) {
            throw new BusinessRuleException("A categoria não pode ser nula.");
        }

        if (categoryService.findById(categoryId).isEmpty()) {
            throw new BusinessRuleException(String.format("A categoria de código %s informada não existe.", categoryId));
        }
    }

    private void handleValidateDuplicateProduct(Product product) {
        Optional<Product> productFound = productRepository.findByCategoryIdAndDescription(product.getCategory().getId(), product.getDescription());

        if (productFound.isPresent() && !Objects.equals(product.getId(), productFound.get().getId())) {
            throw new BusinessRuleException(String.format("O produto já existe com o código %s.", productFound.get().getId()));
        }
    }
}
