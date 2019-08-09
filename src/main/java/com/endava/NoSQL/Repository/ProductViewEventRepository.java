package com.endava.NoSQL.Repository;

import com.endava.NoSQL.Model.ProductViewEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductViewEventRepository extends CrudRepository<ProductViewEvent, Long> {
    public ProductViewEvent save(ProductViewEvent productViewEvent);

    public Optional<ProductViewEvent> findById(Long id);

    public void delete(ProductViewEvent productViewEvent);
    public Set<ProductViewEvent> findAll();

    public Set<ProductViewEvent> findAllByIsTopPickClick(boolean isPicked);

    public Set<ProductViewEvent> findAllByViewedProductItemPromotionId(Long id);
}
