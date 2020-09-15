package sample.controller.dialogs.rule;

import org.javatuples.Pair;
import sample.controller.NeighbourhoodSelector;
import sample.model.Coordinate;
import sample.model.rules.IntegerHROT;

public class IntegerHROTDialog extends RuleWidget {
    private final NeighbourhoodSelector neighbourhoodSelector;

    public IntegerHROTDialog() {
        super();

        neighbourhoodSelector = SharedWidgets.getNeighbourhoodSelector();
        super.add(neighbourhoodSelector, 0, 1);

        this.ruleFamily = new IntegerHROT();
    }

    @Override
    public void updateRule(String rulestring) {
        ruleFamily.setRulestring(rulestring);

        Pair<Coordinate[], int[]> neighbourhoodAndWeights = neighbourhoodSelector.getNeighbourhoodAndWeights();
        if (neighbourhoodAndWeights == null)
            return;

        if (neighbourhoodAndWeights.getValue0() != null) {
            ((IntegerHROT) ruleFamily).setNeighbourhood(neighbourhoodAndWeights.getValue0());
            if (neighbourhoodAndWeights.getValue1() != null) {
                ((IntegerHROT) ruleFamily).setWeights(neighbourhoodAndWeights.getValue1());
            }
        }
    }
}
