package genetic.algoriths;

import java.util.Random;

//Population class
class Population {

    private int popSize = 10;
    private int numGenes = 10;
    private double mutationProbability = 10;
    private Individual[] individuals = null;
    private Fitness fitnessCalculator = new Fitness();
    private int bestIndividualIndex = 0;
    private int worstIndividualIndex = 0;
    private int secondIndividualIndex = 0;
    private Random rn = new Random();
    private double populationMeanFitness = 0.0;

    public Population(int popSize, int numGenes, double mutationProbability) {
        this.popSize = popSize;
        this.numGenes = numGenes;
        this.mutationProbability = mutationProbability;
        this.individuals = new Individual[this.popSize];
        double fitness = 0;
        for (int i = 0; i < this.popSize; i++) {
            Individual individual = new Individual(this.numGenes);
            fitness = fitnessCalculator.calcFitness(individual);
            individual.setFitness(fitness);
            individuals[i] = individual;
        }
        this.scorePopulation();
    }

    public void nextGeneration() {
        // Create a new gemeration
        Random rn = new Random();
        populationMeanFitness = 0;
        // Do crossover
        crossover();
        // Do mutation under a random probability
        mutation();
        // Calculate fitness of population
        this.addFittestOffspring();
        this.scorePopulation();
    }

    // Crossover
    private void crossover() {
        // Select a random crossover point
        int crossOverPoint = rn.nextInt(this.numGenes);
        // Swap values among parents
        for (int i = 0; i < crossOverPoint; i++) {
            int temp = this.getFittest().getGeneValue(i);
            this.getFittest().setGeneValue(i, this.getSecondFittest().getGeneValue(i));
            this.getSecondFittest().setGeneValue(i, temp);
        }
        this.updateIndividual(this.getFittest());
        this.updateIndividual(this.getSecondFittest());
    }

    // Mutation by Flip
    private void mutation() {
        // Select a random mutation point
        for(int i=0; i<this.popSize; i++) {
            for (int j = 0; j < this.numGenes; j++) {
                double mutation = rn.nextDouble() * 100;
                if (mutation < this.mutationProbability) {
                    byte newGene = 0;
                    if (this.individuals[i].getGeneValue(j) == 0)
                        newGene = 1;
                    else
                        newGene = 0;
                    this.individuals[i].setGeneValue(j, newGene);
                }
            }
            this.updateIndividual(individuals[i]);
        }
    }

    private void inversion(Individual individual) {
        individual.inverseGenotype();
        individual.updateIndividual();
    }

    private void updateIndividual(Individual individual) {
        // Update Value and Fitness
        individual.updateIndividual();
        individual.setFitness(fitnessCalculator.calcFitness(individual));
    }

    //Get fittest offspring
    private Individual getFittestOffspring() {
        if (this.getFittest().getFitness() > this.getSecondFittest().getFitness()) {
            return this.getFittest();
        }
        return this.getSecondFittest();
    }

    // Replace least fittest individual from most fittest offspring
    private void addFittestOffspring() {
        // Replace least fittest individual from most fittest offspring
        this.individuals[this.worstIndividualIndex] = getFittestOffspring().clone();
    }

    private void scorePopulation()  {
        double bestFitness = 0;
        double worstFitness = 0;
        double secondFitness = 0;
        double fitness = 0;
        this.populationMeanFitness = 0;
        for (int i = 0; i < this.popSize; i++) {
            fitness = individuals[i].getFitness();
            this.populationMeanFitness += fitness;
            // Perform Stats
            // Best
            if(fitness > bestFitness) {
                // Check if old best is better than second
                if(bestFitness > secondFitness) {
                    // Theoretically it is always
                    secondFitness = bestFitness;
                    secondIndividualIndex = bestIndividualIndex;
                }
                bestFitness = fitness;
                bestIndividualIndex = i;
            } else if((fitness >= secondFitness) && (i != bestIndividualIndex)) {
                // If it is not better than best
                // I check for the second
                secondFitness = fitness;
                secondIndividualIndex = i;
            }
            else if(fitness > worstFitness) {
                // Worst
                worstFitness = fitness;
                worstIndividualIndex = i;
            }
        }
        // Calculate Mean
        this.populationMeanFitness/=this.popSize;
    }

    //Get the fittest individual
    public Individual getFittest() {
        return this.individuals[this.bestIndividualIndex];
    }

    //Get the second most fittest individual
    public Individual getSecondFittest() {
        return this.individuals[this.secondIndividualIndex];
    }

    //Get the worst most fittest individual
    public Individual getWorstFittest() {
        return this.individuals[this.worstIndividualIndex];
    }

    // Get Population Fitener
    public double getPopulationMeanFitness() {
        return populationMeanFitness;
    }
}