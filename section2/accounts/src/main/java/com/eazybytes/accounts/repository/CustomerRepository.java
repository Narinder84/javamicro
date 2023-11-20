package com.eazybytes.accounts.repository;

import com.eazybytes.accounts.entity.Customer;
import org.hibernate.validator.spi.nodenameprovider.JavaBeanProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
