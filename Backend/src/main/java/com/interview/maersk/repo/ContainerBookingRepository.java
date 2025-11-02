package com.interview.maersk.repo;

import com.interview.maersk.entity.ContainerBookingEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerBookingRepository extends ReactiveMongoRepository<ContainerBookingEntity, String> {
}
