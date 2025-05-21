package org.cartrawler.assessment.car;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CarRepository {
    private final Set<CarResult> cars;

    public CarRepository(Set<CarResult> cars) {
        this.cars = cars;
    }

    public Set<CarResult> getUniqueCarResultsByIdentifier() {
        return cars.stream().collect(
                Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(CarResult::getIdentifier))));
    }
}
