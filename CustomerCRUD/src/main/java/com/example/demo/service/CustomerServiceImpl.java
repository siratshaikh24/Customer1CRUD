package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.InvalidId;
import com.example.demo.exception.InvalidMobileNumber;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	
	@Autowired
	private CustomerRepository cr;
	
	@Override
	public void add(Customer customer) {
		// TODO Auto-generated method 
		String mob = customer.getMob();
		
		if(mob != null) {
			mob.trim();
			if (mob.startsWith("+91"))
			    mob = mob.substring(3);			
		}
	
	
	if (mob.length() == 10) {
		if (mob.charAt(0) == '0' || mob.charAt(0) == '1' || mob.charAt(0) == '2' || mob.charAt(0) == '3'
				|| mob.charAt(0) == '4' || mob.charAt(0) == '5')
			throw new InvalidMobileNumber("Invalid Mobile Number");

		for (int i = 0; i < mob.length(); i++) {
			if (!Character.isDigit(mob.charAt(i)))
				throw new InvalidMobileNumber("Invalid Mobile Number");
		}

		Customer existing = cr.findByMob(mob);
		if (existing != null)
		    throw new InvalidMobileNumber("Mobile Number Already Exist");


		} else
			throw new InvalidMobileNumber("Invalid Mobile Number");

		customer.setMob(mob);
	
	
		Integer id=customer.getId();
		
		if(id <= 0) {
			throw new InvalidId("Invalid ID");
		}
		List<Customer> list=cr.findAll();
		
		for(Customer customer1:list) {
			if(id.equals(customer1.getId())) {
				throw new InvalidId("Id Already Exists !!!");
				
			}
			
		}
	
		cr.save(customer); // save = insert	
	
	}

	

	@Override
	public List<Customer> display() {
		// TODO Auto-generated method stub
		return cr.findAll();  // Select * from Customer;
	}

	@Override
	public Customer delete(Integer id) {
		// TODO Auto-generated method stub
		
		// Search
		if (cr.findById(id).isPresent()) {
			Customer temp = cr.findById(id).get();
			cr.deleteById(id);   // Delete
			return temp;
			
		}
	
		return null;
	}

	@Override
	public void update(Customer customer, Integer id) {
		// TODO Auto-generated method stub
		customer.setId(id);
		cr.save(customer);
		
	}

	@Override
	public Customer search(Integer id) {
		// TODO Auto-generated method stub		
	
		if (cr.findById(id).isPresent()) {
			Customer temp = cr.findById(id).get();
			return temp;
			
		}	
			
		return null;
	}
	
	@Override
	public void addAll(List<Customer> list) {
		
		cr.saveAll(list);
	
	}

	
	@Override
	public Customer findBymob(String mob) {
		// TODO Auto-generated method stub
		
		return cr.findByMob(mob);
			
		
	}


	@Override
	public List<Customer> duplicate() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
