package com.progressive.code.starter.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.progressive.code.starter.annotation.LogAccess;
import com.progressive.code.starter.annotation.LogValue;
import com.progressive.code.starter.domain.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Override
	@LogAccess
	public Customer findOne(@LogValue("id") Long id) {
		return getDefaultCustomer();
	}

	@Override
	@LogAccess
	public List<Customer> findByFirstNameAndLastName(@LogValue("firstName") String firstName, @LogValue("lastName") String lastName) {
		return Arrays.asList(getDefaultCustomer());
	}

	private Customer getDefaultCustomer() {
		Customer customer = new Customer("First", "Last");
		customer.setId(1L);
		return customer;
	}

}