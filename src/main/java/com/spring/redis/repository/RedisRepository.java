package com.spring.redis.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.redis.entity.ccenc;

@Repository
public class RedisRepository {

	
	private RedisTemplate<Object,ccenc> redisTemplate;
	private HashOperations hashOperations;
	
	public RedisRepository(RedisTemplate<Object,ccenc> redisTemplate){
		this.redisTemplate = redisTemplate;
		this.hashOperations = redisTemplate.opsForHash();
	}
	
	
	 public void save(ccenc ccEnc){
		 hashOperations.putAll(ccEnc.getAccountID(), new ObjectMapper().convertValue(ccEnc, Map.class));
	 }
	
	public ccenc findByaccountId(String accountId){
		ccenc ccEnc = new ccenc();
       // Map ccencMap = (Map) redisTemplate.opsForHash().get("ccenc", accountID);
       // ccenc ccEnc = new ObjectMapper().convertValue(ccencMap, ccenc.class);
		String key = "ccenc:"+accountId;
		System.out.println(key);
		//System.out.println(redisTemplate.opsForHash().hasKey(accountId,"paymentBillingCountryCode"));
		System.out.println(redisTemplate.opsForHash().keys("*")); // [a, isAdmin]
		System.out.println("AccountId   "+accountId);
        return (ccenc) hashOperations.get(accountId, "accountId");
	}
	
	public Map<String, ccenc> getAll(){
		return hashOperations.entries("ccenc");
	}
}
