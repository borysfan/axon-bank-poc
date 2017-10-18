package com.borysfan.mortage;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MortageObjectRepository extends MongoRepository<MortageObject, String> {
}
