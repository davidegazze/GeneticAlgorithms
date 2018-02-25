package genetic.algoriths;

import java.util.Random;

//Population class
class Population {

    private int popSize = 10;
    private int numGenes = 10;
    private Individual[] individuals = null;
    private Fitness fitnessCalculator = new Fitness();
    private int bestIndividualIndex = 0;
    private int worstIndividualIndex = 0;
    private int secondIndividualIndex = 0;

    public Population(int popSize, int numGenes) {
        this.popSize = popSize;
        this.numGenes = numGenes;
        this.individuals = new Individual[this.popSize];
        double fitness = 0;
        for (int i = 0; i < this.popSize; i++) {
            Individual individual = new Individual(this.numGenes);
            fitness = fitnessCalculator.calcFitness(individual);
            individual.setFitness(fitness);
            individuals[i] = individual;
        }
        this.calculateFitness();
    }

    public void nextGeneration() {
        // Create a new gemeration
        Random rn = new Random();
        // Do crossover
        crossover();

        // Do mutation under a random probability
        if (rn.nextInt() % 7 < 5) {
            mutation();
        }
        // Add fittest offspring to population
        addFittestOffspring();
        // Calculate fitness of population
        this.calculateFitness();
    }

    // Crossover
    private void crossover() {
        Random rn = new Random();
        // Select a random crossover point
        int crossOverPoint = rn.nextInt(this.numGenes);
        // Swap values among parents
        for (int i = 0; i < crossOverPoint; i++) {
            double temp = this.getFittest().genes[i];
            this.getFittest().genes[i] =  this.getSecondFittest().genes[i];
            this.getSecondFittest().genes[i] = temp;
        }
        // Calculate Fitness
        this.getFittest().setFitness(new Fitness().calcFitness(this.getFittest()));
        this.getSecondFittest().setFitness(new Fitness().calcFitness(this.getSecondFittest()));
    }

    // Mutation
    private void mutation() {
        Random rn = new Random();
        // Select a random mutation point
        int mutationPoint = rn.nextInt(this.numGenes);
        this.getFittest().genes[mutationPoint] += rn.nextDouble();
        mutationPoint = rn.nextInt(this.numGenes);
        this.getSecondFittest().genes[mutationPoint] += rn.nextDouble();
    }

    //Get fittest offspring
    private Individual getFittestOffspring() {
        if (this.getFittestValue() > this.getSecondFittest().getFitness()) {
            return this.getFittest();
        }
        return this.getSecondFittest();
    }

    // Replace least fittest individual from most fittest offspring
    private void addFittestOffspring() {
        // Get index of least fit individual
        int leastFittestIndex = this.getLeastFittestIndex();
        // Replace least fittest individual from most fittest offspring
        try {
            this.individuals[leastFittestIndex] = getFittestOffspring().clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private void calculateFitness()  {
        double bestFitness = 0;
        double worstFitness = 0;
        double secondFitness = 0;
        double fitness = 0;
        for (int i = 0; i < this.popSize; i++) {
            fitness = individuals[i].getFitness();
            //System.out.println(i + " " + fitness + " " + bestFitness + " " + secondFitness + " " + worstFitness);
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
            } else if(fitness > secondFitness) {
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
    }

    //Get the fittest individual
    public Individual getFittest() {
        return this.individuals[this.bestIndividualIndex];
    }

    //Get the fittest individual
    public double getFittestValue() {
        return this.individuals[this.bestIndividualIndex].getFitness();
    }

    //Get the second most fittest individual
    public Individual getSecondFittest() {
        return this.individuals[this.secondIndividualIndex];
    }

    //Get the worst most fittest individual
    public Individual getWorstFittest() {
        return this.individuals[this.worstIndividualIndex];
    }

    //Get index of least fittest individual
    public int getLeastFittestIndex() {
        return this.worstIndividualIndex;
    }

    public int getNumGenes() {
        return numGenes;
    }
}