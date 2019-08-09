package com.endava.NoSQL.Services;

import com.endava.NoSQL.Model.ProductViewEvent;


import java.util.Optional;
import java.util.Set;

public interface ProductViewEventService {
    public ProductViewEvent save(ProductViewEvent productViewEvent);

    public Optional<ProductViewEvent> findById(Long id);

    public ProductViewEvent update(ProductViewEvent productViewEvent);

    public void delete(ProductViewEvent productViewEvent);
    public Set<ProductViewEvent> findAll();

    public Set<ProductViewEvent> findAllByIsTopPickClick(boolean isPicked);

    public Set<ProductViewEvent> findAllByViewedProductItemPromotionId(Long id);
}
