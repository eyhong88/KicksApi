package com.kicks.inventory.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kicks.inventory.api.domain.Sales;
import com.kicks.inventory.api.domain.Shoe;
import com.kicks.inventory.api.domain.ShoeSale;
import com.kicks.inventory.api.dao.ShoesDAO;
import com.kicks.inventory.api.domain.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/inventory")
public class ShoesController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ShoesDAO dao;

    @GetMapping("/shoes")
    public ResponseEntity<List<Shoe>> loadShoes(@RequestParam(required = false) String brand,
                                                @RequestParam(required = false) String styleCode,
                                                @RequestParam(required = false) String colorWay,
                                                @RequestParam(required = false) String model){
        List<Shoe> result = dao.loadShoes();

        if(null != brand && !brand.isEmpty())
            result = result.stream().filter(shoe -> shoe.getBrand().equalsIgnoreCase(brand)).collect(Collectors.toList());

        if(null != styleCode && !styleCode.isEmpty())
            result = result.stream().filter(shoe -> shoe.getStyleCode().equalsIgnoreCase(styleCode)).collect(Collectors.toList());

        if(null != model && !model.isEmpty())
            result = result.stream().filter(shoe -> shoe.getModel().toLowerCase().contains(model.toLowerCase())).collect(Collectors.toList());

        if(null != colorWay && !colorWay.isEmpty())
            result = result.stream().filter(shoe -> shoe.getColorway().toLowerCase().contains(colorWay.toLowerCase())).collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/shoes")
    public ResponseEntity<Shoe> addShoe(@RequestBody Shoe shoe){
        System.out.println("Inside addShoe for " + shoe.getSku());
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
        System.out.println("Inside addShoeSale for " + sale.getSku());
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
        System.out.println("Inside updateShoe for " + shoe.getSku());

        dao.updateShoe(shoe);
        return new ResponseEntity<>(shoe, HttpStatus.OK);

    }

    @GetMapping("/shoes/{sku}")
    public ResponseEntity<Shoe> getShoe(@PathVariable String sku){
        System.out.println("Inside getShoe for " + sku);

        Shoe shoe = dao.getShoe(sku);
        return ResponseEntity.ok(shoe);
    }


    @GetMapping("/shoes/vendors")
    public ResponseEntity<List<Vendor>> getVendors(){
        System.out.println("Inside getVendors");

        List<Vendor> vendors = dao.getVendors();
        return ResponseEntity.ok(vendors);
    }
    @GetMapping("/shoes/sold")
    public ResponseEntity<List<ShoeSale>> getShoeSales(){

        List<ShoeSale> shoeSales = dao.getShoeSales();
        return new ResponseEntity<>(shoeSales, HttpStatus.OK);
    }

    @GetMapping("/shoes/sold/totals")
    public ResponseEntity<Sales> getSalesTotal()  {
        System.out.println("Inside getSalesTotal");

        List<ShoeSale> shoeSales = dao.getShoeSales();
        int count = shoeSales.size();
        double totalSales = shoeSales.stream().mapToDouble(ShoeSale::getSalePrice).sum();
        double avgSalesAmt = totalSales / count;
        double totalPayout = shoeSales.stream().mapToDouble(ShoeSale::getTotalPayout).sum();

        return new ResponseEntity<>(new Sales(count, totalSales, avgSalesAmt, totalPayout), HttpStatus.OK);
    }
}
