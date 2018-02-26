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
            fitness += individual.genes[i];
        }
        return fitness;
    }

    //Calculate fitness for x^2 - 11x + 30 = 0
    public double calcFitness(Individual individual) {
        double value = (individual.getValue() * individual.getValue()) - 11 * individual.getValue() + 30;
        double fitness = (1/(1+Math.abs(value)));
        return fitness;
    }
}
