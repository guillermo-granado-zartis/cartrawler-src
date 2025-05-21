package org.cartrawler.assessment.car;

import java.util.Comparator;

public record CarGroupKey(boolean carIsCorporate, CarResult.CarType carType) {
    public static CarGroupKey fromCarResult(CarResult carResult) {
        return new CarGroupKey(carResult.isCorporative(), carResult.getCarType());
    }

    public static Comparator<CarGroupKey> comparator() {
        return Comparator.comparing(CarGroupKey::carIsCorporate).reversed()
                .thenComparing(CarGroupKey::carType);
    }
}
