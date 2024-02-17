package com.slmanagement.salesmanagement.controllers;

import com.slmanagement.salesmanagement.dtos.requests.ProductRequestDTO;
import com.slmanagement.salesmanagement.dtos.responses.ProductResponseDTO;
import com.slmanagement.salesmanagement.entities.Product;
import com.slmanagement.salesmanagement.services.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags = "Products")
@RestController
@RequestMapping("/categories/{categoryId}/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @ApiOperation(value = "List all products", nickname = "findAllProducts")
    @GetMapping
    public List<ProductResponseDTO> findAll(@PathVariable Long categoryId) {
        return productService
                .findAll(categoryId)
                .stream()
                .map(ProductResponseDTO::handleConvertToProductDTO)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "List products by id", nickname = "findProductById")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id, @PathVariable Long categoryId) {
        Optional<Product> product = productService.findById(id, categoryId);

        return product
                .map(value -> ResponseEntity.ok(ProductResponseDTO.handleConvertToProductDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Save product", nickname = "saveProduct")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> save(@PathVariable Long categoryId, @Valid @RequestBody ProductRequestDTO productDTO) {
        Product handleSaveProduct = productService.save(categoryId, productDTO.handleConvertToEntity(categoryId));
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductResponseDTO.handleConvertToProductDTO(handleSaveProduct));
    }

    @ApiOperation(value = "Update product", nickname = "updateProduct")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long categoryId, @PathVariable Long id, @Valid @RequestBody ProductRequestDTO productDTO) {
        Product handleUpdatedProduct = productService.update(categoryId, id, productDTO.handleConvertToEntity(id, categoryId));
        return ResponseEntity.ok(ProductResponseDTO.handleConvertToProductDTO(handleUpdatedProduct));
    }

    @ApiOperation(value = "Delete product", nickname = "deleteProduct")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @PathVariable Long categoryId) {
        productService.delete(id, categoryId);
    }
}
