package sample.model.rules.isotropic.rules;

import sample.model.Coordinate;
import sample.model.Utils;
import sample.model.rules.ApgtableGeneratable;
import sample.model.rules.isotropic.transitions.INTTransitions;
import sample.model.rules.ruleloader.RuleDirective;
import sample.model.rules.ruleloader.ruletable.Ruletable;

import java.util.ArrayList;

/**
 * Implements 2-state isotropic non-totalistic (INT) rules
 */
public class INT extends BaseINT implements ApgtableGeneratable {
    /**
     * The birth transitions of the INT rule
     */
    protected INTTransitions birth;

    /**
     * The survival transitions of the INT rule
     */
    protected INTTransitions survival;

    private static String[] transitionRegexes;
    private static String[] regexes;

    /**
     * Constructs an INT rule with the rule LeapLife
     */
    public INT() {
        this("B2n3/S23-q");
    }

    /**
     * Creates a 2-state INT rule with the given rulestring
     * @param rulestring The rulestring of the 2-state INT rule to be created
     * @throws IllegalArgumentException Thrown if the rulestring is invalid
     */
    public INT(String rulestring) {
        super();

        // Initialise variables
        numStates = 2;
        alternatingPeriod = 1;
        name = "INT";

        // Generating regexes
        regexes = new String[neighbourhoodLookup.size()];
        transitionRegexes = new String[neighbourhoodLookup.size()];

        int counter = 0;
        for (String string: neighbourhoodLookup.keySet()) {
            transitionRegexes[counter] = "(" + neighbourhoodLookup.get(string).getRegex() + ")*";
            if (string.equals("M")) {
                regexes[counter] = "[BbSs]" + transitionRegexes[counter] + "[_/]?[BbSs]" + transitionRegexes[counter];
            } else {
                regexes[counter] = "[BbSs]" + transitionRegexes[counter] + "[_/]?[BbSs]" + transitionRegexes[counter] +
                        "([_/]N)?" + string;
            }
            counter++;
        }

        // Load rulestring
        setRulestring(rulestring);
    }

    /**
     * Loads the rule's parameters from a rulestring
     * @param rulestring The rulestring of the INT rule (eg. B2n3/S23-q, B3/S23/NK)
     * @throws IllegalArgumentException Thrown if an invalid rulestring is passed in
     */
    @Override
    protected void fromRulestring(String rulestring) throws IllegalArgumentException {
        boolean matched = false;
        for (int i = 0; i < regexes.length; i++) {
            if (rulestring.matches(regexes[i])) {
                birth = getINTTransition(rulestring);
                birth.setTransitionString(Utils.matchRegex("[Bb](" + transitionRegexes[i] + ")",
                        rulestring, 0, 1));

                survival = getINTTransition(rulestring);
                survival.setTransitionString(Utils.matchRegex("[Ss](" + transitionRegexes[i] + ")",
                        rulestring, 0, 1));

                matched = true;
            }
        }

        if (matched) updateBackground();
        else throw new IllegalArgumentException("This rulestring is invalid!");
    }

    /**
     * Canonises the inputted rulestring with the currently loaded parameters.
     * @param rulestring The rulestring to canonised
     * @return Canonised rulestring
     */
    @Override
    public String canonise(String rulestring) {
        if (!neighbourhoodString.equals(""))
            return "B" + birth.canoniseTransitions() + "/S" + survival.canoniseTransitions() +
                    "/N" + neighbourhoodString;
        else
            return "B" + birth.canoniseTransitions() + "/S" + survival.canoniseTransitions();
    }

    /**
     * The regexes that will match a valid rulestring
     * @return An array of regexes that will match a valid rulestring
     */
    @Override
    public String[] getRegex() {
        return regexes;
    }

    /**
     * Returns a plain text description of the 2-state INT rule family to be displayed in the Rule Dialog
     * @return Description of the 2-state INT rule family
     */
    @Override
    public String getDescription() {
        return "This implements 2-state Isotropic Non-Totalistic (INT) rules.\n" +
                "B0 rules are supported via emulation with alternating rules.\n\n" +
                "The format is as follows:\n" +
                "B<birth>/S<survival>/N<neighbourhood>";
    }

    /**
     * Generates an apgtable for apgsearch to use
     * @return True if the operation was successful, false otherwise
     */
    @Override
    public RuleDirective[] generateApgtable() {
        // Generating the ruletable
        Ruletable ruletable = new Ruletable("");
        ruletable.setNumStates(2);

        ruletable.setNeighbourhood(birth.getNeighbourhood());

        ruletable.addVariable(Ruletable.ANY);

        // Birth and survival transitions
        ruletable.addINTTransitions(birth, "0", "1", "0", "1");
        ruletable.addINTTransitions(survival, "1", "1", "0", "1");

        // Death transitions
        ruletable.addOTTransition(0, "1", "0", "any", "0");

        return new RuleDirective[]{ruletable};
    }

    /**
     * Sets the birth conditions of the INT rule
     * @param birth The birth conditions
     */
    public void setBirth(INTTransitions birth) {
        this.birth = birth;
    }

    /**
     * Sets the survival conditions of the INT rule
     * @param survival The survival conditions
     */
    public void setSurvival(INTTransitions survival) {
        this.survival = survival;
    }

    /**
     * Gets the birth conditions of the INT rule
     * @return The birth conditions
     */
    public INTTransitions getBirth() {
        return birth;
    }

    /**
     * Gets the survival conditions of the INT rule
     * @return The survival conditions
     */
    public INTTransitions getSurvival() {
        return survival;
    }

    @Override
    public Object clone() {
        return new INT(rulestring);
    }

    @Override
    public Coordinate[] getNeighbourhood(int generation) {
        return birth.getNeighbourhood();
    }

    @Override
    public int transitionFunc(int[] neighbours, int cellState, int generations, Coordinate coordinate) {
        if (cellState == 0 && birth.checkTransition(neighbours)) {
            return 1;
        }
        else if (cellState == 1 && survival.checkTransition(neighbours)) {
            return 1;
        }

        return 0;
    }
}
