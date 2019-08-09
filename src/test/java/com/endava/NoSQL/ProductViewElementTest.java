package com.endava.NoSQL;


import com.endava.NoSQL.Model.Product;
import com.endava.NoSQL.Model.ProductItem;
import com.endava.NoSQL.Model.ProductViewEvent;
import com.endava.NoSQL.Repository.ProductItemRepository;
import com.endava.NoSQL.Repository.ProductRepository;
import com.endava.NoSQL.Services.ProductViewEventServiceImp;
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


    @Before
    public void setUp() {
        createdProduct = new Product(1L, "Tech", "laptop", "Lenovo", "Work Laptop", Arrays.asList("fast", "great"));
        productRepository.save(createdProduct);

        createdProductItem = new ProductItem(1L, 100, 2L, 10, createdProduct);
        productItemRepository.save(createdProductItem);

        Set<ProductItem> personalReomendedProductItems = new HashSet<>();
        personalReomendedProductItems.add(createdProductItem);
        Set<ProductItem> topPicksProductItems = new HashSet<>();
        topPicksProductItems.add(createdProductItem);

        createdProductViewEvent = new ProductViewEvent(3L, createdProductItem, true, true, personalReomendedProductItems, topPicksProductItems, 1, new Date());
        productViewEventServiceImp.save(createdProductViewEvent);

        createdProductViewEvent = new ProductViewEvent(4L, createdProductItem, true, true, personalReomendedProductItems, topPicksProductItems, 1, new Date());
        productViewEventServiceImp.save(createdProductViewEvent);

        createdProductViewEvent = new ProductViewEvent(1L, createdProductItem, true, true, personalReomendedProductItems, topPicksProductItems, 1, new Date());
        productViewEventServiceImp.save(createdProductViewEvent);
    }

    @Test
    public void testProductCreate() {
         /*createdProduct = new Product(1L, "Tech", "laptop", "Lenovo", "Work Laptop", Arrays.asList("fast", "great"));
        productRepository.save(createdProduct);*/

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

        //Product product = productRepository.findById(1L).get();

        /*createdProductItem = new ProductItem(1L, 100, 2L, 10, createdProduct);
        productItemRepository.save(createdProductItem);*/

        ProductItem foundProductItem = productItemRepository.findById(1L).get();

        assertEquals(createdProductItem.getId(), foundProductItem.getId());
        assertEquals(createdProductItem.getPromotionId(), foundProductItem.getPromotionId());
        //assertEquals(createdProductItem.getPrice(),foundProductItem.getPrice());
        assertEquals(createdProductItem.getInStockQuantity(), foundProductItem.getInStockQuantity());
        assertEquals(createdProductItem.getProduct().getId(), foundProductItem.getProduct().getId());


    }

    @Test
    public void testProductViewEventCreate() {

        //ProductItem productItem = productItemRepository.findById(1L).get();

        /*Set<ProductItem> personalReomendedProductItems = new HashSet<>();
        personalReomendedProductItems.add(createdProductItem);
        Set<ProductItem> topPicksProductItems = new HashSet<>();
        topPicksProductItems.add(createdProductItem);

         createdProductViewEvent = new ProductViewEvent(1L, productItem, true, true, personalReomendedProductItems, topPicksProductItems, 1, new Date());
        productViewEventServiceImp.save(createdProductViewEvent);*/

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
        System.out.println(updatedFoundedProductViewEvent.getUserId() + " " + foundProductViewEvent.getUserId());

        assertNotEquals(foundProductViewEvent.getUserId(), updatedFoundedProductViewEvent.getUserId());
    }

    @Test
    public void testDeletingProductViewEvent() {

        Set<ProductItem> personalReomendedProductItems = new HashSet<>();
        personalReomendedProductItems.add(createdProductItem);
        Set<ProductItem> topPicksProductItems = new HashSet<>();
        topPicksProductItems.add(createdProductItem);

        ProductViewEvent ProductViewEvent = new ProductViewEvent(2L, createdProductItem, false, true, personalReomendedProductItems, topPicksProductItems, 14, new Date());
        productViewEventServiceImp.save(ProductViewEvent);
        int numObjects = productViewEventServiceImp.findAll().size();
        assertEquals(numObjects, 4);
        productViewEventServiceImp.delete(ProductViewEvent);
        int numObjectsAfterDelete = productViewEventServiceImp.findAll().size();
        assertEquals(numObjectsAfterDelete, 3);

    }

   /* @Test
    public  void testIsTopPickClickedProductViewEvent(){

        Set<ProductItem> personalReomendedProductItems = new HashSet<>();
        personalReomendedProductItems.add(createdProductItem);
        Set<ProductItem> topPicksProductItems = new HashSet<>();
        topPicksProductItems.add(createdProductItem);

        ProductViewEvent ProductViewEvent = new ProductViewEvent(2L, createdProductItem, false, true, personalReomendedProductItems, topPicksProductItems, 14, new Date());
        productViewEventServiceImp.save(ProductViewEvent);
        System.out.println("Id of top picked ProductViewEvents");
        productViewEventServiceImp.findTopPickedProductViewEvents().stream().forEach(productViewEvent -> System.out.println(productViewEvent.getId()));

        int numOfTopPickedProductViewEvents = productViewEventServiceImp.findTopPickedProductViewEvents().size();

        assertEquals(numOfTopPickedProductViewEvents,2);

    }*/

    @Test
    public void testIsTopPickedClcket() {

        Set<ProductItem> personalReomendedProductItems = new HashSet<>();
        personalReomendedProductItems.add(createdProductItem);
        Set<ProductItem> topPicksProductItems = new HashSet<>();
        topPicksProductItems.add(createdProductItem);

        ProductViewEvent ProductViewEvent = new ProductViewEvent(2L, createdProductItem, false, true, personalReomendedProductItems, topPicksProductItems, 14, new Date());
        productViewEventServiceImp.save(ProductViewEvent);

        productViewEventServiceImp.findAllByIsTopPickClick(true).stream()
                .forEach(System.out::println);


    }

    @Test
    public void testByPromotionId() {
        Set<Long> ids = productItemRepository.findAll().stream()
                .filter(productItem -> productItem.getPromotionId() != null).map(productItem -> productItem.getPromotionId())
                .collect(Collectors.toSet());
        Map<Long, List<ProductViewEvent>> productViewEventsList = ids.stream()
                .collect(Collectors.toMap(aLong -> aLong, aLong -> productViewEventServiceImp.findAllByViewedProductItemPromotionId(aLong)
                        .stream().collect(Collectors.toList())));

        productViewEventsList.forEach((aLong, productViewEvents) -> System.out.println(productViewEvents));


    }


}
