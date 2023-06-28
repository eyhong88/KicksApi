package com.kicks.inventory.api.controller;

import com.kicks.inventory.api.Shoe;
import com.kicks.inventory.api.ShoeSale;
import com.kicks.inventory.api.dao.ShoesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(value = "/inventory")
public class ShoesController {

    @Autowired
    private ShoesDAO dao;

    @GetMapping("/shoes")
    public ResponseEntity<List<Shoe>> loadShoes(){
        return new ResponseEntity<>(dao.loadShoes(), HttpStatus.OK);
    }

    @PostMapping("/shoes")
    public ResponseEntity<Shoe> addShoe(@RequestBody Shoe shoe){
        try {
            dao.addShoe(shoe);
            return new ResponseEntity<>(shoe, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(shoe, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/shoes/sell")
    public ResponseEntity<ShoeSale> addShoe(@RequestBody ShoeSale sale){
        try {
            dao.addShoeSale(sale);
            return new ResponseEntity<>(sale, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(sale, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/shoes")
    public ResponseEntity<String> updateShoe(@RequestBody Shoe shoe){

        dao.updateShoe(shoe);
        return ResponseEntity.ok("updated.");

    }

    @GetMapping("/shoes/{sku}")
    public ResponseEntity<Shoe> getShoe(@PathVariable String sku){

        Shoe shoe = dao.getShoe(sku);
        return ResponseEntity.ok(shoe);
    }
}
