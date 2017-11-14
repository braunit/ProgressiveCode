package com.progressive.code.starter.service;

import java.util.List;

import com.progressive.code.starter.domain.Customer;

public interface CustomerService {

	Customer findOne(Long id);

	List<Customer> findByFirstNameAndLastName(String firstName, String lastName);

}