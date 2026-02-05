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

        // ---------- MOBILE VALIDATION ----------
        String mob = customer.getMob();

        if (mob == null)
            throw new InvalidMobileNumber("Mobile number is required");

        mob = mob.trim();

        if (mob.startsWith("+91"))
            mob = mob.substring(3);

        if (mob.length() != 10)
            throw new InvalidMobileNumber("Invalid Mobile Number");

        if (mob.charAt(0) < '6')
            throw new InvalidMobileNumber("Invalid Mobile Number");

        for (char ch : mob.toCharArray()) {
            if (!Character.isDigit(ch))
                throw new InvalidMobileNumber("Invalid Mobile Number");
        }

        // ---------- DUPLICATE MOBILE ----------
        Customer existing = cr.findByMob(mob);
        if (existing != null)
            throw new InvalidMobileNumber("Mobile Number Already Exists");

        customer.setMob(mob);

        // ---------- SAVE ----------
        cr.save(customer); // ID auto-generated
    }

    @Override
    public List<Customer> display() {
        return cr.findAll();
    }

    @Override
    public Customer delete(Integer id) {
        return cr.findById(id).map(c -> {
            cr.deleteById(id);
            return c;
        }).orElse(null);
    }

    @Override
    public void update(Customer customer, Integer id) {
        customer.setId(id);
        cr.save(customer);
    }

    @Override
    public Customer search(Integer id) {
        return cr.findById(id).orElse(null);
    }

    @Override
    public void addAll(List<Customer> list) {
        cr.saveAll(list);
    }

    @Override
    public Customer findBymob(String mob) {
        return cr.findByMob(mob);
    }

    @Override
    public List<Customer> duplicate() {
        return null;
    }
}
