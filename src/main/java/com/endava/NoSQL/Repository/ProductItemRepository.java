package com.endava.NoSQL.Repository;

import com.endava.NoSQL.Model.ProductItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductItemRepository extends CrudRepository<ProductItem,Long> {
    public Set<ProductItem> findAll();
}
