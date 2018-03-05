package genetic.algoriths;

import java.util.*;
import java.util.Random;
import java.util.stream.*;

//Individual class
class Individual {

    private double fitness = 0;
    private int[] genes = null;
    private int value = 0;
    private int numberOfGenes = 10;


    public Individual(int numberOfGenes) {
        this.genes = new int[numberOfGenes];
        this.numberOfGenes = numberOfGenes;
        Random rn = new Random();
        // Set genes randomly for each individual
        for(int i=0; i<this.numberOfGenes; i++)
            this.genes[i] = rn.nextInt(2);
        this.updateIndividual();
    }

    public void updateIndividual() {
        // Update value of individual
        this.value = Arrays.stream(this.genes).sum();
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


    public int compareTo(Individual o1) {
        if((o1 == null) || (this.getFitness() >= o1.getFitness())) return 1;
        else return 0;
    }

    public Individual clone() {
        Individual cloned = new Individual(this.numberOfGenes);
        cloned.setFitness(this.getFitness());
        cloned.value = this.value;
        cloned.genes = Arrays.copyOf(this.genes, this.numberOfGenes);
        return cloned;
    }

    public double getValue() {
        return this.value;
    }

    public int getGeneValue(int i) {
        return genes[i];
    }

    public void setGeneValue(int i, int value) {
        genes[i] = value;
    }

    public void inverseGenotype() {
        int[] newGenes = new int[this.numberOfGenes];
        for(int i=0; i<this.numberOfGenes; i++) {
            newGenes[this.numberOfGenes - i -1] = this.genes[i];
        }
        this.genes = null;
        this.genes = newGenes;
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

}
