package com.spring.redis;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.spring.redis.entity.ccenc;
import com.spring.redis.repository.CcencRepository;
import com.spring.redis.repository.RedisRepository;

@Service
public class RedisService {
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RedisService.class);
	
	@Autowired
	private CcencRepository ccencRepository;
	
	@Autowired
	private RedisRepository soteroRedisRepository;
	
		
	public String loadData(String path){
		
		try{
			Map<String, String> mapping = new HashMap<String, String>();
			mapping.put("isProxyIP", "isProxyIP");
			mapping.put("ipPostcode", "ipPostcode");
			mapping.put("paymentBillingState", "paymentBillingState");
			mapping.put("transactionAmountUSD", "transactionAmountUSD");
			mapping.put("localHour", "localHour");
			mapping.put("transactionTime", "transactionTime");
			mapping.put("paymentInstrumentType", "paymentInstrumentType");
			mapping.put("transactionID", "transactionID");
			mapping.put("paymentBillingPostalCode", "paymentBillingPostalCode");
			mapping.put("accountID", "accountID");
			mapping.put("transactionAmount", "transactionAmount");
			mapping.put("transactionCurrencyCode", "transactionCurrencyCode");
			mapping.put("transactionIPaddress", "transactionIPaddress");
			mapping.put("cvvVerifyResult", "cvvVerifyResult");
			mapping.put("digitalItemCount", "digitalItemCount");
			mapping.put("physicalItemCount", "physicalItemCount");
			mapping.put("browserLanguage", "browserLanguage");
			mapping.put("transactionScenario", "transactionScenario");
			mapping.put("cardType", "cardType");
			mapping.put("ipCountryCode", "ipCountryCode");
			mapping.put("transactionDate", "transactionDate");
			mapping.put("transactionType", "transactionType");
			mapping.put("paymentBillingCountryCode", "paymentBillingCountryCode");
			mapping.put("ipState", "ipState");
			
			HeaderColumnNameTranslateMappingStrategy<ccenc> strategy = new HeaderColumnNameTranslateMappingStrategy<ccenc>();
			strategy.setType(ccenc.class);
			strategy.setColumnMapping(mapping);
			
			CSVReader csvReader = null;
			csvReader = new CSVReader(new FileReader(path));
		
			CsvToBean csvToBean = new CsvToBean();
			
			List<ccenc> list = csvToBean.parse(strategy, csvReader);
			ccencRepository.saveAll(list.subList(0, 1000));
	/*		int i=0;
			for(ccenc ccEnc : list){
				soteroRedisRepository.save(ccEnc);
				i++;
				
				if(i==50)
					break;
			}*/
			
			return "Done";
		}catch(FileNotFoundException e){
			LOGGER.debug(e.getMessage());
		}
		return "Failed to Load Data!!!";
	}

	public ccenc findByAccountId(String accountId) {
		ccenc ccEnc = new ccenc();
		try{
			Optional<ccenc> ccEncOp = ccencRepository.findById(accountId);
			if(ccEncOp.isPresent())
				ccEnc = ccEncOp.get();
			//ccEnc = soteroRedisRepository.findByaccountId(accountID);
		}catch(Exception e){
			 LOGGER.debug(e.getMessage());
		}
		return ccEnc;
	}


	public List<ccenc> findAll() {
		List<ccenc> ccencList = new ArrayList<ccenc>();
		try{
		   Iterable<ccenc> ccencIterable = ccencRepository.findAll();
		   ccencIterable.forEach(ccencList::add);
			//ccencList.addAll(soteroRedisRepository.getAll().values());
			 
		}catch(Exception e){
			 LOGGER.debug(e.getMessage());
	    }
	
		return ccencList.subList(0, 100);
	}


}
