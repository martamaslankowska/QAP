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

        // distance and flow are correctly read from file
        if (files.dataReader()) {

            Specimen example_solution = new Specimen(matrix_size);
            example_solution.fillArrayRandomly();
            example_solution.countCost(distance_matrix, flow_matrix);
            System.out.println(example_solution);

            Population first_pop = new Population(distance_matrix, flow_matrix, px, pm, tour, pop_size);
            for(int i=0; i<pop_size; i++) {
                System.out.println(first_pop.population.get(i));
            }

            System.out.println("\n");

            first_pop.selectAllParents();
            System.out.println("Number of parents = " + first_pop.parents.size());
            for(int i=0; i<first_pop.parents.size(); i++) {
                System.out.println(first_pop.parents.get(i));
            }

            System.out.println("\n");

            first_pop.generateAllChildren();
            System.out.println("Number of children = " + first_pop.children.size());
            for(int i=0; i<first_pop.children.size(); i++) {
                System.out.println(first_pop.children.get(i));
            }
            first_pop.mutatePopulation();

            System.out.println("\n\n\n");

            Population next_pop = new Population(first_pop);
            System.out.println("Population size = " + next_pop.population.size() + " (" + pop_size + ")");
            for(int i=0; i<pop_size; i++) {
                System.out.println(next_pop.population.get(i));
            }

            System.out.println("\n");

            next_pop.selectAllParents();
            System.out.println("Number of parents = " + next_pop.parents.size());
            for(int i=0; i<next_pop.parents.size(); i++) {
                System.out.println(next_pop.parents.get(i));
            }

            System.out.println("\n");

            next_pop.generateAllChildren();
            System.out.println("Number of children = " + next_pop.children.size());
            for(int i=0; i<next_pop.children.size(); i++) {
                System.out.println(next_pop.children.get(i));
            }

            Population new_pop = new Population(next_pop);

            System.out.println(first_pop.countSpecimenCost(first_pop.population));
            System.out.println(next_pop.countSpecimenCost(next_pop.population));
            System.out.println(new_pop.countSpecimenCost(new_pop.population));

            System.out.println("\nBEST\n" + first_pop.selectBestSpecimen(first_pop.population));
            System.out.println(next_pop.selectBestSpecimen(next_pop.population));
            System.out.println(new_pop.selectBestSpecimen(new_pop.population));


        } else {
            System.out.println("Files haven't been found - sorry...");
        }
    }



}
