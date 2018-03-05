package genetic.algoriths;

/**
 * @author Davide Gazzè
 */

//Main class
public class StartGA {

    public static void main(String[] args) {
        Population population = new Population(100, 100, 50);
        int generationCount = 0;
        int maxGenerations = 100000000;
        int bestGeneration = 0;
        Individual bestIndividual = population.getFittest();
        System.out.println("Generation: " + generationCount + " Fittest: " + population.getFittest().getFitness());
        System.out.println("First " + population.getFittest());
        System.out.println("Second " + population.getSecondFittest());
        System.out.println("Worst " + population.getWorstFittest());
        //While population gets an individual with maximum fitness
        double preFitness = 0.0;
        while (true) {
            generationCount++;
            if(generationCount % 10000 == 0) System.out.println("Generation " + generationCount + " Value " + population.getFittest().getValue() + " (fitness : " + population.getFittest().getFitness() + " \t Mean Fitness : " + population.getPopulationMeanFitness() + " \t Increment : " + (population.getPopulationMeanFitness() - preFitness) + ")");
            if(bestIndividual.compareTo(population.getFittest()) == 0) {
                bestIndividual = population.getFittest().clone();
                bestGeneration = generationCount;
            }
            if(population.getFittest().getFitness() == 1) break;
            if(generationCount == maxGenerations) break;
            preFitness = population.getPopulationMeanFitness();
            population.nextGeneration();
        }
        System.out.println("Last generation : " + generationCount);
        System.out.println("Last best :\n" +population.getFittest());
        System.out.println("Best solution all time :\n" + bestIndividual);
        System.out.println("Best generation : " + bestGeneration);
    }

}