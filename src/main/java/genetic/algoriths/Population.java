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
            System.out.println(individual);
        }
        this.calculateFitnessPopulation();
    }

    public void nextGeneration() {
        // Create a new gemeration
        Random rn = new Random();
        // Do crossover
        crossover();

        // Do mutation under a random probability
        if (rn.nextInt() % 10 < 5) {
            mutation();
        }
        // Add fittest offspring to population
        addFittestOffspring();
        // Calculate fitness of population
        this.updateBestIndividuals();
        this.calculateFitnessPopulation();
    }

    // Crossover
    private void crossover() {
        Random rn = new Random();
        // Select a random crossover point
        int crossOverPoint = rn.nextInt(this.numGenes);
        // Swap values among parents
        for (int i = 0; i < crossOverPoint; i++) {
            int temp = this.getFittest().genes[i];
            this.getFittest().genes[i] =  this.getSecondFittest().genes[i];
            this.getSecondFittest().genes[i] = temp;
        }
    }

    // Mutation by Flip
    private void mutation() {
        Random rn = new Random();
        // Select a random mutation point
        int mutationPoint = rn.nextInt(this.numGenes);
        this.getFittest().genes[mutationPoint] = (1 - this.getFittest().genes[mutationPoint]);
        mutationPoint = rn.nextInt(this.numGenes);
        this.getSecondFittest().genes[mutationPoint] = (1 - this.getSecondFittest().genes[mutationPoint]);
    }

    private void updateBestIndividuals() {
        // Update Value and Fitness
        System.out.println("old : " + this.getFittest().getValue());
        System.out.println("old : " + this.getFittest().getFitness());
        this.getFittest().updateIndividual();
        this.getFittest().setFitness(fitnessCalculator.calcFitness(this.getFittest()));
        this.getSecondFittest().updateIndividual();
        this.getSecondFittest().setFitness(fitnessCalculator.calcFitness(this.getSecondFittest()));
        System.out.println("new : " + this.getFittest().getValue());
        System.out.println("new : " + this.getFittest().getFitness());
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
        try {
            this.individuals[this.worstIndividualIndex] = getFittestOffspring().clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private void calculateFitnessPopulation()  {
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

    //Get the second most fittest individual
    public Individual getSecondFittest() {
        return this.individuals[this.secondIndividualIndex];
    }

    //Get the worst most fittest individual
    public Individual getWorstFittest() {
        return this.individuals[this.worstIndividualIndex];
    }

}