package com.endava.NoSQL.Repository.Implementation;

import com.endava.NoSQL.Model.Product;
import com.endava.NoSQL.Model.ProductViewEvent;
import com.endava.NoSQL.Repository.CustomRedisHashRepository;
import com.endava.NoSQL.Services.ProductViewEventServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Repository
public class CustomeRedisHashRepositoryImpl implements CustomRedisHashRepository {

     private static final String KEY_FOR_SORTED_SET ="MySortedSet";

    @Autowired
    ProductViewEventServiceImp productViewEventServiceImp;


    @Autowired
    private ZSetOperations<String,Object> zSetOperation;


    @Override
    public boolean AddKeyValueForSortedSet(ProductViewEvent productViewEvent) {
         zSetOperation.add(KEY_FOR_SORTED_SET,productViewEvent,productViewEvent.getDateTimeViewed().getTime());

        return true;
    }

    @Override
    public Set<ProductViewEvent> getByRange(Long min,Long max) {
        Set<Object> valueByRange = zSetOperation.range(KEY_FOR_SORTED_SET,min,max);
        if(valueByRange == null){
            return new HashSet<ProductViewEvent>();
        }else{
            return valueByRange.stream().map(o -> (ProductViewEvent)o).collect(Collectors.toSet());
        }
        /*return zSetOperation.range(KEY_FOR_SORTED_SET,min,max).stream().map(o -> (ProductViewEvent)o).collect(Collectors.toSet());*/
    }


    @Override
    public Set<ProductViewEvent> getElementFromSortedSetByScore(Double rangeStart,Double rangeEnd) {
        Set<Object> valueByRange = zSetOperation.rangeByScore(KEY_FOR_SORTED_SET,rangeStart,rangeEnd);
        if(valueByRange == null){
            return new HashSet<ProductViewEvent>();
        }else{
            return valueByRange.stream().map(o -> (ProductViewEvent)o).collect(Collectors.toSet());
        }
    }

    @Override
    public Long removeReturnedFromSetByLowestScore() {
        ProductViewEvent productViewEvent = new ArrayList<>(getByRange(0L, 0L)).get(0);
        return zSetOperation.remove(KEY_FOR_SORTED_SET,productViewEvent);
    }

    @Override
    public Long countMembers() {
        return zSetOperation.count(KEY_FOR_SORTED_SET,Double.MIN_VALUE,Double.MAX_VALUE);
    }
}
