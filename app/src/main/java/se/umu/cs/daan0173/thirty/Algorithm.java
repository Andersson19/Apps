package se.umu.cs.daan0173.thirty;

import java.util.*;
import java.util.Arrays;

//Algorithm used for calculating result of thrown dice and choice made
//
//Will find all possible combinations that will match the choice the user has made.
//Then it checks, for each combination, (starting at a 1 combination (exact match), then checking for a 2 combination and so forth..)
//if they are "legitimate" meaning, if they exist as a subarray in the array containing the result for dice thrown
//
//When a match has been found, all Integers from that combination will be set as '0' in the original array to prevent
//multiple combinations using the same Integers.
//
//To clarify the statement above, if a combination was found for let's say (2,1) for target 3 and result for dice thrown was [2,1,4,5,1,1].
//Since we do not find any exact matches for 3, we move onto 2 combinations and find (2,1) as a match for 3.
//This means when we get to the 3 combinations, (1,1,1) will not be counted since one of the 1's has been used in the 2 combinations already counted.
public class Algorithm {

    //Keep track of exact matches for given target
    private static int[] res_of_1 = new int[6];

    //Keep track of combinations of multiple die that match given target
    private static ArrayList<Tuple2<Integer,Integer>> res_of_2 = new ArrayList<Tuple2<Integer,Integer>>();
    private static ArrayList<Tuple3<Integer,Integer,Integer>> res_of_3 = new ArrayList<Tuple3<Integer,Integer,Integer>>();
    private static ArrayList<Tuple4<Integer,Integer,Integer,Integer>> res_of_4 = new ArrayList<Tuple4<Integer,Integer,Integer,Integer>>();
    private static ArrayList<Tuple5<Integer,Integer,Integer,Integer,Integer>> res_of_5 = new ArrayList<Tuple5<Integer,Integer,Integer,Integer,Integer>>();
    private static ArrayList<Tuple6<Integer,Integer,Integer,Integer,Integer,Integer>> res_of_6 = new ArrayList<Tuple6<Integer,Integer,Integer,Integer,Integer,Integer>>();

    //Array keeping track of result of the thrown dice
    private int[] arr;
    //The target we want to match
    private int target;
    //Points generated
    private int points;

    public int getResult() {
        return points;
    }

    public Algorithm(int[] array, int n) {
        this.arr = array;
        this.target = n;

        //search exact match and add to result_of_1 array
        search_exact(arr, target);

        //search pairs of two and add to result_of_2 array
        search_2(arr, target);

        //search three's and add to res_of_3 arr
        search_3(arr, target);

        //search four's and add to res_of_4 arr
        search_4(arr, target);

        //search five's and add to res_of_5 arr
        search_5(arr,target);

        //search six's and add to res_of_6 arr
        search_6(arr, target);

        //calculate result
        calc_result();
    }

    //Tuples used for keeping track of combinations that match target
    private static class Tuple2<A, B> {
        public A a;
        public B b;
    }

    private static class Tuple3<A, B, C> {
        public A a;
        public B b;
        public C c;
    }

    private static class Tuple4<A, B, C, D> {
        public A a;
        public B b;
        public C c;
        public D d;
    }

    private static class Tuple5<A, B, C, D, E> {
        public A a;
        public B b;
        public C c;
        public D d;
        public E e;
    }

    private static class Tuple6<A, B, C, D, E, F> {
        public A a;
        public B b;
        public C c;
        public D d;
        public E e;
        public F f;
    }

    //Find exact match for target
    public static void search_exact(int[] arr, int target) {
        int index = 0;
        for (int a : arr) {
            if (a == target) {
                res_of_1[index] = a;
                index += 1;
            }
        }
    }

    //Find all combination of two dice matching target
    public static void search_2(int[] arr, int target) {

        for (int i = 0; i < arr.length - 1; i++) {
            int x = arr[i];

            int next = i+1;
            boolean found = false;
            while(!found && next < arr.length) {
                int y = arr[next];

                if (x + y == target) {
                    Tuple2<Integer,Integer> tpl = new Tuple2<Integer,Integer>();
                    tpl.a = x;
                    tpl.b = y;

                    res_of_2.add(tpl);

                    found = true;
                }

                next++;
            }


        }
    }

    //Find all combination of three dice matching target
    public static void search_3(int[] arr, int target) {

        for (int i = 0; i < arr.length - 2; i++) {
            int x = arr[i];

            for (int p = i+1; p < arr.length - 1; p++) {
                int y = arr[p];

                for (int k = i+2; k < arr.length; k++) {
                    int z = arr[k];

                    if (x + y + z == target) {
                        Tuple3<Integer,Integer,Integer> tpl = new Tuple3<>();
                        tpl.a = x;
                        tpl.b = y;
                        tpl.c = z;

                        res_of_3.add(tpl);
                    }
                }
            }
        }
    }

    //Find all combination of four dice matching target
    public static void search_4(int[] arr, int target) {

        for (int i = 0; i < arr.length - 3; i++) {
            int x = arr[i];

            for (int p = i+1; p < arr.length - 2; p++) {
                int y = arr[p];

                for (int k = i+2; k < arr.length - 1; k++) {
                    int z = arr[k];

                    for (int l = i+3; l < arr.length; l++) {
                        int m = arr[l];
                        if (x + y + z + m == target) {
                            Tuple4<Integer,Integer,Integer,Integer> tpl = new Tuple4<>();
                            tpl.a = x;
                            tpl.b = y;
                            tpl.c = z;
                            tpl.d = m;

                            res_of_4.add(tpl);
                        }
                    }


                }
            }
        }
    }

    //Find all combination of five dice matching target
    public static void search_5(int[] arr, int target) {

        for (int i = 0; i < arr.length - 4; i++) {
            int x = arr[i];

            for (int p = i+1; p < arr.length - 3; p++) {
                int y = arr[p];

                for (int k = i+2; k < arr.length - 2; k++) {
                    int z = arr[k];

                    for (int l = i+3; l < arr.length - 1; l++) {
                        int m = arr[l];

                        for (int n = i+4; n < arr.length; n++) {
                            int f = arr[n];

                            if (x + y + z + m + f == target) {
                                Tuple5<Integer,Integer,Integer,Integer,Integer> tpl = new Tuple5<>();
                                tpl.a = x;
                                tpl.b = y;
                                tpl.c = z;
                                tpl.d = m;
                                tpl.e = f;

                                res_of_5.add(tpl);
                            }
                        }


                    }


                }
            }
        }
    }

    //Find all combination of six dice matching target
    public static void search_6(int[] arr, int target) {

        for (int i = 0; i < arr.length - 4; i++) {
            int x = arr[i];

            for (int p = i+1; p < arr.length - 3; p++) {
                int y = arr[p];

                for (int k = i+2; k < arr.length - 2; k++) {
                    int z = arr[k];

                    for (int l = i+3; l < arr.length - 1; l++) {
                        int m = arr[l];

                        for (int n = i+4; n < arr.length; n++) {
                            int f = arr[n];

                            for (int a = i+5; a < arr.length; a++) {
                                int u = arr[a];

                                if (x + y + z + m + f + u == target) {
                                    Tuple6<Integer,Integer,Integer,Integer,Integer,Integer> tpl = new Tuple6<>();
                                    tpl.a = x;
                                    tpl.b = y;
                                    tpl.c = z;
                                    tpl.d = m;
                                    tpl.e = f;
                                    tpl.f = u;

                                    res_of_6.add(tpl);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //Check for each combination, if they exist as subarray in array of results
    static boolean all_items_in_array(int[] arr, int[] temp) {

        int[] copy = arr.clone();

        for (int n = 0; n < temp.length; n++) {

            boolean found = false;

            for (int i = 0; i < copy.length; i++) {
                if (temp[n] == copy[i]) {
                    found = true;
                    //set to zero to prevent multiple combinations using the same die
                    copy[i] = 0;
                    break;
                }
            }

            if (!found) {
                return false;
            }
        }
        return true;
    }

    //Calculate the result
    public void calc_result() {
        points = 0;

        checkOne();
        checkTwo();
        checkThree();
        checkFour();
        checkFive();

        //reset all results
        Arrays.fill(res_of_1,0);
        res_of_2.clear();
        res_of_3.clear();
        res_of_4.clear();
        res_of_5.clear();
        res_of_6.clear();

    }

    //checks for exact matches of target in arr[]
    public void checkOne() {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                points += target;

                //Remove the exact match for target
                arr[i] = 0;
            }
        }
    }

    //checks for a pair of dice matching target in arr[]
    public void checkTwo() {
        for (Tuple2<Integer,Integer> tpl : res_of_2) {
            int[] temp = new int[2];
            temp[0] = tpl.a;
            temp[1] = tpl.b;
            if (all_items_in_array(arr, temp)) {
                points += target;

                //Remove the two integers that match target
                boolean a_removed = false;
                boolean b_removed = false;
                for (int i = 0; i < arr.length; i++) {

                    if (arr[i] == temp[0] && !a_removed) {
                        arr[i] = 0;
                        a_removed = true;
                    } else if (arr[i] == temp[1] && !b_removed) {
                        arr[i] = 0;
                        b_removed = true;
                    }
                }
            }
        }
    }

    //checks for a 3-pair match for target in arr[]
    public void checkThree() {
        for (Tuple3<Integer,Integer,Integer> tpl : res_of_3) {
            int[] temp = new int[3];
            temp[0] = tpl.a;
            temp[1] = tpl.b;
            temp[2] = tpl.c;
            if (all_items_in_array(arr, temp)) {
                points += target;

                //Remove the three integers that match target
                boolean a_removed = false;
                boolean b_removed = false;
                boolean c_removed = false;
                for (int i = 0; i < arr.length; i++) {

                    if (arr[i] == temp[0] && !a_removed) {
                        arr[i] = 0;
                        a_removed = true;
                    } else if (arr[i] == temp[1] && !b_removed) {
                        arr[i] = 0;
                        b_removed = true;
                    } else if (arr[i] == temp[2] && !c_removed) {
                        arr[i] = 0;
                        c_removed = true;
                    }
                }
            }
        }
    }

    //checks for a 4-pair match in target
    public void checkFour() {
        boolean done = false;
        for (Tuple4<Integer,Integer,Integer,Integer> tpl : res_of_4) {
            if (done) {
                break;
            }

            int[] temp = new int[4];
            temp[0] = tpl.a;
            temp[1] = tpl.b;
            temp[2] = tpl.c;
            temp[3] = tpl.d;
            if (all_items_in_array(arr, temp)) {
                points += target;

                //Remove the three integers that match target
                boolean a_removed = false;
                boolean b_removed = false;
                boolean c_removed = false;
                boolean d_removed = false;
                for (int i = 0; i < arr.length; i++) {

                    if (arr[i] == temp[0] && !a_removed) {
                        arr[i] = 0;
                        a_removed = true;
                    } else if (arr[i] == temp[1] && !b_removed) {
                        arr[i] = 0;
                        b_removed = true;
                    } else if (arr[i] == temp[2] && !c_removed) {
                        arr[i] = 0;
                        c_removed = true;
                    } else if (arr[i] == temp[2] && !d_removed) {
                        arr[i] = 0;
                        d_removed = true;
                    }
                }
                done = true;
            }
        }
    }

    //checks for a 5-pair match in target
    public void checkFive() {
        boolean done = false;
        for (Tuple5<Integer,Integer,Integer,Integer,Integer> tpl : res_of_5) {
            if (done) {
                break;
            }
            int[] temp = new int[5];
            temp[0] = tpl.a;
            temp[1] = tpl.b;
            temp[2] = tpl.c;
            temp[3] = tpl.d;
            temp[4] = tpl.e;
            if (all_items_in_array(arr, temp)) {
                points += target;

                // no need to remove here

                done = true;
            }
        }
    }

    //checks for a 6-pair match in target
    public void checkSix() {
        boolean done = false;
        for (Tuple6<Integer,Integer,Integer,Integer,Integer,Integer> tpl : res_of_6) {
            if (done) {
                break;
            }
            int[] temp = new int[6];
            temp[0] = tpl.a;
            temp[1] = tpl.b;
            temp[2] = tpl.c;
            temp[3] = tpl.d;
            temp[4] = tpl.e;
            temp[5] = tpl.f;
            if (all_items_in_array(arr, temp)) {
                points += target;

                //no need to remove here

                done = true;
            }
        }
    }

    //main method used for debugging

    /*
    public static void main(String[] args) {
        int[] arr = {2,5,5,6,6,1};
        int TARGET = 5;

        System.out.println("Searching: " + Arrays.toString(arr));

        new Algorithm(arr, TARGET);

        System.out.println("res_of_1: " + Arrays.toString(res_of_1));

        System.out.print("res_of_2: [ ");
        for (int i = 0; i < res_of_2.size(); i++) {
            System.out.print("(" + res_of_2.get(i).a + ", " + res_of_2.get(i).b + "), ");
        }
        System.out.print(" ]");
        System.out.println("");

        System.out.print("res_of_3: [ ");
        for (int i = 0; i < res_of_3.size(); i++) {
            System.out.print("(" + res_of_3.get(i).a + ", " + res_of_3.get(i).b + ", " + res_of_3.get(i).c + "), ");
        }
        System.out.print(" ]");
        System.out.println("");

        System.out.print("res_of_4: [ ");
        for (int i = 0; i < res_of_4.size(); i++) {
            System.out.print("(" + res_of_4.get(i).a + ", " + res_of_4.get(i).b + ", " + res_of_4.get(i).c + ", " + res_of_4.get(i).d + "), ");
        }
        System.out.print(" ]");
        System.out.println("");

        System.out.print("res_of_5: [ ");
        for (int i = 0; i < res_of_5.size(); i++) {
            System.out.print("(" + res_of_5.get(i).a + ", " + res_of_5.get(i).b + ", " + res_of_5.get(i).c + ", " + res_of_5.get(i).d + ", " + res_of_5.get(i).e + "), ");
        }
        System.out.print(" ]");
        System.out.println("");

        System.out.print("res_of_6: [ ");
        for (int i = 0; i < res_of_6.size(); i++) {
            System.out.print("(" + res_of_6.get(i).a + ", " + res_of_6.get(i).b + ", " + res_of_6.get(i).c + ", " + res_of_6.get(i).d + ", " + res_of_6.get(i).e + ", " + res_of_6.get(i).f + "), ");
        }
        System.out.print(" ]");
        System.out.println("");
    }
    */
}