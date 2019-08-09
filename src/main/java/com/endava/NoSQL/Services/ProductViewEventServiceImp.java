package com.endava.NoSQL.Services;

import com.endava.NoSQL.Model.ProductViewEvent;
import com.endava.NoSQL.Repository.ProductItemRepository;
import com.endava.NoSQL.Repository.ProductViewEventRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductViewEventServiceImp implements ProductViewEventService {

    private ProductViewEventRepository productViewEventRepository;

    public ProductViewEventServiceImp(ProductViewEventRepository productViewEventRepository) {
        this.productViewEventRepository = productViewEventRepository;
    }

    @Override
    public ProductViewEvent save(ProductViewEvent productViewEvent) {
        return productViewEventRepository.save(productViewEvent);
    }

    @Override
    public Optional<ProductViewEvent> findById(Long id) {
        return productViewEventRepository.findById(id);
    }

    @Override
    public ProductViewEvent update(ProductViewEvent productViewEvent) {
        return productViewEventRepository.save(productViewEvent);
    }

    @Override
    public void delete(ProductViewEvent productViewEvent) {
        productViewEventRepository.delete(productViewEvent);
    }

    @Override
    public Set<ProductViewEvent> findAll() {

        return productViewEventRepository.findAll();
    }


    @Override
    public Set<ProductViewEvent> findAllByIsTopPickClick(boolean isPicked) {
        return productViewEventRepository.findAllByIsTopPickClick(isPicked);
    }

    @Override
    public Set<ProductViewEvent> findAllByViewedProductItemPromotionId(Long id) {
        return productViewEventRepository.findAllByViewedProductItemPromotionId(id);
    }



}
