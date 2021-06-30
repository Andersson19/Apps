package se.umu.cs.daan0173.thirty;

import java.util.*;
import java.util.Arrays;

public class Algorithm {

    private static int[] res_of_1 = new int[6];
    private static ArrayList<Tuple2<Integer,Integer>> res_of_2 = new ArrayList<Tuple2<Integer,Integer>>();
    private static ArrayList<Tuple3<Integer,Integer,Integer>> res_of_3 = new ArrayList<Tuple3<Integer,Integer,Integer>>();
    private static ArrayList<Tuple4<Integer,Integer,Integer,Integer>> res_of_4 = new ArrayList<Tuple4<Integer,Integer,Integer,Integer>>();
    private static ArrayList<Tuple5<Integer,Integer,Integer,Integer,Integer>> res_of_5 = new ArrayList<Tuple5<Integer,Integer,Integer,Integer,Integer>>();
    private static ArrayList<Tuple6<Integer,Integer,Integer,Integer,Integer,Integer>> res_of_6 = new ArrayList<Tuple6<Integer,Integer,Integer,Integer,Integer,Integer>>();


    private int[] arr;
    private int target;
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

        calc_result();
    }

    public static void search_exact(int[] arr, int target) {
        int index = 0;
        for (int a : arr) {
            if (a == target) {
                System.out.println("found lonesome");
                res_of_1[index] = a;
                index += 1;
            }
        }
    }

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

    public static void search_2(int[] arr, int target) {

        for (int i = 0; i < arr.length - 1; i++) {
            int x = arr[i];

            int next = i+1;
            boolean found = false;
            while(!found && next < arr.length) {
                int y = arr[next];

                if (x + y == target) {
                    System.out.println("" + x + " + " + y + " = " + target);
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

    public static void search_3(int[] arr, int target) {

        for (int i = 0; i < arr.length - 2; i++) {
            int x = arr[i];

            for (int p = i+1; p < arr.length - 1; p++) {
                int y = arr[p];

                for (int k = i+2; k < arr.length; k++) {
                    int z = arr[k];

                    if (x + y + z == target) {
                        System.out.println("" + x + " + " + y + " + " + z + " = " + target);
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
                            System.out.println("" + x + " + " + y + " + " + z + " + " + m + " = " + target);
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
                                System.out.println("" + x + " + " + y + " + " + z + " + " + m + " + " + f + " = " + target);
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
                                    System.out.println("" + x + " + " + y + " + " + z + " + " + m + " + " + f + " + " + u + " = " + target);
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

    static boolean all_items_in_array(int[] arr, int[] temp) {

        int[] copy = arr.clone();

        //System.out.println("Checking array: " + Arrays.toString(temp));

        for (int n = 0; n < temp.length; n++) {

            //System.out.println("Checking: " + n);
            boolean found = false;

            for (int i = 0; i < copy.length; i++) {
                if (temp[n] == copy[i]) {
                    //System.out.println("Found match for: " + temp[n] + " with tempindex: " + n);
                    found = true;
                    copy[i] = 0;
                    break;
                } else {
                    //System.out.println("" + temp[n] + " is not equal to: " + copy[i]);
                }
            }

            if (!found) {

                return false;
            }

        }

        return true;
    }

    public void calc_result() {
        int result = 0;

        System.out.println("Calculating results for arr: " + Arrays.toString(arr) + " with target: " + target);

        //check 1's
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                result += target;

                //remove this element from the array
                arr[i] = 0;
                System.out.println("Removed lonesome: " + arr[i] + "and added points");
            }
        }

        System.out.println("Array is now : " + Arrays.toString(arr));

        //check 2's

        for (Tuple2<Integer,Integer> tpl : res_of_2) {
            int[] temp = new int[2];
            temp[0] = tpl.a;
            temp[1] = tpl.b;
            if (all_items_in_array(arr, temp)) {
                result += target;

                boolean a_removed = false;
                boolean b_removed = false;
                for (int i = 0; i < arr.length; i++) {

                    if (arr[i] == temp[0] && !a_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[0]);
                        arr[i] = 0;
                        a_removed = true;
                    } else if (arr[i] == temp[1] && !b_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[1]);
                        arr[i] = 0;
                        b_removed = true;
                    }
                }
            }
        }

        System.out.println("Array is now : " + Arrays.toString(arr));

        //check 3's

        for (Tuple3<Integer,Integer,Integer> tpl : res_of_3) {
            int[] temp = new int[3];
            temp[0] = tpl.a;
            temp[1] = tpl.b;
            temp[2] = tpl.c;
            if (all_items_in_array(arr, temp)) {
                result += target;

                boolean a_removed = false;
                boolean b_removed = false;
                boolean c_removed = false;
                for (int i = 0; i < arr.length; i++) {

                    if (arr[i] == temp[0] && !a_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[0]);
                        arr[i] = 0;
                        a_removed = true;
                    } else if (arr[i] == temp[1] && !b_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[1]);
                        arr[i] = 0;
                        b_removed = true;
                    } else if (arr[i] == temp[2] && !c_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[2]);
                        arr[i] = 0;
                        c_removed = true;
                    }
                }
            }
        }

        System.out.println("Array is now : " + Arrays.toString(arr));

        //check 4's

        for (Tuple4<Integer,Integer,Integer,Integer> tpl : res_of_4) {
            int[] temp = new int[4];
            temp[0] = tpl.a;
            temp[1] = tpl.b;
            temp[2] = tpl.c;
            temp[3] = tpl.d;
            if (all_items_in_array(arr, temp)) {
                result += target;

                boolean a_removed = false;
                boolean b_removed = false;
                boolean c_removed = false;
                boolean d_removed = false;
                for (int i = 0; i < arr.length; i++) {

                    if (arr[i] == temp[0] && !a_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[0]);
                        arr[i] = 0;
                        a_removed = true;
                    } else if (arr[i] == temp[1] && !b_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[1]);
                        arr[i] = 0;
                        b_removed = true;
                    } else if (arr[i] == temp[2] && !c_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[2]);
                        arr[i] = 0;
                        c_removed = true;
                    } else if (arr[i] == temp[3] && !d_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[3]);
                        arr[i] = 0;
                        d_removed = true;
                    }
                }
            }
        }

        //check 5's

        for (Tuple5<Integer,Integer,Integer,Integer,Integer> tpl : res_of_5) {
            int[] temp = new int[5];
            temp[0] = tpl.a;
            temp[1] = tpl.b;
            temp[2] = tpl.c;
            temp[3] = tpl.d;
            temp[4] = tpl.e;
            if (all_items_in_array(arr, temp)) {
                result += target;

                boolean a_removed = false;
                boolean b_removed = false;
                boolean c_removed = false;
                boolean d_removed = false;
                boolean e_removed = false;
                for (int i = 0; i < arr.length; i++) {

                    if (arr[i] == temp[0] && !a_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[0]);
                        arr[i] = 0;
                        a_removed = true;
                    } else if (arr[i] == temp[1] && !b_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[1]);
                        arr[i] = 0;
                        b_removed = true;
                    } else if (arr[i] == temp[2] && !c_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[2]);
                        arr[i] = 0;
                        c_removed = true;
                    } else if (arr[i] == temp[3] && !d_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[3]);
                        arr[i] = 0;
                        d_removed = true;
                    } else if (arr[i] == temp[4] && !e_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[4]);
                        arr[i] = 0;
                        e_removed = true;
                    }
                }
            }
        }

        //check 6's

        for (Tuple6<Integer,Integer,Integer,Integer,Integer,Integer> tpl : res_of_6) {
            int[] temp = new int[6];
            temp[0] = tpl.a;
            temp[1] = tpl.b;
            temp[2] = tpl.c;
            temp[3] = tpl.d;
            temp[4] = tpl.e;
            temp[5] = tpl.f;
            if (all_items_in_array(arr, temp)) {
                result += target;

                boolean a_removed = false;
                boolean b_removed = false;
                boolean c_removed = false;
                boolean d_removed = false;
                boolean e_removed = false;
                boolean f_removed = false;
                for (int i = 0; i < arr.length; i++) {

                    if (arr[i] == temp[0] && !a_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[0]);
                        arr[i] = 0;
                        a_removed = true;
                    } else if (arr[i] == temp[1] && !b_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[1]);
                        arr[i] = 0;
                        b_removed = true;
                    } else if (arr[i] == temp[2] && !c_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[2]);
                        arr[i] = 0;
                        c_removed = true;
                    } else if (arr[i] == temp[3] && !d_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[3]);
                        arr[i] = 0;
                        d_removed = true;
                    } else if (arr[i] == temp[4] && !e_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[4]);
                        arr[i] = 0;
                        e_removed = true;
                    } else if (arr[i] == temp[5] && !f_removed) {
                        System.out.println("Found match in: " + arr[i] + " and " + temp[5]);
                        arr[i] = 0;
                        f_removed = true;
                    }
                }
            }
        }

        System.out.println("Points: " + result);

        Arrays.fill(res_of_1,0);
        res_of_2.clear();
        res_of_3.clear();
        res_of_4.clear();
        res_of_5.clear();
        res_of_6.clear();

        this.points = result;
    }



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


}