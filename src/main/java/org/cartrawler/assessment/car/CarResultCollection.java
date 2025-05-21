package org.cartrawler.assessment.car;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CarResultCollection {
    public static Collector<CarResult, ?, Map<CarGroupKey, CarResultCollection>> groupByCarGroupKey() {
        return Collectors.groupingBy(
                CarGroupKey::fromCarResult,
                () -> new TreeMap<>(CarGroupKey.comparator()),
                Collectors.collectingAndThen(
                        Collectors.toList(), CarResultCollection::new));
    }

    private final List<CarResult> carResults;

    public CarResultCollection(Collection<CarResult> carResults) {
        this.carResults = carResults.stream()
                .sorted(Comparator.comparing(CarResult::getRentalCost))
                .toList();
    }

    public Collection<CarResult> getCarResults() {
        var medianPrice = calculateMedianPrice();
        return carResults.stream()
                .filter(carResult ->
                        carResult.validateFuelPolicyWithMedianPrice(medianPrice))
                .toList();
    }

    private double calculateMedianPrice() {
        int size = carResults.size();
        if (size == 0) {
            return 0;
        }
        if (size % 2 == 1) {
            return carResults.get(size/2).getRentalCost();
        }
        return (carResults.get(size/2-1).getRentalCost() + carResults.get(size/2).getRentalCost()) / 2.0;
    }
}
