package com.spring.redis.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.redis.RedisService;
import com.spring.redis.SpringBootRedisApplication;
import com.spring.redis.entity.ccenc;
import com.spring.redis.repository.RedisRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/redis")
public class RedisController {

	@Autowired
	private RedisService redisService;

	
	@GetMapping(value = "/load/{user}")
	public ResponseEntity<String> loadData(@PathVariable String user){
		SpringBootRedisApplication.user = user;
		if(redisService.loadData("/home/vinay/ccenc.csv").contains("Done"))
			return new ResponseEntity("File Loaded!!!",HttpStatus.CREATED);
		
		return new ResponseEntity("Failed to Load Data!!!", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/{user}/all")
	public ResponseEntity<List<ccenc>> getAll(@PathVariable String user) throws IOException{
		SpringBootRedisApplication.user = user;
		return new ResponseEntity(redisService.findAll(),HttpStatus.OK);
	}
	
	@GetMapping(value = "/accountId/{user}/{accountId}")
	public ResponseEntity findByAccountId(@PathVariable String user,@PathVariable String accountId){
		SpringBootRedisApplication.user = user;
		ccenc ccEnc = redisService.findByAccountId(accountId.trim());
		if(ccEnc!=null)
			return new ResponseEntity(ccEnc,HttpStatus.FOUND);
		
		return new ResponseEntity("Record not found for this AccountId : "+accountId,HttpStatus.NOT_FOUND);
	}
}
