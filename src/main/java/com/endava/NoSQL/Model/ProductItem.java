package com.endava.NoSQL.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash("ProductItem")
public class ProductItem implements Serializable {
    @Id
    private Long id;
    private double price;
    @Indexed
    private Long promotionId;
    private int inStockQuantity;
    private Product product;

    public ProductItem() {
    }

    public ProductItem(Long id, double price, Long promotionId, int inStockQuantity, Product product) {
        this.id = id;
        this.price = price;
        this.promotionId = promotionId;
        this.inStockQuantity = inStockQuantity;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public int getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(int inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "id=" + id +
                ", price=" + price +
                ", promotionId=" + promotionId +
                ", inStockQuantity=" + inStockQuantity +
                ", product=" + product +
                '}';
    }
}
