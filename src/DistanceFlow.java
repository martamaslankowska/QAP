import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DistanceFlow {

    String path;
    int matrix_size;

    ArrayList<ArrayList<Integer>> distance_matrix;
    ArrayList<ArrayList<Integer>> flow_matrix;

    public DistanceFlow(String path_name, int data_size) {
        path = path_name;
        matrix_size = data_size;
        distance_matrix = new ArrayList<>();
        flow_matrix = new ArrayList<>();
    }

    // returns information if desired .txt files are found
    public boolean dataReader() {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String fname = file.getName();
                int size = Integer.parseInt(fname.replaceAll("[\\D]", ""));

                if (size == matrix_size) {
                    switch(fname.split("_")[0]) {
                        case "distance":
                            fillMatrix(fname, distance_matrix);
                            break;
                        case "flow":
                            fillMatrix(fname, flow_matrix);
                            break;
                        default:
                            System.out.println("We have a problem with finding your file....");
                    }
                }
            }
        }

        if (distance_matrix.isEmpty() || flow_matrix.isEmpty())
            return false;
        return true;
    }

    public ArrayList<ArrayList<Integer>> fillMatrix(String matrix_name, ArrayList<ArrayList<Integer>> matrix){
        Scanner input = null;
        try {
            input = new Scanner(new File((path + matrix_name)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(input.hasNextLine())
        {
            Scanner colReader = new Scanner(input.nextLine());
            ArrayList col = new ArrayList();
            while(colReader.hasNextInt())
            {
                col.add(colReader.nextInt());
            }
            matrix.add(col);
        }
        return matrix;
    }

}
