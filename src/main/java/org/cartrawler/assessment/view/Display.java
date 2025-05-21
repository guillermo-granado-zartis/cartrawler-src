package org.cartrawler.assessment.view;

import de.vandermeer.asciitable.AsciiTable;
import org.cartrawler.assessment.car.CarResult;

import java.util.Set;

public class Display {
    public void render(Set<CarResult> cars) {
        if (cars.isEmpty()) {
            return;
        }
        AsciiTable table = new AsciiTable();
        for (CarResult car : cars) {
            table.addRule();
            table.addRow(car.toRow());
        }
        table.addRule();
        System.out.println(table.render());
    }
}
