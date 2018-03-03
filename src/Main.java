import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String path_name = "/Users/Komputer/Documents/Dokumenty/Nauka/Studia WIZ/Semestr 6/Sztuczna Inteligencja/Laborki/Lista 1/";
        int matrix_size = 12;

        double px = 0.6;
        double pm = 0.015;
        int pop_size = 100;
        int gen = 100;
        int tour = 10;
        int multiplicity = 100;

        DistanceFlow files = new DistanceFlow(path_name, matrix_size);
        ArrayList<ArrayList<Integer>> distance_matrix = files.distance_matrix;
        ArrayList<ArrayList<Integer>> flow_matrix = files.flow_matrix;

        // distance and flow are correctly read from file
        if (files.dataReader()) {
            ArrayList<Specimen> best_ones = new ArrayList<>();

            // writing best costs to file
            PrintWriter writer = null;
            String file_name = "matrix" + Integer.toString(matrix_size) + "_pop" + Integer.toString(pop_size) + "_px" + Double.toString(px) + "_pm"
                    + Double.toString(pm)+ "_gen" + Integer.toString(gen) + "_tour" + Integer.toString(tour);
            try {
                writer = new PrintWriter(file_name + ".txt", "UTF-8");
            } catch (Exception e) {
                e.printStackTrace(); }

            for(int i=0; i<multiplicity; i++) {
                best_ones.add(runOneGeneration(distance_matrix, flow_matrix, px, pm, tour, pop_size, gen));
                writer.println(best_ones.get(i).cost);
            }
            writer.close();

            System.out.println("\nAVERAGE COST: " + averageCost(best_ones));

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

    public static int averageCost(ArrayList<Specimen> best_ones) {
        int average = 0;
        for(int i=0; i<best_ones.size(); i++) {
            average += best_ones.get(i).cost;
        }
        return average/best_ones.size();
    }


}
