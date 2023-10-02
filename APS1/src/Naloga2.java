import java.util.Scanner;

public class Naloga2 {

    public interface ResizableArray<T> {

        void add(T data);
        StringBuilder toString1(int start, int end);

        int get(int i);

        int size();

        void set(int index, int value);

    }

    public static class ResizableArrasImpl<T> implements ResizableArray<T> {

        private Object[] temp_array = null;
        private Object[] array = new Object[0];


        @Override
        public void add(T data) {
            Object[] temp_array = new Object[array.length + 1];
            for (int i = 0; i < array.length; i++) {
                temp_array[i] = array[i];
            }
            temp_array[array.length] = data;
            array = temp_array;
        }

        @Override
        public StringBuilder toString1(int start, int end) {
            StringBuilder sb = null;
            if (array.length > 0) {
                for (int i = start; i < end; i++) {
                    if (sb == null) {
                        sb = new StringBuilder();
                        sb.append(array[i]);
                    } else {
                        sb.append(" " + array[i]);
                    }
                }
            }
            return sb;
        }

        @Override
        public int get(int i) {
            return (int) array[i];
        }

        @Override
        public int size() {
            return array.length;
        }

        @Override
        public void set(int index, int value) {
            array[index] = value;
        }


    }

    public static class Urejanje{

        int C = 0;
        int M = 0;

        int smer;

        ResizableArray<Integer> zaporedje = new ResizableArrasImpl<>();
        ResizableArray<Integer> count = new ResizableArrasImpl<>();

        String[] ukazi = new String[3];

        public boolean compare(int st1, int st2, int smer) {
            C++;
            return smer * st1 > st2 * smer;

        }

        public boolean compare_merge(int st1, int st2, int smer){
            C++;
            return smer * st1 >= st2 * smer;
        }

        public ResizableArray<Integer> partition(ResizableArray<Integer> arr, int start, int end){
            ResizableArray<Integer> temp = new ResizableArrasImpl<>();
            for (int i = start; i <= end; i++) {
                temp.add(arr.get(i));
            }
            return temp;
        }

        public void siftDown(ResizableArray<Integer> array, int size, int i){
            int max = i;
            int levi = 2 * i + 1;
            int desni = 2 * i + 2;

            if (levi < size && compare(zaporedje.get(levi), zaporedje.get(max),smer)){
                max = levi;
            }
            if (desni < size && compare(zaporedje.get(desni), zaporedje.get(max),smer)){
                max = desni;
            }
            if (max != i){
                swap(array, max,i);
                siftDown(zaporedje, size, max);
            }
        }

        public void swap(ResizableArray<Integer> a, int i, int m){
            int temp = a.get(i);
            a.set(i,a.get(m));
            a.set(m, temp);
            M += 3;
        }


        public void Napolni(){

            Scanner vrstica = new Scanner(System.in);
            String ukaz = vrstica.nextLine();
            Scanner beseda = new Scanner(ukaz);
            int i = 0;
            while (beseda.hasNext()){
                ukazi[i] = beseda.next();
                i++;
            }
            String stevilke = vrstica.nextLine();
            Scanner stevilka = new Scanner(stevilke);
            while (stevilka.hasNext()){
                zaporedje.add(Integer.valueOf(stevilka.next()));
            }

        }

        public void Count_izpis(){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < count.size(); i+=2) {
                if (i == count.size() - 2){
                    sb.append(count.get(i) + " " + count.get(i + 1));
                }
                else{
                    sb.append(count.get(i) + " " + count.get(i + 1) + " | ");
                }
            }
            System.out.println(sb);
        }

        public void Count_insert(){
            Insert_count();
            Insert_count();
            this.smer *= -1;
            Insert_count();
            Count_izpis();
        }

        public void Insert_trace(){
            StringBuilder sb = new StringBuilder();
            sb.append(zaporedje.toString1(0,zaporedje.size()) + "\n");
            for (int i = 1; i < zaporedje.size(); i++) {
                int x = zaporedje.get(i);
                int index = i;
                while (index > 0 && compare(zaporedje.get(index - 1),x,this.smer)){
                    zaporedje.set(index, zaporedje.get(index - 1));
                    index--;
                }
                zaporedje.set(index,x);
                sb.append(zaporedje.toString1(0,i + 1) + " | ");
                if (zaporedje.toString1(i + 1,zaporedje.size()) != null){
                    sb.append(zaporedje.toString1(i + 1,zaporedje.size()) + "\n");
                }
            }
            System.out.println(sb);
        }

        public void Insert_count(){
            for (int i = 1; i < zaporedje.size(); i++) {
                int x = zaporedje.get(i);
                M++;
                int index = i;
                while (index > 0 && compare(zaporedje.get(index - 1),x,this.smer)){
                    zaporedje.set(index, zaporedje.get(index - 1));
                    M++;
                    index--;
                }
                zaporedje.set(index,x);
                M++;
            }
            count.add(M);
            count.add(C);
            M = 0;
            C = 0;
        }
        public void Select_trace(){
            StringBuilder sb = new StringBuilder();
            sb.append(zaporedje.toString1(0,zaporedje.size()) + "\n");
            for (int i = 0; i < zaporedje.size() - 1; i++) {
                int m = i;
                for (int j = i + 1; j < zaporedje.size(); j++) {
                    if (compare(zaporedje.get(m), zaporedje.get(j), this.smer)){
                        m = j;
                    }
                }
                swap(zaporedje,i,m);
                sb.append(zaporedje.toString1(0,i + 1) + " | ");
                if (zaporedje.toString1(i + 1,zaporedje.size()) != null){
                    sb.append(zaporedje.toString1(i + 1,zaporedje.size()) + "\n");
                }
            }
            System.out.println(sb);
        }

        public void Select_count(){
            for (int i = 0; i < zaporedje.size() - 1; i++) {
                int m = i;
                for (int j = i + 1; j < zaporedje.size(); j++) {
                    if (compare(zaporedje.get(m), zaporedje.get(j), this.smer)){
                        m = j;
                    }
                }
                swap(zaporedje,i,m);
            }
            count.add(M);
            count.add(C);
            M = 0;
            C = 0;
        }
        public void Count_select(){
            Select_count();
            Select_count();
            this.smer *= -1;
            Select_count();
            Count_izpis();
        }

        public void Bubble_trace() {
            StringBuilder sb = new StringBuilder();
            sb.append(zaporedje.toString1(0, zaporedje.size()) + "\n");
            int zadnjaZamenjava;
            for (int i = 0; i < zaporedje.size() - 1; i = zadnjaZamenjava) {
                zadnjaZamenjava = zaporedje.size() - 1;
                for (int j = zaporedje.size() - 2; j >= i ; j--) {
                    if (compare(zaporedje.get(j + 1), zaporedje.get(j), smer)){
                        swap(zaporedje,j,j + 1);
                        zadnjaZamenjava = j + 1;
                    }
                }
                sb.append(zaporedje.toString1(0, zadnjaZamenjava) + " | " +
                        zaporedje.toString1(zadnjaZamenjava, zaporedje.size()) + "\n");
            }
            System.out.println(sb);
        }

        public void Bubble_count(){
            int zadnjaZamenjava;
            for (int i = 0; i < zaporedje.size() - 1; i = zadnjaZamenjava) {
                zadnjaZamenjava = zaporedje.size() - 1;
                for (int j = zaporedje.size() - 2; j >= i ; j--) {
                    if (compare(zaporedje.get(j + 1), zaporedje.get(j), smer)){
                        swap(zaporedje,j,j + 1);
                        zadnjaZamenjava = j + 1;
                    }
                }
            }
            count.add(M);
            count.add(C);
            M = 0;
            C = 0;
        }

        public void Count_bubble(){
            Bubble_count();
            Bubble_count();
            this.smer *= -1;
            Bubble_count();
            Count_izpis();
        }

        public void Heapsort_trace(){
            StringBuilder sb = new StringBuilder();
            sb.append(zaporedje.toString1(0, zaporedje.size()) + "\n");
            int size = zaporedje.size();
            for (int i = size / 2 - 1; i >= 0 ; i--) {
                siftDown(zaporedje, size, i);
            }
            sb.append(zaporedje.toString1(0, zaporedje.size()) + " | " + "\n");
            for (int i = size - 1; i > 0 ; i--) {
                swap(zaporedje, 0, i);
                siftDown(zaporedje, i, 0);
                sb.append(zaporedje.toString1(0, i) + " | "
                        + zaporedje.toString1(i, zaporedje.size()) + "\n");
            }
            System.out.println(sb);
        }

        public void Heapsort_count(){

            int size = zaporedje.size();
            for (int i = size / 2 - 1; i >= 0 ; i--) {
                siftDown(zaporedje, size, i);
            }
            for (int i = size - 1; i > 0 ; i--) {
                swap(zaporedje, 0, i);
                siftDown(zaporedje, i, 0);
            }
            count.add(M);
            count.add(C);
            M = 0;
            C = 0;
        }

        public void Count_heapsort(){
            Heapsort_count();
            Heapsort_count();
            this.smer *= -1;
            Heapsort_count();
            Count_izpis();
        }

        public ResizableArray<Integer> Merge(ResizableArray<Integer> left, ResizableArray<Integer> right){
            ResizableArray<Integer> merged = new ResizableArrasImpl<>();
            int k = left.size();
            int l = right.size();

            for (int i = 0; i < k + l; i++) {
                merged.add(0);
            }

            int i = 0, j = 0, t = 0;
            while (i < k && j < l){
                if (compare_merge(right.get(j), left.get(i), this.smer)){
                    M+=2;
                    merged.set(t, left.get(i++));
                }
                else{
                    M+=2;
                    merged.set(t, right.get(j++));
                }
                t++;
            }
            while (i < k){
                M+=2;
                merged.set(t++, left.get(i++));
            }
            while (j < l){
                M+=2;
                merged.set(t++, right.get(j++));
            }

            return merged;
        }

        public ResizableArray<Integer> Mergesort_trace(ResizableArray<Integer> arr){
            if (arr.size() <= 1){
                return arr;
            }
            int middle = (arr.size() - 1) / 2;
            System.out.println(arr.toString1(0, middle + 1) + " | " + arr.toString1(middle + 1, arr.size()));
            ResizableArray<Integer> left = Mergesort_trace(partition(arr,0,middle));
            if (left.size() > 1){
                System.out.println(left.toString1(0, left.size()));
            }

            ResizableArray<Integer> right = Mergesort_trace(partition(arr, middle + 1, arr.size() - 1));

            if (right.size() > 1){
                System.out.println(right.toString1(0, right.size()));
            }

            return Merge(left, right);
        }

        public ResizableArray<Integer> Mergesort_count(ResizableArray<Integer> arr){
            if (arr.size() <= 1){
                return arr;
            }
            int middle = (arr.size() - 1) / 2;
            ResizableArray<Integer> left = Mergesort_count(partition(arr,0,middle));
            ResizableArray<Integer> right = Mergesort_count(partition(arr, middle + 1, arr.size() - 1));

            return Merge(left, right);
        }

        public void Count_merge(){
            ResizableArray<Integer> urejeno = Mergesort_count(zaporedje);
            count.add(M);
            count.add(C);
            M = 0;
            C = 0;
            Mergesort_count(urejeno);
            count.add(M);
            count.add(C);
            M = 0;
            C = 0;
            this.smer *= -1;
            Mergesort_count(urejeno);
            count.add(M);
            count.add(C);
            M = 0;
            C = 0;
            Count_izpis();
        }

        public int partition_quick(ResizableArray<Integer> a, int left, int right){
            int pivot = a.get(left);
            int l = left;
            int r = right + 1;
            while (true){
                do{
                    l++;
                }
                while(compare(pivot, a.get(l), this.smer) && l < right);
                do{
                    r--;
                }
                while(compare(a.get(r), pivot, this.smer));
                if (l >= r){
                    break;
                }
                swap(a,l,r);
            }
            M++;
            swap(a,left,r);
            return r;
        }

        public void Quicksort(ResizableArray<Integer> a, int left, int right){
            if (left >= right) {
                return;
            }
            int r = partition_quick(a, left, right);
            QuickTrace(a, r, left, right + 1);
            Quicksort(a, left, r - 1);
            Quicksort(a, r + 1, right);

        }

        public void Quicksort_count(ResizableArray<Integer> a, int left, int right){
            if (left >= right) {
                return;
            }
            int r = partition_quick(a, left, right);
            Quicksort_count(a, left, r - 1);
            Quicksort_count(a, r + 1, right);
        }

        public void Count_quick(){
            Quicksort_count(zaporedje,0, zaporedje.size() - 1);
            count.add(M);
            count.add(C);
            M = 0;
            C = 0;
            Quicksort_count(zaporedje, 0, zaporedje.size() - 1);
            count.add(M);
            count.add(C);
            M = 0;
            C = 0;
            this.smer *= -1;
            Quicksort_count(zaporedje, 0, zaporedje.size() - 1);
            count.add(M);
            count.add(C);
            M = 0;
            C = 0;
            Count_izpis();
        }

        public void QuickTrace(ResizableArray<Integer> a, int r, int left, int right){
            StringBuilder sb = new StringBuilder();
            for (int i = left; i < right; i++) {
                if (i == r){
                    sb.append("| ");
                }
                sb.append(a.get(i) + " ");
                if (i == r){
                    sb.append("| ");
                }
            }
            System.out.println(sb);;
        }

        int getMax(ResizableArray<Integer> a, int n){
            int max = a.get(0);
            for (int i = 0; i < n; i++) {
                if (a.get(i) > max){
                    max = a.get(i);
                }
            }
            return max;
        }

        int getMin(ResizableArray<Integer> a, int n){
            int min = a.get(0);
            for (int i = 0; i < n; i++) {
                if (a.get(i) < min){
                    min = a.get(i);
                }
            }
            return min;
        }

        void countingSort_asc(ResizableArray<Integer> a, int n, int place){
            int[] output = new int[n + 1];
            int[] count = new int[10];

            for (int i = 0; i < n; i++) {
                count[(a.get(i) / place) % 10]++;
            }

            for (int i = 1; i < 10; i++) {
                count[i] += count[i - 1];
            }

            for (int i = n - 1; i >= 0 ; i--) {
                C+=2;
                output[count[(a.get(i) / place) % 10] - 1] = a.get(i);
                count[(a.get(i) / place) % 10]--;
            }

            for (int i = 0; i < n; i++) {
                M+=2;
                a.set(i, output[i]);
            }
        }

        void countingSort_desc(ResizableArray<Integer> a, int n, int place){
            int[] output = new int[n];
            int[] count = new int[10];

            for(int i=0; i<10; i++)
                count[i]=0;

            for (int i = 0; i < n; i++) {
                count[9 - a.get(i) / place % 10]++;
            }

            for (int i = 1; i < 10; i++) {
                count[i] += count[i - 1];
            }

            for (int i = n - 1; i >= 0 ; i--) {
                C+=2;
                output[count[9 - a.get(i) / place % 10] - 1] = a.get(i);
                count[9 - a.get(i) / place % 10]--;
            }

            for (int i = 0; i < n; i++) {
                M+=2;
                a.set(i, output[i]);
            }
        }

        void radixsort(ResizableArray<Integer> a, int n){
            int max = getMax(a,n);
            for (int place = 1; max/place > 0; place *= 10) {
                System.out.println(a.toString1(0, a.size()));
                if (this.smer == 1){
                    countingSort_asc(a,n,place);
                }
                else{
                    countingSort_desc(a,n,place);
                }
            }
            System.out.println(a.toString1(0, a.size()));
        }

        void radixsort_count(ResizableArray<Integer> a, int n){
            int max = getMax(a,n);
            for (int place = 1; max/place > 0; place *= 10) {
                if (this.smer == 1){
                    countingSort_asc(a,n,place);
                }
                else{
                    countingSort_desc(a,n,place);
                }
            }
        }

        public void Count_radix(){
            radixsort_count(zaporedje, zaporedje.size());
            count.add(M);
            count.add(C);
            M = 0;
            C = 0;
            radixsort_count(zaporedje, zaporedje.size());
            count.add(M);
            count.add(C);
            M = 0;
            C = 0;
            this.smer *= -1;
            radixsort_count(zaporedje, zaporedje.size());
            count.add(M);
            count.add(C);
            M = 0;
            C = 0;
            Count_izpis();
        }

        public void Izpis(){

            String delovanje = ukazi[0];
            String urejanje = ukazi[1];
            String smer = ukazi[2];

            if (urejanje.equals("insert")){
                if (delovanje.equals("trace")){
                    if (smer.equals("up")){
                        this.smer = 1;
                    }
                    else{
                        this.smer = -1;
                    }
                    Insert_trace();
                }
                else{
                    if (smer.equals("up")){
                        this.smer = 1;
                    }
                    else{
                        this.smer = -1;
                    }
                    Count_insert();
                }
            }
            else if (urejanje.equals("select")) {
                if (delovanje.equals("trace")){
                    if (smer.equals("up")){
                        this.smer = 1;
                    }
                    else{
                        this.smer = -1;
                    }
                    Select_trace();
                }
                else{
                    if (smer.equals("up")){
                        this.smer = 1;
                    }
                    else{
                        this.smer = -1;
                    }
                    Count_select();
                }
            }
            else if (urejanje.equals("bubble")) {
                if (delovanje.equals("trace")){
                    if (smer.equals("up")){
                        this.smer = -1;
                    }
                    else{
                        this.smer = 1;
                    }
                    Bubble_trace();
                }
                else{
                    if (smer.equals("up")){
                        this.smer = -1;
                    }
                    else{
                        this.smer = 1;
                    }
                    Count_bubble();
                }
            }
            else if (urejanje.equals("heap")) {
                if (delovanje.equals("trace")){
                    if (smer.equals("up")){
                        this.smer = 1;
                    }
                    else{
                        this.smer = -1;
                    }
                    Heapsort_trace();
                }
                else{
                    if (smer.equals("up")){
                        this.smer = 1;
                    }
                    else{
                        this.smer = -1;
                    }
                    Count_heapsort();
                }
            }
            else if (urejanje.equals("merge")) {
                if (delovanje.equals("trace")){
                    if (smer.equals("up")){
                        this.smer = 1;
                    }
                    else{
                        this.smer = -1;
                    }
                    System.out.println(zaporedje.toString1(0, zaporedje.size()));
                    System.out.println(Mergesort_trace(zaporedje).toString1(0, zaporedje.size()));
                }
                else{
                    if (smer.equals("up")){
                        this.smer = 1;
                    }
                    else{
                        this.smer = -1;
                    }
                    Count_merge();
                }
            }
            else if (urejanje.equals("quick")) {
                if (delovanje.equals("trace")){
                    if (smer.equals("up")){
                        this.smer = 1;
                    }
                    else{
                        this.smer = -1;
                    }
                    System.out.println(zaporedje.toString1(0, zaporedje.size()));
                    Quicksort(zaporedje,0, zaporedje.size() - 1);
                    System.out.println(zaporedje.toString1(0, zaporedje.size()));

                }
                else{
                    if (smer.equals("up")){
                        this.smer = 1;
                    }
                    else{
                        this.smer = -1;
                    }
                    Count_quick();
                }
            }
            else if (urejanje.equals("radix")){
                if (delovanje.equals("trace")){
                    if (smer.equals("up")){
                        this.smer = 1;
                    }
                    else{
                        this.smer = -1;
                    }
                    radixsort(zaporedje, zaporedje.size());
                }
                else{
                    if (smer.equals("up")){
                        this.smer = 1;
                    }
                    else{
                        this.smer = -1;
                    }
                    Count_radix();
                }
            }
        }
    }


    public static void main(String[] args) {
        Urejanje u = new Urejanje();
        u.Napolni();
        u.Izpis();
    }
}
