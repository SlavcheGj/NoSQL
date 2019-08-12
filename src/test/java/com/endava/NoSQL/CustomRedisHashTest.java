package com.endava.NoSQL;

import com.endava.NoSQL.Model.ProductViewEvent;
import com.endava.NoSQL.Repository.CustomRedisHashRepository;
import com.endava.NoSQL.Repository.Implementation.CustomeRedisHashRepositoryImpl;
import com.endava.NoSQL.Services.ProductViewEventServiceImp;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomRedisHashTest {

    @Autowired
    ProductViewEventServiceImp productViewEventServiceImp;

    @Autowired
    CustomRedisHashRepository customeRedisHashRepository;



    @Before
    public void setUp() {
        productViewEventServiceImp.findAll().stream().forEach(productViewEvent -> {
            customeRedisHashRepository.AddKeyValueForSortedSet(productViewEvent);
                        System.out.println(productViewEvent.getId()+" "+productViewEvent.getDateTimeViewed().getTime());
        });


    }

    @Test
    public void TestGetRangeByScoreFromSet() {

        Optional<ProductViewEvent> elementFromSortedSetByScore = getElementByScore();
        System.out.println(elementFromSortedSetByScore.get());
        assertEquals(elementFromSortedSetByScore.get().getId().longValue(), 1);

    }


    @Test
    public void TestGetByRange() {
        customeRedisHashRepository.getByRange(0l,3l).forEach(productViewEvent -> System.out.println(productViewEvent.toString()));
        assertEquals(customeRedisHashRepository.getByRange(0L, 3L).size(),4);

    }

    @Test
    public void TestRemoveFromSetByLowestScore() {
        Long numberOfElementsBeforeDeleteOperationInSortedSet = customeRedisHashRepository.countMembers();
        customeRedisHashRepository.removeReturnedFromSetByLowestScore();
        Long numberOfElementsAfterDeleteOperationInSortedSet = customeRedisHashRepository.countMembers();
        assertNotEquals(numberOfElementsAfterDeleteOperationInSortedSet,numberOfElementsBeforeDeleteOperationInSortedSet);

    }

    @Test
    public void TestCountOfMembersFromSet() {

        Long numberOfElementsInSortedSet = customeRedisHashRepository.countMembers();
        assertEquals(numberOfElementsInSortedSet.longValue(),5);
    }

    private Optional<ProductViewEvent> getElementByScore() {
        return customeRedisHashRepository.getElementFromSortedSetByScore(0d, 1565621238186d).stream().findFirst();
    }

}
