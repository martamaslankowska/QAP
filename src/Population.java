import java.lang.reflect.Array;
import java.util.*;

public class Population {
    double p_cross;
    double p_mutation;
    double tour;

    int matrix_size;
    int overallCost;
    int pop_size;

    ArrayList<ArrayList<Integer>> distance_matrix;
    ArrayList<ArrayList<Integer>> flow_matrix;
    ArrayList<Specimen> population = new ArrayList<>();
    ArrayList<Specimen> parents = new ArrayList<>();
    ArrayList<Specimen> children = new ArrayList<>();

    public Population(ArrayList<ArrayList<Integer>> distance_matrix, ArrayList<ArrayList<Integer>> flow_matrix,
                      double px, double pm, double tour, int pop_size) {
        this.distance_matrix = distance_matrix;
        this.flow_matrix = flow_matrix;
        this.matrix_size = distance_matrix.size();
        this.p_cross = px;
        this.p_mutation = pm;
        this.tour = tour;
        this.pop_size = pop_size;

        population = generateFirstPopulation();
    }

    public Population(Population old) {
        population.addAll(old.parents);
        population.addAll(old.children);
        distance_matrix = old.distance_matrix;
        flow_matrix = old.flow_matrix;
        matrix_size = old.matrix_size;
        p_cross = old.p_cross;
        p_mutation = old.p_mutation;
        pop_size = old.pop_size;
        tour = old.tour;
    }


    private ArrayList<Specimen> generateFirstPopulation() {
        for(int i=0; i<pop_size; i++) {
            Specimen spec = new Specimen(matrix_size);
            spec.fillArrayRandomly();
            spec.countCost(distance_matrix, flow_matrix);
            population.add(spec);
        }
        return population;
    }

    int countSpecimenCost(ArrayList<Specimen> pop) {
        overallCost = 0;
        for(Specimen s : pop) {
            overallCost += s.countCost(distance_matrix, flow_matrix);
        }
        return overallCost;
    }


    ArrayList<Specimen> selectAllParents() {
        int nr_of_parents = (int) (p_cross*pop_size);
        for(int i=0; i<nr_of_parents; i++) {
            parents.add(selectParentTour());
        }
        return parents;
    }

    private Specimen selectParentTour() {
        Random rand = new Random();
        ArrayList<Specimen> potential_parents = new ArrayList<>();
        for(int i=0; i<tour; i++)
            potential_parents.add(population.get(rand.nextInt(pop_size)));

        return selectBestSpecimen(potential_parents);
    }

    Specimen selectBestSpecimen(ArrayList<Specimen> array) {
        Specimen best = array.get(0);
        for(int j=1; j<tour; j++) {
            if(best.cost > array.get(j).cost)
                best = array.get(j);
        }
        return best;
    }

    private List<Specimen> generateTwins(Specimen mom, Specimen dad) {
        ArrayList<Specimen> twins = new ArrayList<>();
        Random rand = new Random();
        int midd_number = (int) matrix_size/2;
        int crossing_point = rand.nextInt(matrix_size-midd_number)+(int)(midd_number/2);

        twins.add(generateChild(mom, dad, crossing_point));
        if (children.size() < (pop_size-parents.size()))
            twins.add(generateChild(dad, mom, crossing_point));

        return twins;
    }

    private Specimen generateChild(Specimen mom, Specimen dad, int point) {
        // makes sure we don't generate child with more than one facility of each kind
        ArrayList<Integer> numbers =  new ArrayList<>();
        for(int a=0; a<matrix_size; a++)
            numbers.add(a);

        Specimen child = new Specimen(matrix_size);
        for(int i=0; i<point; i++) {
            child.genotype[i] = mom.genotype[i];
            numbers.remove((Integer)mom.genotype[i]);
        }
        for(int i=point; i<matrix_size; i++) {
            if(numbers.contains(dad.genotype[i]))
                child.genotype[i] = -1;
            else {
                child.genotype[i] = dad.genotype[i];
                numbers.remove((Integer)dad.genotype[i]);
            }
        }

        // makes sure that all repetitive facilities are replaced with random numbers, that are not in use
        for(int j=0; j<matrix_size; j++) {
            if(child.genotype[j] < 0) {
                child.genotype[j] = numbers.get(0);
                numbers.remove(0);
            }
        }
        return child;
    }

    List<Specimen> generateAllChildren() {
        Random rand = new Random();
        while(children.size() < (pop_size-parents.size())) {
            int mom_index = rand.nextInt(parents.size());
            int dad_index = rand.nextInt(parents.size());
            Specimen mom = parents.get(mom_index);
            while(dad_index == mom_index)
                dad_index = rand.nextInt(parents.size());
            Specimen dad = parents.get(dad_index);

            children.addAll(generateTwins(mom, dad));
        }
        countSpecimenCost(children);
        return children;
    }

    ArrayList<Specimen> mutatePopulation() {
        // mutates population of selected parents
        int nr_of_mutations = (int)(p_mutation*pop_size);
        Random rand = new Random();
        for(int i=0; i<nr_of_mutations; i++) {
            Specimen mutated = parents.get(rand.nextInt(parents.size()));
            mutated.shuffleArray(3); //shuffles n pairs (city,facility) in genotype
        }
        return parents;
    }
}
