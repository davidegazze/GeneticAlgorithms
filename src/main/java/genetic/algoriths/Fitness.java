package genetic.algoriths;

// Class for calculating the fitness
public class Fitness {

    //Calculate fitness
    public double calcFitnessOld(Individual individual) {
        double fitness = 0;
        for (int i = 0; i < individual.getNumberOfGenes(); i++) {
            /*
            if (individual.genes[i] == 1) {
                ++fitness;
            }
            */
            fitness += individual.getGeneValue(i);
        }
        return fitness;
    }

    // Calculate fitness for x^2 - 12x + 35.75 = 0
    // Solution 5.5 and 6.5
    public double calcFitness(Individual individual) {
        double value = (individual.getValue() * individual.getValue()) - 12 * individual.getValue() + 35.75;
        double fitness = (1/(1+Math.abs(value)));
        return fitness;
    }
}
