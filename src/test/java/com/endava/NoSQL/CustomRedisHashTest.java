package com.endava.NoSQL;

import com.endava.NoSQL.Model.ProductViewEvent;
import com.endava.NoSQL.Repository.CustomRedisHashRepository;
import com.endava.NoSQL.Repository.Implementation.CustomeRedisHashRepositoryImpl;
import com.endava.NoSQL.Services.ProductViewEventServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomRedisHashTest {

    @Autowired
    ProductViewEventServiceImp productViewEventServiceImp;

    @Autowired
    CustomRedisHashRepository customeRedisHashRepository;

    @Before
    public void setUp() {

        productViewEventServiceImp.findAll().stream().forEach(productViewEvent -> {customeRedisHashRepository.AddKeyValueForSortedSet(productViewEvent);
            System.out.println(productViewEvent.getId()+" "+productViewEvent.getDateTimeViewed().getTime());
        });

    }

    @Test
    public void TestGetRangeByScoreFromSet(){
        customeRedisHashRepository.getElementFromSortedSetByScore(0d,1565350078301d);
    }

    @Test
    public void TestGetByRange(){
        System.out.println(customeRedisHashRepository.getByRange(0L,1L));
    }
    @Test
    public void TestRemoveFromSetByLowestscore(){

        System.out.println(customeRedisHashRepository.removeReturnedFromSetByLowestScore());
    }
    @Test
    public void TestCountOfMembesFromSet(){
        System.out.println(customeRedisHashRepository.countMembers());
    }


}
