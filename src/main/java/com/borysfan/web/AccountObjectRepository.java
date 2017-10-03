package com.borysfan.web;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountObjectRepository extends MongoRepository<AccountObject, String> {
    
}
