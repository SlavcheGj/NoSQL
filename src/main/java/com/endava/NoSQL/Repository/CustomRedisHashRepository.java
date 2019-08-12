package com.endava.NoSQL.Repository;

import com.endava.NoSQL.Model.Product;
import com.endava.NoSQL.Model.ProductViewEvent;
import com.google.gson.JsonElement;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomRedisHashRepository {

    public boolean AddKeyValueForSortedSet(ProductViewEvent productViewEvent);
    public Set<ProductViewEvent> getByRange(Long min, Long max);
    public Long removeReturnedFromSetByLowestScore();
    public Set<ProductViewEvent> getElementFromSortedSetByScore(Double range1,Double range2);
    public Long countMembers();



}
