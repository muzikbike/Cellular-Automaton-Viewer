package sample.model.rules.isotropic;

import sample.model.Coordinate;

import java.util.ArrayList;

/**
 * Represents range 1 moore INT transitions (hensel notation)
 */
public class R1MooreINT extends SingleLetterTransitions {
    /**
     * Constructs the range 1 moore INT transitions with hensel notation
     * @param string The hensel notation string
     */
    public R1MooreINT(String string) {
        super(string);

        neighbourhood = new Coordinate[]{
                new Coordinate(1, -1),
                new Coordinate(1, 0),
                new Coordinate(1, 1),
                new Coordinate(0, 1),
                new Coordinate(-1, 1),
                new Coordinate(-1, 0),
                new Coordinate(-1, -1),
                new Coordinate(0, -1)
        };

        readTransitionsFromFile(getClass().getResourceAsStream("/int/r1_moore.txt"));

        parseTransitions(string);
        transitionString = canoniseTransitions();
    }

    @Override
    protected void addTransition(ArrayList<Integer> transition) {
        ArrayList<Integer> rotate1 = rotate(transition);
        ArrayList<Integer> rotate2 = rotate(rotate1);
        ArrayList<Integer> rotate3 = rotate(rotate2);

        transitionTable.add(transition);
        transitionTable.add(reflect1(transition));
        transitionTable.add(reflect2(transition));

        transitionTable.add(rotate1);
        transitionTable.add(reflect1(rotate1));
        transitionTable.add(reflect2(rotate1));

        transitionTable.add(rotate2);
        transitionTable.add(reflect1(rotate2));
        transitionTable.add(reflect2(rotate2));

        transitionTable.add(rotate3);
        transitionTable.add(reflect1(rotate3));
        transitionTable.add(reflect2(rotate3));
    }

    @Override
    protected Object clone() {
        return new R1MooreINT(transitionString);
    }

    private ArrayList<Integer> rotate(ArrayList<Integer> transition) {
        int[] rotateSymmetry = new int[]{2, 3, 4, 5, 6, 7, 0, 1};

        ArrayList<Integer> rotated = new ArrayList<>();
        for (int index: rotateSymmetry) {
            rotated.add(transition.get(index));
        }

        return rotated;
    }

    private ArrayList<Integer> reflect1(ArrayList<Integer> transition) {
        int[] reflectSymmetry = new int[]{2, 1, 0, 7, 6, 5, 4, 3};

        ArrayList<Integer> reflected = new ArrayList<>();
        for (int index: reflectSymmetry) {
            reflected.add(transition.get(index));
        }

        return reflected;
    }

    private ArrayList<Integer> reflect2(ArrayList<Integer> transition) {
        int[] reflectSymmetry = new int[]{6, 5, 4, 3, 2, 1, 0, 7};

        ArrayList<Integer> reflected = new ArrayList<>();
        for (int index: reflectSymmetry) {
            reflected.add(transition.get(index));
        }

        return reflected;
    }
}
