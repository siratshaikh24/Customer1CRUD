package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;

@RestController
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private CustomerService cs;

    // Welcome API
    @GetMapping
    public String welcome() {
        return "I am Sirat";
    }

    // Add single customer
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Customer c) {
        cs.add(c);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer added");
    }

    // Add multiple customers
    @PostMapping("/add-all")
    public ResponseEntity<String> addAll(@RequestBody List<Customer> list) {
        cs.addAll(list);
        return ResponseEntity.status(HttpStatus.CREATED).body("All customers added");
    }

    // Display all customers
    @GetMapping("/display")
    public List<Customer> display() {
        return cs.display();
    }

    // Delete customer by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Customer temp = cs.delete(id);
        if (temp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer not found");
        }
        return ResponseEntity.ok(temp);
    }

    // Search by id
    @GetMapping("/search/id/{id}")
    public ResponseEntity<?> searchById(@PathVariable Integer id) {
        Customer c = cs.search(id);
        if (c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer not found");
        }
        return ResponseEntity.ok(c);
    }

    // Search by mobile number
    @GetMapping("/search/mob/{mob}")
    public ResponseEntity<?> searchByMob(@PathVariable String mob) {
        Customer c = cs.findBymob(mob);
        if (c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer not found");
        }
        return ResponseEntity.ok(c);
    }
}
