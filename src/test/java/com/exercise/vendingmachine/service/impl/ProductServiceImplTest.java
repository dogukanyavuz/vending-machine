package com.exercise.vendingmachine.service.impl;

import ch.qos.logback.classic.util.CopyOnInheritThreadLocal;
import com.exercise.vendingmachine.dto.ProductDto;
import com.exercise.vendingmachine.dto.UserDetailsDto;
import com.exercise.vendingmachine.enumeration.UserRole;
import com.exercise.vendingmachine.model.Product;
import com.exercise.vendingmachine.model.User;
import com.exercise.vendingmachine.repository.ProductRepository;
import com.exercise.vendingmachine.repository.UserRepository;
import com.exercise.vendingmachine.service.ProductService;
import com.exercise.vendingmachine.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock private ProductRepository productRepository;
    @InjectMocks private ProductServiceImpl productServiceImpl;

    @Mock private ProductService productService;

    Product product1 = new Product(1L,10, 99,"Coca Cola",1L);
    Product product2 = new Product(2l,40,200,"Fanta",2l);

    User user1 = new User(1l,"Dogukan","1234",100L, UserRole.SELLER);

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void createProduct() {
        //given
        UserDetailsDto userDetailsDto = new UserDetailsDto(user1);

        ProductDto productDto = new ProductDto();
        productDto.setProductName("Fanta");
        productDto.setCost(200);
        productDto.setAmountAvailable(40);

        //when
        Product product = Product.builder()
                .id(null)
                .amountAvailable(productDto.getAmountAvailable())
                .cost(productDto.getCost())
                .productName(productDto.getProductName())
                .sellerId(userDetailsDto.getUser().getId())
                .build();

        //then
        when(productRepository.save(product)).thenReturn(product2);
        assertEquals(productService.createProduct(userDetailsDto, productDto), product2);
    }

    @Test
    void getProduct() {

        //given
        given(productRepository.findById(product1.getId())).willReturn(Optional.of(product1));

        //then
        assertEquals(productService.getProduct(1L),product1);
    }

    @Test
    void updateProduct() {
        Product product = Product.builder()
                .id(2l)
                .amountAvailable(40)
                .cost(200)
                .productName("Fanta")
                .sellerId(2l)
                .build();

        UserDetailsDto userDetailsDto = new UserDetailsDto(user1);

        ProductDto productDto = new ProductDto();
        productDto.setProductName("Fanta");
        productDto.setAmountAvailable(20);
        productDto.setCost(31);

        Product updateProduct = product.builder()
                .id(2l)
                .amountAvailable(20)
                .cost(31)
                .productName("Fanta")
                .sellerId(2l)
                .build();

        Mockito.when(productRepository.findByIdAndSellerId(2l,userDetailsDto.getUser().getId())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(updateProduct)).thenReturn(updateProduct);
        assertEquals(productServiceImpl.updateProduct(userDetailsDto,2l,productDto),updateProduct);
    }

    @Test
    void deleteProduct() {
        //given
        UserDetailsDto userDetailsDto = new UserDetailsDto(user1);

        //then
        when(productRepository.findByIdAndSellerId(2l,userDetailsDto.getUser().getId())).thenReturn(Optional.ofNullable(product2));
        assertEquals(productService.deleteProduct(userDetailsDto,2l),product2);
    }
}