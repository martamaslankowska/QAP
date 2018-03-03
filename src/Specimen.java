import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Specimen {

    /* Result = genotype returned in format (indexing cities and facilities from 0)
     * [Lines] / Columns
     * [Index]  0  1  2  3  ... - cities in the right order
     * [1 line] 3  5  0  7  ... - facilities in the right order */

    Integer[] genotype;
    int size;
    int cost;

    public Specimen(int matrix_size) {
        size = matrix_size;
        genotype = new Integer[size];
        cost = 0;
    }

    public Specimen(ArrayList<Integer> list, ArrayList<ArrayList<Integer>> distances, ArrayList<ArrayList<Integer>> flows) {
        size = list.size();
        genotype = new Integer[size];
        genotype = list.toArray(genotype);
        cost = countCost(distances, flows);
    }

    public int countCost(ArrayList<ArrayList<Integer>> distances, ArrayList<ArrayList<Integer>> flows) {
        cost = 0;

        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                int fac_A = genotype[i];
                int fac_B = genotype[j];
                cost += flows.get(fac_A).get(fac_B) * distances.get(i).get(j);
            }
        }
        return cost;
    }

    public void fillArrayRandomly() {
        for (int i=0; i<size; i++) {
            genotype[i] = i; // facilities
        }
        shuffleArray(size);
    }

    public void shuffleArray(int shuffles) {
        Random rand = new Random();
        for (int i=0; i<shuffles; i++) {
            int fac_index = rand.nextInt(size);
            int city_index = rand.nextInt(size);
            int temp = genotype[fac_index];
            genotype[fac_index] = genotype[city_index];
            genotype[city_index] = temp;
        }
    }

    @Override
    public String toString() {
        String matrix = "cost = " + cost + " --> ";
        for(int i=0; i<size; i++)
            matrix += "(" + Integer.toString(i) + ", " + Integer.toString(genotype[i]) + ") ";
        return matrix;
    }
}
