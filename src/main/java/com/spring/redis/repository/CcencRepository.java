package com.spring.redis.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spring.redis.entity.ccenc;

@Repository
public interface CcencRepository extends CrudRepository<ccenc, String> {

	ccenc findByaccountIDIgnoreCase(String accountID);

	ccenc findBytransactionID(String transactionID);

	List<ccenc> findBypaymentInstrumentType(String paymentInstrumentType);

	List<ccenc> findBycardType(String cardType);

}
