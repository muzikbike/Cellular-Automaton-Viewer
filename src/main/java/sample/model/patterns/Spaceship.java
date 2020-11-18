package sample.model.patterns;

import sample.model.Coordinate;
import sample.model.rules.Rule;
import sample.model.simulation.Grid;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a spaceship
 */
public class Spaceship extends Pattern {
    /**
     * The period of the spaceship
     */
    private final int period;

    /**
     * The x-displacement of the spaceship (taking moving to the right as +ve)
     */
    private final int displacementX;

    /**
     * The y-displacement of the spaceship (taking upwards as +ve)
     */
    private final int displacementY;

    /**
     * The heat of the oscillator
     */
    private double heat;

    /**
     * Phases of the spaceship
     */
    private Grid[] phases;

    /**
     * Constructs a spaceship
     * @param rule The rule the spaceship works in
     * @param pattern The pattern of the spaceship
     * @param period The period of the spaceship
     * @param displacementX The horizontal displacement of the spaceship
     * @param displacementY The vertical displacement of the spaceship
     */
    public Spaceship(Rule rule, Grid pattern, int period, int displacementX, int displacementY) {
        super(rule);

        this.period = period;
        this.displacementX = displacementX;
        this.displacementY = displacementY;
        this.insertCells(pattern, new Coordinate(0, 0));
    }

    @Override
    public void setPhases(Grid[] grids) {
        super.setPhases(grids);

        // Finding smallest phase of the spaceship
        int minPop = Integer.MAX_VALUE;
        Grid minPhase = new Grid();

        phases = new Grid[grids.length];
        for (int i = 0; i < grids.length; i++) {
            phases[i] = grids[i].deepCopy();
            if (grids[i].getPopulation() < minPop && grids[i].getBackground() == 0) minPhase = phases[i];

            // Calculating heat
            if (i > 0) {
                int finalI = i;
                grids[i].iterateCells(cell -> {
                    if (grids[finalI].getCell(cell) != grids[finalI - 1].getCell(cell)) heat++;
                });

                grids[i - 1].iterateCells(cell -> {
                    if (grids[finalI].getCell(cell) == 0 &&
                            grids[finalI].getCell(cell) != grids[finalI - 1].getCell(cell)) heat++;
                });
            }
        }

        this.clearCells();
        this.insertCells(minPhase, new Coordinate(0, 0));

        heat /= period;
    }

    @Override
    public String toString() {
        return "(" + -displacementX + "," + displacementY + ")c/" + period;
    }

    /**
     * Additional information about the spaceship.
     * Namely the period, horizontal and vertical displacement and the minimum and maximum rule.
     * @return Returns a map containing the additional information about the spaceship
     */
    @Override
    public Map<String, String> additionalInfo() {
        LinkedHashMap<String, String> information = new LinkedHashMap<>();
        information.put("Period", "" + period);
        information.put("Displacement X", "" + -displacementX);
        information.put("Displacement Y", "" + displacementY);
        information.put("Heat", String.format("%.2f", heat));

        if (minRule != null && maxRule != null) {
            information.put("Minimum Rule", "" + minRule);
            information.put("Maximum Rule", "" + maxRule);
        }

        return information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Spaceship blocks = (Spaceship) o;
        return period == blocks.period &&
                displacementX == blocks.displacementX &&
                displacementY == blocks.displacementY &&
                Arrays.equals(phases, blocks.phases);
    }

    @Override
    public int hashCode() {
        int result = (1 << period ^ displacementX << 2) ^ displacementY << 3;
        result = 31 * result + Arrays.hashCode(phases);
        return result;
    }

    /**
     * Gets the period of the spaceship
     * @return Returns the period of the spaceship
     */
    public int getPeriod() {
        return period;
    }

    /**
     * Gets the displacement of the spaceship in the x-direction
     * @return Returns the displacement of the spaceship in the x-direction
     */
    public int getDisplacementX() {
        return displacementX;
    }

    /**
     * Gets the displacement of the spaceship in the y-direction
     * @return Returns the displacement of the spaceship in the y-direction
     */
    public int getDisplacementY() {
        return displacementY;
    }
}
