import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String path_name = "/Users/Komputer/Documents/Dokumenty/Nauka/Studia WIZ/Semestr 6/Sztuczna Inteligencja/Laborki/Lista 1/";
        int matrix_size = 20;

        double px = 0.7;
        double pm = 0.015;
        int pop_size = 200;
        int gen = 50;
        int tour = 5;
        int multiplicity = 50;

        DistanceFlow files = new DistanceFlow(path_name, matrix_size);
        ArrayList<ArrayList<Integer>> distance_matrix = files.distance_matrix;
        ArrayList<ArrayList<Integer>> flow_matrix = files.flow_matrix;

        // distance and flow are correctly read from file
        if (files.dataReader()) {
            PrintWriter writer = null;
            String file_name = "POP" + "matrix" + Integer.toString(matrix_size) + "_pop" + Integer.toString(pop_size) + "_px" + Double.toString(px) + "_pm"
                    + Double.toString(pm) + "_gen" + Integer.toString(gen) + "_tour" + Integer.toString(tour);

            // writing best costs to file
            try {
                writer = new PrintWriter(file_name + ".txt", "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i < multiplicity; i++) {
                ArrayList<Specimen> best_ones = runOneGenerationInTime(distance_matrix, flow_matrix, px, pm, tour, pop_size, gen);
                for(int a=0; a<best_ones.size(); a++)
                    writer.print(best_ones.get(a).cost + ";");
                writer.println();
            }
            writer.close();

        } else {
            System.out.println("Files haven't been found - sorry...");
        }
    }

    public static Specimen runOneGeneration(ArrayList<ArrayList<Integer>> distance_matrix, ArrayList<ArrayList<Integer>> flow_matrix,
                                            double px, double pm, double tour, int pop_size, int generations) {
        Population population = new Population(distance_matrix, flow_matrix, px, pm, tour, pop_size);
        population.countSpecimenCost();

        ArrayList<Specimen> best_ones = new ArrayList<>();
        best_ones.add(population.selectBestSpecimen());

        while(generations > 0) {
            population.selectAllParents();
            population.generateAllChildren();
            population.mutatePopulation();

            population = new Population(population);
            population.countSpecimenCost();
            best_ones.add(population.selectBestSpecimen());
            generations--;
        }

        Specimen best = population.selectBestSpecimen(best_ones);
        System.out.println("Best one: " + best);
        return best;
    }


    public static ArrayList<Specimen> runOneGenerationInTime(ArrayList<ArrayList<Integer>> distance_matrix, ArrayList<ArrayList<Integer>> flow_matrix,
                                            double px, double pm, double tour, int pop_size, int generations) {
        Population population = new Population(distance_matrix, flow_matrix, px, pm, tour, pop_size);
        population.countSpecimenCost();

        ArrayList<Specimen> best_ones = new ArrayList<>();
        best_ones.add(population.selectBestSpecimen());

        while(generations > 0) {
            population.selectAllParents();
            population.generateAllChildren();
            population.mutatePopulation();

            population = new Population(population);
            population.countSpecimenCost();
            best_ones.add(population.selectBestSpecimen());
            generations--;
        }
        Specimen best = population.selectBestSpecimen(best_ones);
        best_ones.add(best);
        return best_ones;
    }


    public static int averageCost(ArrayList<Specimen> best_ones) {
        int average = 0;
        for(int i=0; i<best_ones.size(); i++) {
            average += best_ones.get(i).cost;
        }
        return average/best_ones.size();
    }


}
