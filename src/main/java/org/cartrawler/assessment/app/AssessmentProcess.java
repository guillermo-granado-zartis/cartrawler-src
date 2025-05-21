package org.cartrawler.assessment.app;

import org.cartrawler.assessment.car.CarGroupKey;
import org.cartrawler.assessment.car.CarRepository;
import org.cartrawler.assessment.car.CarResultCollection;
import org.cartrawler.assessment.view.Display;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AssessmentProcess {
    private final CarRepository repository;
    private final Display display;

    public AssessmentProcess(CarRepository repository, Display display) {
        this.repository = repository;
        this.display = display;
    }

    public void run() {
        var nonDuplicatedCars = repository.getUniqueCarResultsByIdentifier();

        var groupedNonDuplicatedCars = nonDuplicatedCars.stream()
                .collect(CarResultCollection.groupByCarGroupKey())
                .values().stream()
                .map(CarResultCollection::getCarResults)
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        display.render(groupedNonDuplicatedCars);
    }
}
