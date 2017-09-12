package com.rodrigopeleias.products.service;

import com.rodrigopeleias.products.domain.Product;
import com.rodrigopeleias.products.exception.ProductNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void shouldCreateProduct() {
        Product product = new Product();
        product.setName("New product");
        product.setDescription("Description");

        Product savedProduct = productService.save(product);
        assertThat(savedProduct.getId(), notNullValue());
        assertThat(savedProduct.getName(), equalTo(product.getName()));
        assertThat(savedProduct.getDescription(), equalTo(product.getDescription()));
    }

    @Test
    public void shouldUpdateAnExistingProduct() {
        Product product = new Product();
        product.setName("Update test");
        product.setDescription("Description");

        Product savedProduct = productService.save(product);
        savedProduct.setName("New name");
        savedProduct.setDescription("New description");

        Product updatedProduct = productService.update(savedProduct.getId(), savedProduct);
        assertThat(updatedProduct.getId(), notNullValue());
        assertThat(updatedProduct.getName(), equalTo(savedProduct.getName()));
        assertThat(updatedProduct.getDescription(), equalTo(savedProduct.getDescription()));
    }

    @Test(expected = ProductNotFoundException.class)
    public void shouldThrowExceptionWhenUpdateANonExistingProduct() {
        Product product = new Product();
        product.setName("Update test");
        product.setDescription("Description");
        productService.update(20L, product);
    }

    @Test
    public void shouldDeleteAnExistingProduct() {
        Product product = new Product();
        product.setName("Delete test");
        product.setDescription("Description");

        Product savedProduct = productService.save(product);
        productService.delete(savedProduct.getId());

        Product deletedProduct = productService.findById(product.getId());
        assertThat(deletedProduct, nullValue());
    }

    @Test(expected = ProductNotFoundException.class)
    public void shouldThrowAnExceptionWhenDeleteNonExistingProduct() {
        productService.delete(20L);
    }
}