package com.endava.NoSQL;


import com.endava.NoSQL.Model.Product;
import com.endava.NoSQL.Model.ProductItem;
import com.endava.NoSQL.Model.ProductViewEvent;
import com.endava.NoSQL.Repository.ProductItemRepository;
import com.endava.NoSQL.Repository.ProductRepository;
import com.endava.NoSQL.Services.ProductViewEventServiceImp;
import net.minidev.json.JSONUtil;
import org.hibernate.validator.constraints.Range;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductViewElementTest {

    @Autowired
    private ProductViewEventServiceImp productViewEventServiceImp;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    private Product createdProduct;
    private ProductItem createdProductItem;
    private ProductViewEvent createdProductViewEvent;
    private ProductItem createdSecondProductItem;


    @Before
    public void setUp() {
        createdProduct = new Product(1L, "Tech", "laptop", "Lenovo", "Work Laptop", Arrays.asList("fast", "great"));
        productRepository.save(createdProduct);

        createdProductItem = new ProductItem(1L, 100, 2L, 10, createdProduct);
        productItemRepository.save(createdProductItem);

        createdSecondProductItem = new ProductItem(2L, 100, 3L, 10, createdProduct);
        productItemRepository.save(createdSecondProductItem);

        Set<ProductItem> personalReomendedProductItems = new HashSet<>();
        personalReomendedProductItems.add(createdProductItem);
        personalReomendedProductItems.add(createdSecondProductItem);
        Set<ProductItem> topPicksProductItems = new HashSet<>();
        topPicksProductItems.add(createdProductItem);

        createdProductViewEvent = new ProductViewEvent(1L, createdProductItem, true, true, personalReomendedProductItems, topPicksProductItems, 1, new Date());
        productViewEventServiceImp.save(createdProductViewEvent);

        productViewEventServiceImp.save(new ProductViewEvent(2L, createdSecondProductItem, false, true, personalReomendedProductItems, topPicksProductItems, 1, new Date()));

        productViewEventServiceImp.save(new ProductViewEvent(3L, createdProductItem, true, false, personalReomendedProductItems, topPicksProductItems, 1, new Date()));

        productViewEventServiceImp.save(new ProductViewEvent(4L, createdProductItem, false, true, personalReomendedProductItems, topPicksProductItems, 14, new Date()));

        productViewEventServiceImp.save(new ProductViewEvent(5L, createdProductItem, false, true, personalReomendedProductItems, topPicksProductItems, 14, new Date()));

    }

    @Test
    public void testProductCreate() {

        Product foundProduct = productRepository.findById(1L).get();

        assertEquals(createdProduct.getId(), foundProduct.getId());
        assertEquals(createdProduct.getCategory(), foundProduct.getCategory());
        assertEquals(createdProduct.getSubcategory(), foundProduct.getSubcategory());
        assertEquals(createdProduct.getName(), foundProduct.getName());
        assertEquals(createdProduct.getDescription(), foundProduct.getDescription());
        assertFalse(createdProduct.getTags().isEmpty());

    }

    @Test
    public void testProductItemCreate() {


        ProductItem foundProductItem = productItemRepository.findById(1L).get();

        assertEquals(createdProductItem.getId(), foundProductItem.getId());
        assertEquals(createdProductItem.getPromotionId(), foundProductItem.getPromotionId());
        assertEquals(createdProductItem.getInStockQuantity(), foundProductItem.getInStockQuantity());
        assertEquals(createdProductItem.getProduct().getId(), foundProductItem.getProduct().getId());


    }

    @Test
    public void testProductViewEventCreate() {


        ProductViewEvent foundProductViewEvent = productViewEventServiceImp.findById(1L).get();

        assertEquals(createdProductViewEvent.getId(), foundProductViewEvent.getId());
        assertEquals(createdProductViewEvent.getUserId(), foundProductViewEvent.getUserId());
        assertEquals(createdProductViewEvent.getViewedProductItem().getId(), foundProductViewEvent.getViewedProductItem().getId());
        assertEquals(createdProductViewEvent.isRecommendationClick(), foundProductViewEvent.isRecommendationClick());


    }

    @Test
    public void testProductViewEventUpdate() {

        ProductViewEvent foundProductViewEvent = productViewEventServiceImp.findById(1L).get();

        ProductViewEvent updateFoundedProductViewEvent = productViewEventServiceImp.findById(1L).get();

        updateFoundedProductViewEvent.setUserId(2);

        productViewEventServiceImp.update(updateFoundedProductViewEvent);

        ProductViewEvent updatedFoundedProductViewEvent = productViewEventServiceImp.findById(1L).get();

        assertNotEquals(foundProductViewEvent.getUserId(), updatedFoundedProductViewEvent.getUserId());
    }

    @Test
    public void testDeletingProductViewEvent() {

        Optional<ProductViewEvent> productViewEvent = productViewEventServiceImp.findById(4l);

        int numberOfProductViewEventsInDatabaseBeforeDelete = productViewEventServiceImp.findAll().size();

        assertEquals(numberOfProductViewEventsInDatabaseBeforeDelete, 5);

        productViewEventServiceImp.delete(productViewEvent.get());

        int numberOfProductViewEventsAfterDelete = productViewEventServiceImp.findAll().size();

        assertEquals(numberOfProductViewEventsAfterDelete, 4);

    }


    @Test
    public void testIsProductViewEventTopPickClicked() {

        Set<ProductViewEvent> topPickedClickedSetofProductViewEvents = productViewEventServiceImp.findAllByIsTopPickClick();

        assertEquals(topPickedClickedSetofProductViewEvents.size(), 4);


    }

    @Test
    public void testMapingProductViewEventsByPromotionId() {
        Set<Long> promotionIds = getSetOfPromotionIdFromProductItem();
        Map<Long, List<ProductViewEvent>> productViewEventsMapByPromotionId = MappedByPromotionIdProductViewEvents(promotionIds);

        assertEquals(productViewEventsMapByPromotionId.get(3l).size(), 1);
        assertEquals(productViewEventsMapByPromotionId.get(2l).size(), 4);


    }

    private Map<Long, List<ProductViewEvent>> MappedByPromotionIdProductViewEvents(Set<Long> promotionIds) {
        return promotionIds.stream()
                .collect(Collectors.toMap(aLong -> aLong, aLong -> productViewEventServiceImp.findAllByViewedProductItemPromotionId(aLong)
                        .stream().collect(Collectors.toList())));
    }

    private Set<Long> getSetOfPromotionIdFromProductItem() {
        return productItemRepository.findAll().stream()
                .filter(productItem -> productItem.getPromotionId() != null).map(productItem -> productItem.getPromotionId())
                .collect(Collectors.toSet());
    }


}
