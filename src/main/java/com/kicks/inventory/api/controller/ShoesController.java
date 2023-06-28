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
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/inventory")
public class ShoesController {

    @Autowired
    private ShoesDAO dao;

    @GetMapping("/shoes")
    public ResponseEntity<List<Shoe>> loadShoes(@RequestParam(required = false) String brand){
        List<Shoe> result = dao.loadShoes();

        if(null != brand && !brand.isEmpty())
            result = result.stream().filter(shoe -> shoe.getBrand().equalsIgnoreCase(brand)).collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
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
    public ResponseEntity<ShoeSale> addShoeSale(@RequestBody ShoeSale sale){
        try {
            dao.addShoeSale(sale);
            return new ResponseEntity<>(sale, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(sale, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/shoes")
    public ResponseEntity<Shoe> updateShoe(@RequestBody Shoe shoe){

        dao.updateShoe(shoe);
        return new ResponseEntity<>(shoe, HttpStatus.OK);

    }

    @GetMapping("/shoes/{sku}")
    public ResponseEntity<Shoe> getShoe(@PathVariable String sku){

        Shoe shoe = dao.getShoe(sku);
        return ResponseEntity.ok(shoe);
    }
}
