package org.cartrawler.assessment.app;

import org.cartrawler.assessment.car.CarRepository;
import org.cartrawler.assessment.car.CarResult;
import org.cartrawler.assessment.view.Display;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedHashSet;
import java.util.Set;

public class AssessmentProcessTest {

    @Test
    void GivenEmptyCarResultsWhenListThenNothingIsRendered() {
        Set<CarResult> data = new LinkedHashSet<>();

        CarRepository repository = new CarRepository(data);
        Display spyDisplay = Mockito.spy(new Display());
        AssessmentProcess process = new AssessmentProcess(repository, spyDisplay);
        process.run();

        Set<CarResult> expectedSet = new LinkedHashSet<>();
        Mockito.verify(spyDisplay).render(expectedSet);
    }

    @Test
    void GivenOneCarResultsWhenListThenOneIsRendered() {
        var carResult1 = new CarResult("Volkswagen Polo", "NIZA", "EDMR", 12.81d, CarResult.FuelPolicy.FULLEMPTY);
        Set<CarResult> data = new LinkedHashSet<>();
        data.add(carResult1);

        CarRepository repository = new CarRepository(data);
        Display spyDisplay = Mockito.spy(new Display());
        AssessmentProcess process = new AssessmentProcess(repository, spyDisplay);
        process.run();

        Set<CarResult> expectedSet = new LinkedHashSet<>();
        expectedSet.add(carResult1);
        Mockito.verify(spyDisplay).render(expectedSet);
    }

    @Test
    void GivenDuplicatedCarResultsWhenListThenOnlyOneIsRendered() {
        var carResult1 = new CarResult("Volkswagen Polo", "NIZA", "EDMR", 12.81d, CarResult.FuelPolicy.FULLEMPTY);
        var carResult2 = new CarResult("Volkswagen Polo", "NIZA", "EDMR", 22.04d, CarResult.FuelPolicy.FULLEMPTY);

        Set<CarResult> data = new LinkedHashSet<>();
        data.add(carResult1);
        data.add(carResult2);

        CarRepository repository = new CarRepository(data);
        Display spyDisplay = Mockito.spy(new Display());
        AssessmentProcess process = new AssessmentProcess(repository, spyDisplay);
        process.run();

        Set<CarResult> expectedSet = new LinkedHashSet<>();
        expectedSet.add(carResult1);
        Mockito.verify(spyDisplay).render(expectedSet);
    }

    @Test
    void GivenUnsortedCarResultsWhenListThenAllSortedAreRendered() {
        var nocorporateEconomyCarResult = new CarResult("Volkswagen Polo", "NIZA", "EDMR", 12.81d, CarResult.FuelPolicy.FULLEMPTY);
        var nocorporateOtherCarResult = new CarResult("Volkswagen Polo", "DELPASO", "PDMR", 12.81d, CarResult.FuelPolicy.FULLEMPTY);
        var nocorporateOtherCheaperCarResult = new CarResult("Volkswagen Polo", "RHODIUM", "PDMR", 8.81d, CarResult.FuelPolicy.FULLEMPTY);
        var corporateOtherCarResult = new CarResult("Mercedes E Class", "AVIS", "PDAR", 948.24d, CarResult.FuelPolicy.FULLEMPTY);
        var corporateMiniCarResult = new CarResult("Smart Fortwo", "SIXT", "MUMR", 115.76d, CarResult.FuelPolicy.FULLEMPTY);
        var corporateEconomyCarResult = new CarResult("Smart Forone", "BUDGET", "EUMR", 115.76d, CarResult.FuelPolicy.FULLEMPTY);
        var corporateCompactCarResult = new CarResult("Smart Fortwo", "THRIFTY", "CUMR", 115.76d, CarResult.FuelPolicy.FULLEMPTY);
        var corporateCompactCheaperCarResult = new CarResult("Smart Forthree", "FIREFLY", "CUMR", 90.76d, CarResult.FuelPolicy.FULLEMPTY);

        Set<CarResult> data = new LinkedHashSet<>();
        data.add(nocorporateOtherCheaperCarResult);
        data.add(corporateCompactCarResult);
        data.add(corporateEconomyCarResult);
        data.add(nocorporateOtherCarResult);
        data.add(nocorporateEconomyCarResult);
        data.add(corporateOtherCarResult);
        data.add(corporateMiniCarResult);
        data.add(corporateCompactCheaperCarResult);

        CarRepository repository = new CarRepository(data);
        Display spyDisplay = Mockito.spy(new Display());
        AssessmentProcess process = new AssessmentProcess(repository, spyDisplay);
        process.run();

        Set<CarResult> expectedSet = new LinkedHashSet<>();
        expectedSet.add(corporateMiniCarResult);
        expectedSet.add(corporateEconomyCarResult);
        expectedSet.add(corporateCompactCheaperCarResult);
        expectedSet.add(corporateCompactCarResult);
        expectedSet.add(corporateOtherCarResult);
        expectedSet.add(nocorporateEconomyCarResult);
        expectedSet.add(nocorporateOtherCheaperCarResult);
        expectedSet.add(nocorporateOtherCarResult);
        Mockito.verify(spyDisplay).render(expectedSet);
    }

    @Test
    void GivenThreeCarResultsForOneGroupWhenListThenOnlyFuelMedianPriceValidatedAreRendered() {
        var corporateMiniFullAboveMedianPriceCarResult = new CarResult("Smart Forone", "SIXT", "MUMR", 115.0d, CarResult.FuelPolicy.FULLFULL);
        var corporateMiniFullEqualMedianPriceCarResult = new CarResult("Smart Fortwo", "SIXT", "MUMR", 100.0d, CarResult.FuelPolicy.FULLFULL);
        var corporateMiniFullBelowMedianPriceCarResult = new CarResult("Smart Forthree", "SIXT", "MUMR", 90.0d, CarResult.FuelPolicy.FULLFULL);

        Set<CarResult> data = new LinkedHashSet<>();
        data.add(corporateMiniFullAboveMedianPriceCarResult);
        data.add(corporateMiniFullEqualMedianPriceCarResult);
        data.add(corporateMiniFullBelowMedianPriceCarResult);

        CarRepository repository = new CarRepository(data);
        Display spyDisplay = Mockito.spy(new Display());
        AssessmentProcess process = new AssessmentProcess(repository, spyDisplay);
        process.run();

        Set<CarResult> expectedSet = new LinkedHashSet<>();
        expectedSet.add(corporateMiniFullBelowMedianPriceCarResult);
        expectedSet.add(corporateMiniFullEqualMedianPriceCarResult);
        Mockito.verify(spyDisplay).render(expectedSet);
    }

    @Test
    void GivenTwoCarResultsForOneGroupWhenListThenOnlyFuelMedianPriceValidatedAreRendered() {
        var corporateMiniFullBelowMedianPriceCarResult = new CarResult("Smart Forone", "SIXT", "MUMR", 90.0d, CarResult.FuelPolicy.FULLFULL);
        var corporateMiniEmptyAboveMedianPriceCarResult = new CarResult("Smart Fortwo", "SIXT", "MUMR", 92.0d, CarResult.FuelPolicy.FULLEMPTY);

        Set<CarResult> data = new LinkedHashSet<>();
        data.add(corporateMiniFullBelowMedianPriceCarResult);
        data.add(corporateMiniEmptyAboveMedianPriceCarResult);

        CarRepository repository = new CarRepository(data);
        Display spyDisplay = Mockito.spy(new Display());
        AssessmentProcess process = new AssessmentProcess(repository, spyDisplay);
        process.run();

        Set<CarResult> expectedSet = new LinkedHashSet<>();
        expectedSet.add(corporateMiniFullBelowMedianPriceCarResult);
        expectedSet.add(corporateMiniEmptyAboveMedianPriceCarResult);
        Mockito.verify(spyDisplay).render(expectedSet);
    }
}
