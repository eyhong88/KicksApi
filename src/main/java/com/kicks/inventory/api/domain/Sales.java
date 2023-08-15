package com.kicks.inventory.api.domain;

public class Sales {
    private int salesCount;
    private double salesTotal;
    private double avgSaleAmount;
    private double totalPayout;

    public Sales(){}
    public Sales(int salesCount, double salesTotal, double avgSaleAmount, double totalPayout){
        this.salesCount = salesCount;
        this.salesTotal = salesTotal;
        this.avgSaleAmount = avgSaleAmount;
        this.totalPayout = totalPayout;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }

    public double getSalesTotal() {
        return salesTotal;
    }

    public void setSalesTotal(double salesTotal) {
        this.salesTotal = salesTotal;
    }

    public double getAvgSaleAmount() {
        return avgSaleAmount;
    }

    public void setAvgSaleAmount(double avgSaleAmount) {
        this.avgSaleAmount = avgSaleAmount;
    }
    public double getTotalPayout() {
        return totalPayout;
    }

    public void setTotalPayout(double totalPayout) {
        this.totalPayout = totalPayout;
    }
}
