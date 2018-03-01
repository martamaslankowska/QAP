import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String path_name = "/Users/Komputer/Documents/Dokumenty/Nauka/Studia WIZ/Semestr 6/Sztuczna Inteligencja/Laborki/Lista 1/";
        int matrix_size = 16;

        double px = 0.7;
        double pm = 0.01;
        int pop_size = 100;
        int gen = 100;
        int tour = 5;

        DistanceFlow files = new DistanceFlow(path_name, matrix_size);
        ArrayList<ArrayList<Integer>> distance_matrix = files.distance_matrix;
        ArrayList<ArrayList<Integer>> flow_matrix = files.flow_matrix;
        ArrayList<Specimen> best_ones = new ArrayList<>();

        // distance and flow are correctly read from file
        if (files.dataReader()) {
            Population population = new Population(distance_matrix, flow_matrix, px, pm, tour, pop_size);
            population.countSpecimenCost();
            best_ones.add(population.selectBestSpecimen());

            while(gen > 0) {
                population.selectAllParents();
                population.generateAllChildren();
                population.mutatePopulation();

                population = new Population(population);
                population.countSpecimenCost();
                best_ones.add(population.selectBestSpecimen());
                gen--;
            }

            Specimen best = population.selectBestSpecimen(best_ones);
            System.out.println("\nBest one: " + best);

        } else {
            System.out.println("Files haven't been found - sorry...");
        }
    }



}
