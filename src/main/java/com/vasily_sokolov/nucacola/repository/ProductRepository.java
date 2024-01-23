package com.vasily_sokolov.nucacola.repository;

import com.vasily_sokolov.nucacola.entity.Product;
import com.vasily_sokolov.nucacola.entity.enums.ProductCharacteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("select p from Product p where p.productName = :name")
    List<Product> getProductsByName(String name);

    @Query("select p from Product p")
    List<Product> getAllProducts();

    @Query("select p from Product p where p.characteristic = :characteristic")
    List<Product> getProductsByCharacteristic(ProductCharacteristic characteristic);

    @Modifying
    @Query("update Product p set p.productPrice = :productPrice where p.productId = :uuid")
    void updateProductPrice(UUID uuid, double productPrice);

}
