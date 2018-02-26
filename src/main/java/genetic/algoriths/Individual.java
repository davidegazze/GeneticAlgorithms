package genetic.algoriths;

import java.util.Arrays;
import java.util.Random;

//Individual class
class Individual {

    private double fitness = 0;
    int[] genes = null;
    double value = 0;
    private int numberOfGenes = 10;

    public Individual(int numberOfGenes) {
        this.genes = new int[numberOfGenes];
        this.numberOfGenes = numberOfGenes;
        Random rn = new Random();
        //Set genes randomly for each individual
        this.value = 0;
        for (int i = 0; i < numberOfGenes; i++) {
            this.genes[i] = rn.nextInt(2);
            this.value = (this.value * 2) + this.genes[i];
        }
        this.fitness = 0;
    }

    public double getFitness() {
        return this.fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public int getNumberOfGenes() {
        return this.numberOfGenes;
    }

    public void updateIndividual() {
        // Update value of individual
        this.value = 0;
        for (int i = 0; i < numberOfGenes; i++) {
            this.value = (this.value * 2) + this.genes[i];
        }
        this.fitness = 0;
    }


    @Override
    public String toString() {
        return "Individual {" +
            "fitness = " + this.fitness +
            ", genes = " + Arrays.toString(this.genes) +
            ", value = " + this.value +
            ", Number of genes = " + this.numberOfGenes +
        "}";
    }

    public int compareTo(Individual o1) {
        if((o1 == null) || (this.getFitness() > o1.getFitness())) return 1;
        else return 0;
    }

    public Individual clone() throws CloneNotSupportedException {
        Individual cloned = new Individual(this.numberOfGenes);
        cloned.setFitness(this.getFitness());
        cloned.genes =  Arrays.copyOf(this.genes, this.numberOfGenes);
        return cloned;
    }

    public double getValue() {
        return this.value;
    }
}
