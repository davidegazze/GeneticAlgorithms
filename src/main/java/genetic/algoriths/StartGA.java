package genetic.algoriths;

/**
 * @author Davide Gazzè
 */

//Main class
public class StartGA {

    public static void main(String[] args) throws CloneNotSupportedException {
        Population population = new Population(100, 1);
        int generationCount = 0;
        int maxGenerations = 1000000;
        int bestGeneration = 0;
        Individual bestIndividual = population.getFittest();
        System.out.println("Generation: " + generationCount + " Fittest: " + population.getFittestValue());
        System.out.println("First " + population.getFittest());
        System.out.println("Second " + population.getSecondFittest());
        System.out.println("Worst " + population.getWorstFittest());
        //While population gets an individual with maximum fitness
        while (true) {
            ++generationCount;
            System.out.println("Generation: " + generationCount + " ; Fittest: " + population.getFittestValue());
            /*
            System.out.println("First " + population.getFittest());
            System.out.println("Best " + bestIndividual.getFitness());
            System.out.println("Pop " + population.getFittest().getFitness());
             */
            if(bestIndividual.compareTo(population.getFittest()) == 0) {
                bestIndividual = population.getFittest().clone();
                bestGeneration = generationCount;
            }
            population.nextGeneration();
            if(generationCount == maxGenerations) break;
        }
        System.out.println("Last generation : " + generationCount);
        System.out.println("Last best :\n" +population.getFittest());
        System.out.println("Best solution all time :\n" + bestIndividual);
        System.out.println("Best generation : " + bestGeneration);
    }

}