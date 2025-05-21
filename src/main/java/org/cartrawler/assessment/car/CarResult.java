package org.cartrawler.assessment.car;

import java.util.List;

public class CarResult {
    private final String description;
    private final String supplierName;
    private final String sippCode;
    private final double rentalCost;
    private final FuelPolicy fuelPolicy;
    
	public enum FuelPolicy {
        FULLFULL,
        FULLEMPTY
    }

    public enum CarType {
        MINI,
        ECONOMY,
        COMPACT,
        OTHER;

        static CarType bySIPP(String sippCode) {
            return switch (sippCode.charAt(0)) {
                case 'M' -> MINI;
                case 'E' -> ECONOMY;
                case 'C' -> COMPACT;
                default -> OTHER;
            };
        }
    }
    
    public CarResult(String description, String supplierName, String sipp, double cost, FuelPolicy fuelPolicy) {
        this.description = description;
        this.supplierName = supplierName;
        this.sippCode = sipp;
        this.rentalCost = cost;
        this.fuelPolicy = fuelPolicy;
    }
    
    public String getDescription() {
        return this.description;        
    }
    
    public String getSupplierName() {
        return this.supplierName;        
    }
    
    public String getSippCode() {
        return this.sippCode;        
    }
    
    public double getRentalCost() {
        return this.rentalCost;        
    }
    
    public FuelPolicy getFuelPolicy() {
        return this.fuelPolicy;
    }

    public String getIdentifier() {
        return this.description + "," +
                this.supplierName + "," +
                this.sippCode + " : " +
                this.fuelPolicy;
    }

    public boolean isCorporative() {
        var corporativeSuppliers = List.of("AVIS", "BUDGET", "ENTERPRISE", "FIREFLY", "HERTZ", "SIXT", "THRIFTY");
        return corporativeSuppliers.contains(this.supplierName);
    }

    public CarType getCarType() {
        return CarType.bySIPP(this.sippCode);
    }

    public boolean validateFuelPolicyWithMedianPrice(double medianPrice) {
        return getFuelPolicy().equals(FuelPolicy.FULLEMPTY) || getRentalCost() <= medianPrice;
    }

    public List<String> toRow() {
        return List.of(
                this.getSupplierName(),
                this.getDescription(),
                this.getSippCode(),
                String.valueOf(this.getRentalCost()),
                this.getFuelPolicy().toString());
    }
    
    public String toString() {
        return this.supplierName + " : " +
            this.description + " : " +
            this.sippCode + " : " +
            this.rentalCost + " : " +
            this.fuelPolicy;
    }
}
