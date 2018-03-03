import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteForce {

    public static void main(String[] args) {
        String path_name = "/Users/Komputer/Documents/Dokumenty/Nauka/Studia WIZ/Semestr 6/Sztuczna Inteligencja/Laborki/Lista 1/";
        int matrix_size = 6;

        double px = 0.7;
        double pm = 0.01;
        int pop_size = 100;
        int gen = 100;
        int tour = 5;

        DistanceFlow files = new DistanceFlow(path_name, matrix_size);
        ArrayList<ArrayList<Integer>> distance_matrix = files.distance_matrix;
        ArrayList<ArrayList<Integer>> flow_matrix = files.flow_matrix;

        ArrayList<Integer> list = new ArrayList<>();
        for(int i=0; i<matrix_size; i++)
            list.add(i);

//         distance and flow are correctly read from file
        if (files.dataReader()) {
            ArrayList<ArrayList<Integer>> result = listPermutations(list);
            System.out.println(result.size());
            Specimen best = new Specimen(result.get(0), distance_matrix, flow_matrix);

        for(int i=0; i<result.size(); i++) {
            Specimen act = new Specimen(result.get(i), distance_matrix, flow_matrix);
            System.out.println(result.get(i) + "  :  " + act);
            if(best.cost > act.cost)
                best = act;
        }
            System.out.println("\nBEST: " + best);

        } else {
            System.out.println("Files haven't been found - sorry...");
        }
    }


    public static ArrayList<ArrayList<Integer>> listPermutations(ArrayList<Integer> list) {

        if (list.size() == 0) {
            ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
            result.add(new ArrayList<Integer>());
            return result;
        }

        ArrayList<ArrayList<Integer>> returnMe = new ArrayList<ArrayList<Integer>>();

        Integer firstElement = list.remove(0);

        ArrayList<ArrayList<Integer>> recursiveReturn = listPermutations(list);
        for (List<Integer> li : recursiveReturn) {

            for (int index = 0; index <= li.size(); index++) {
                ArrayList<Integer> temp = new ArrayList<Integer>(li);
                temp.add(index, firstElement);
                returnMe.add(temp);
            }
        }
        return returnMe;
    }




}
