package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {

         Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

         Optional<Customer> isCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
         if(isCustomer.isPresent()){

             throw new CustomerAlreadyExistsException("Customer already exist with given phone number" + customerDto.getMobileNumber());

         }
         customer.setCreatedAt(LocalDateTime.now());
         customer.setCreatedBy("Any One");
         Customer savedCustomer  = customerRepository.save(customer);

         accountRepository.save(createNewAccount(savedCustomer));

    }

    private Accounts  createNewAccount(Customer customer){

        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }


}
