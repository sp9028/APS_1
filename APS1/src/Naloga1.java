import java.util.Objects;
import java.util.Scanner;


public class Naloga1 {

    interface Collection {
        static final String ERR_MSG_EMPTY = "Collection is empty.";
        static final String ERR_MSG_FULL = "Collection is full.";

        boolean isEmpty();

        boolean isFull();

        int size();

        String toString1();
        String Printextra();
    }

    interface Stack<T> extends Collection {
        T top();

        void push(T x);

        T pop();

    }

    interface Sequence<T> extends Collection {
        static final String ERR_MSG_INDEX = "Wrong index in sequence.";

        T get(int i);

        void add(T x);
    }

    @SuppressWarnings("unchecked")
    static class ImplementacijaSklada<T> implements Stack<T> {
        private static final int DEFAULT_CAPACITY = 64;

        @Override
        public String toString1() {
            StringBuffer sb = new StringBuffer();
            if (size > 0){
                sb.append(sklad[front].toString());
            }
            for (int i = 0; i < size - 1; i++) {
                sb.append(" " + sklad[next(front + i)].toString());
            }
            return sb.toString();
        }

        @Override
        public String Printextra() {
            StringBuffer sb = new StringBuffer();
            if (size > 0) {
                sb.append(sklad[back - 1].toString());
            }
            for (int i = 1; i < size; i++) {
                sb.append(" " + sklad[prev(back - i)].toString());
            }
            return sb.toString();
        }


        @Override
        public boolean isEmpty() {
            return (size == 0);
        }

        @Override
        public boolean isFull() {
            return (size == DEFAULT_CAPACITY);
        }

        @Override
        public int size() {
            return size;
        }

        private int next(int i) {
            return (i + 1) % DEFAULT_CAPACITY;
        }

        private int prev(int i) {
            return (DEFAULT_CAPACITY + i - 1) % DEFAULT_CAPACITY;
        }


        @Override
        public T top(){
            return sklad[prev(back)];
        }

        @Override
        public void push(T x) {

            sklad[back] = x;
            back = next(back);
            size++;
        }

        @Override
        public T pop(){
            back = prev(back);
            T o = sklad[back];
            sklad[back] = null;
            size--;
            return o;
        }

        private T[] sklad;
        private int front, back, size;

        public ImplementacijaSklada() {
            this.sklad = (T[]) (new Object[DEFAULT_CAPACITY]);
            this.front = 0;
            this.back = 0;
            this.size = 0;

        }

    }
    @SuppressWarnings("unchecked")
    static class ImplementacijaSekvence<T> implements Sequence<T> {
        private static final int DEFAULT_CAPACITY = 42;

        @Override
        public String toString1(){
            StringBuffer sb = new StringBuffer();
            if (size > 0){
                sb.append(sekvenca[front].toString());
            }
            for (int i = 0; i < size - 1; i++) {
                sb.append(", " + sekvenca[next(front + i)].toString());
            }
            return sb.toString();
        }

        @Override
        public String Printextra() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return (size == 0);
        }

        @Override
        public boolean isFull() {
            return (size == DEFAULT_CAPACITY);
        }

        @Override
        public int size() {
            return size;
        }

        private int next(int i) {
            return (i + 1) % DEFAULT_CAPACITY;
        }

        private int prev(int i) {
            return (DEFAULT_CAPACITY + i - 1) % DEFAULT_CAPACITY;
        }


        private int index(int i) {
            return (front + i) % DEFAULT_CAPACITY;
        }

        @Override
        public T get(int i) {

            return sekvenca[index(i)];

        }

        @Override
        public void add(T x) {

            sekvenca[back] = x;
            back = next(back);
            size++;
        }

        private T[] sekvenca;
        private int front, back, size;

        public ImplementacijaSekvence() {
            this.sekvenca = (T[])(new Object[DEFAULT_CAPACITY]);
            this.front = 0;
            this.back = 0;
            this.size = 0;
        }
    }

    static class Kalkulator {
        boolean pogoj = false;
        Sequence<Stack<String>> sekvenca = new ImplementacijaSekvence<>();
        Stack<String> glavni_sklad = sekvenca.get(0);
        public Kalkulator() {
            for (int i = 0; i < ImplementacijaSekvence.DEFAULT_CAPACITY; i++) {
                Stack<String> sklad = new ImplementacijaSklada<>();
                this.sekvenca.add(sklad);
            }
        }

        public String Echo(){
            if (sekvenca.get(0).top() == null){
                return "";
            }
            else {
                return sekvenca.get(0).top();
            }
        }

        public String Print() {
            int index = Integer.parseInt(sekvenca.get(0).pop());
            return sekvenca.get(index).Printextra();
        }

        public void Clear() {
            Stack<String> glavni_sklad = sekvenca.get(0);
            int index = Integer.parseInt(glavni_sklad.pop());
            while (!sekvenca.get(index).isEmpty()){
                sekvenca.get(index).pop();
            }
        }

        public void Pop() {
            sekvenca.get(0).pop();
        }

        public void Dup() {
            sekvenca.get(0).push(sekvenca.get(0).top());
        }

        public void Dup2() {
            String y = sekvenca.get(0).pop();
            String x = sekvenca.get(0).pop();
            for (int i = 0; i < 2; i++) {
                sekvenca.get(0).push(x);
                sekvenca.get(0).push(y);
            }

        }

        public void Swap() {
            String y = sekvenca.get(0).pop();
            String x = sekvenca.get(0).pop();
            sekvenca.get(0).push(y);
            sekvenca.get(0).push(x);
        }

        public void Reverse() {
            int index = Integer.parseInt(sekvenca.get(0).pop());
            Stack<String> s1 = new ImplementacijaSklada<>();
            Stack<String> s2 = new ImplementacijaSklada<>();

            while (!sekvenca.get(index).isEmpty()){
                s1.push(sekvenca.get(index).pop());
            }
            while (!s1.isEmpty()){
                s2.push(s1.pop());
            }
            while (!s2.isEmpty()){
                sekvenca.get(index).push(s2.pop());
            }
        }

        public void Even(){
            if (Math.abs(Integer.parseInt(sekvenca.get(0).top())) % 2 == 0){
                sekvenca.get(0).pop();
                sekvenca.get(0).push("1");
            }
            else {
                sekvenca.get(0).pop();
                sekvenca.get(0).push("0");
            }
        }

        public void Odd(){
            if (Math.abs(Integer.parseInt(sekvenca.get(0).top())) % 2 == 1){
                sekvenca.get(0).pop();
                sekvenca.get(0).push("1");
            }
            else {
                sekvenca.get(0).pop();
                sekvenca.get(0).push("0");
            }
        }

        public void NotEqual (){
            Stack<String> glavni_sklad = sekvenca.get(0);
            String y = glavni_sklad.pop();
            String x = glavni_sklad.pop();
            if (Objects.equals(x, y)){
                glavni_sklad.push("0");
            }
            else {
                glavni_sklad.push("1");
            }
        }

        public void LessThan (){
            Stack<String> glavni_sklad = sekvenca.get(0);
            String y = glavni_sklad.pop();
            String x = glavni_sklad.pop();
            if (Integer.parseInt(x) < Integer.parseInt(y)){
                glavni_sklad.push("1");
            }
            else {
                glavni_sklad.push("0");
            }
        }

        public void LessThanOrEqual (){
            Stack<String> glavni_sklad = sekvenca.get(0);
            String y = glavni_sklad.pop();
            String x = glavni_sklad.pop();
            if (Integer.parseInt(x) <= Integer.parseInt(y)){
                glavni_sklad.push("1");
            }
            else {
                glavni_sklad.push("0");
            }
        }

        public void Equal (){
            Stack<String> glavni_sklad = sekvenca.get(0);
            String y = glavni_sklad.pop();
            String x = glavni_sklad.pop();
            if (Integer.parseInt(x) == Integer.parseInt(y)){
                glavni_sklad.push("1");
            }
            else {
                glavni_sklad.push("0");
            }
        }

        public void GreaterThan (){
            Stack<String> glavni_sklad = sekvenca.get(0);
            String y = glavni_sklad.pop();
            String x = glavni_sklad.pop();
            if (Integer.parseInt(x) > Integer.parseInt(y)){
                glavni_sklad.push("1");
            }
            else {
                glavni_sklad.push("0");
            }
        }

        public void GreaterThanOrEqual (){
            Stack<String> glavni_sklad = sekvenca.get(0);
            String y = glavni_sklad.pop();
            String x = glavni_sklad.pop();
            if (Integer.parseInt(x) >= Integer.parseInt(y)){
                glavni_sklad.push("1");
            }
            else {
                glavni_sklad.push("0");
            }
        }

        public void Vsota() {
            Stack<String> glavni_sklad = sekvenca.get(0);
            String y = glavni_sklad.pop();
            String x = glavni_sklad.pop();
            int rezultat = Integer.parseInt(x) + Integer.parseInt(y);
            glavni_sklad.push(Integer.toString(rezultat));
        }

        public void Razlika() {
            Stack<String> glavni_sklad = sekvenca.get(0);
            String y = glavni_sklad.pop();
            String x = glavni_sklad.pop();
            int rezultat = Integer.parseInt(x) - Integer.parseInt(y);
            glavni_sklad.push(Integer.toString(rezultat));
        }

        public void Mnozenje() {
            Stack<String> glavni_sklad = sekvenca.get(0);
            String y = glavni_sklad.pop();
            String x = glavni_sklad.pop();
            int rezultat = Integer.parseInt(x) * Integer.parseInt(y);
            glavni_sklad.push(Integer.toString(rezultat));
        }

        public void Deljenje() {
            Stack<String> glavni_sklad = sekvenca.get(0);
            String y = glavni_sklad.pop();
            String x = glavni_sklad.pop();
            int rezultat = Integer.parseInt(x) / Integer.parseInt(y);
            glavni_sklad.push(Integer.toString(rezultat));
        }

        public void Ostanek() {
            Stack<String> glavni_sklad = sekvenca.get(0);
            String y = glavni_sklad.pop();
            String x = glavni_sklad.pop();
            int rezultat = Integer.parseInt(x) % Integer.parseInt(y);
            glavni_sklad.push(Integer.toString(rezultat));
        }

        public int Factorial(int st){
            if (st >= 1)
                return st * Factorial(st - 1);
            else
                return 1;
        }

        public void Faktoriela(){
            Stack<String> glavni_sklad = sekvenca.get(0);
            String vrh = glavni_sklad.pop();
            glavni_sklad.push(Integer.toString(Factorial(Integer.parseInt(vrh))));
        }

        public void Rnd() {
            Stack<String> glavni_sklad = sekvenca.get(0);
            int max = Integer.parseInt(glavni_sklad.pop());
            int min = Integer.parseInt(glavni_sklad.pop());
            if (min > max){
                int rnd = (int)(Math.random() * ((min - max) + 1)) + max;
                glavni_sklad.push(Integer.toString(rnd));
            }
            else {
                int rnd = (int)(Math.random() * ((max - min) + 1)) + min;
                glavni_sklad.push(Integer.toString(rnd));
            }
        }

        public void Len() {
            Stack<String> glavni_sklad = sekvenca.get(0);
            String vrh = glavni_sklad.pop();
            glavni_sklad.push(Integer.toString(vrh.length()));
        }

        public void Zlepi(){
            Stack<String> glavni_sklad = sekvenca.get(0);
            String y = glavni_sklad.pop();
            String x = glavni_sklad.pop();
            String rez = x + y;
            glavni_sklad.push(rez);
        }

        public void Char(){
            Stack<String> glavni_sklad = sekvenca.get(0);
            int vrh = Integer.parseInt(glavni_sklad.pop());
            char c = (char)vrh;
            glavni_sklad.push(String.valueOf(c));
        }

         public void Then(){
             Stack<String> glavni_sklad = sekvenca.get(0);
             String vrh = glavni_sklad.pop();
             if (!vrh.equals("0")){
                 pogoj = true;
             }
             else {
                 pogoj = false;
             }
         }

         public void Else(){
            boolean temp_bool = pogoj;
            pogoj = !temp_bool;
         }

         public void Move() {
             Stack<String> glavni_sklad = sekvenca.get(0);
             int index = Integer.parseInt(glavni_sklad.pop());
             int st = Integer.parseInt(glavni_sklad.pop());
             for (int i = 0; i < st; i++) {
                 sekvenca.get(index).push(glavni_sklad.pop());
             }
         }

         public void Fun(Scanner s){
             Stack<String> glavni_sklad = sekvenca.get(0);
             int index = Integer.parseInt(glavni_sklad.pop());
             int st = Integer.parseInt(glavni_sklad.pop());
             for (int i = 0; i < st; i++) {
                 String niz = s.next();
                 sekvenca.get(index).push(niz);
             }
         }
         
         public void Run() {
             Stack<String> glavni_sklad = sekvenca.get(0);
             int index = Integer.parseInt(glavni_sklad.pop());

             Scanner sc = new Scanner(sekvenca.get(index).toString1());
             while (sc.hasNext()) {
                 String niz = sc.next();
                 if (!Objects.equals(niz, " ")) {
                     if (niz.charAt(0) != '?' || pogoj) {
                         String novi_niz = niz.replace("?", "");
                         if (novi_niz.equals("fun")) {
                             Fun(sc);
                         }
                         else {
                             Izpis(novi_niz);
                         }
                     }
                 }

             }
         }

         public void Loop(){
             Stack<String> glavni_sklad = sekvenca.get(0);
             int index = Integer.parseInt(glavni_sklad.pop());
             int st = Integer.parseInt(glavni_sklad.pop());
             int ponovitve = 0;
             while (ponovitve < st){
                 Scanner sc = new Scanner(sekvenca.get(index).toString1());
                 while (sc.hasNext()) {
                     String niz = sc.next();
                     if (!Objects.equals(niz, " ")) {
                         if (niz.charAt(0) != '?' || pogoj) {
                             String novi_niz = niz.replace("?", "");
                             if (novi_niz.equals("fun")) {
                                 Fun(sc);
                             }
                             else {
                                 Izpis(novi_niz);
                             }
                         }
                     }
                 }
                 ponovitve++;
             }
         }


         public void Izpis(String niz){
            if (Objects.equals(niz, "echo")) {
                 System.out.println(Echo());
             }
             else if (Objects.equals(niz, "print")) {
                 System.out.println(Print());
             }
             else if (Objects.equals(niz, "clear")) {
                 Clear();
             }
             else if (Objects.equals(niz, "pop")){
                 Pop();
             }
             else if (Objects.equals(niz, "dup")){
                 Dup();
             }
             else if (Objects.equals(niz, "dup2")){
                 Dup2();
             }
             else if (Objects.equals(niz, "swap")){
                 Swap();
             }
             else if (Objects.equals(niz, "reverse")){
                 Reverse();
             }
             else if (Objects.equals(niz, "even")){
                 Even();
             }
             else if (Objects.equals(niz, "odd")){
                 Odd();
             }
             else if (Objects.equals(niz, "<>")){
                 NotEqual();
             }
             else if (Objects.equals(niz, "<")){
                 LessThan();
             }
             else if (Objects.equals(niz, "<=")){
                 LessThanOrEqual();
             }
             else if (Objects.equals(niz, "==")){
                 Equal();
             }
             else if (Objects.equals(niz, ">")){
                 GreaterThan();
             }
             else if (Objects.equals(niz, ">=")){
                 GreaterThanOrEqual();
             }
             else if (Objects.equals(niz, "+")){
                 Vsota();
             }
             else if (Objects.equals(niz, "-")){
                 Razlika();
             }
             else if (Objects.equals(niz, "*")){
                 Mnozenje();
             }
             else if (Objects.equals(niz, "/")){
                 Deljenje();
             }
             else if (Objects.equals(niz, "%")){
                 Ostanek();
             }
             else if (Objects.equals(niz, "!")){
                 Faktoriela();
             }
             else if (Objects.equals(niz, "rnd")){
                 Rnd();
             }
             else if (Objects.equals(niz, "len")){
                 Len();
             }
             else if (Objects.equals(niz, ".")){
                 Zlepi();
             }
             else if (Objects.equals(niz, "char")){
                 Char();
             }
             else if (Objects.equals(niz, "then")){
                 Then();
             }
             else if (Objects.equals(niz, "else")){
                 Else();
             }
            else if (Objects.equals(niz, "move")){
                Move();
            }
            else if (Objects.equals(niz, "run")){
                Run();
            }
            else if (Objects.equals(niz, "loop")){
                Loop();
            }

            else {
                 sekvenca.get(0).push(niz);
             }
         }
    }

    public static void main(String[] args) {
        Scanner v = new Scanner(System.in);
        while (v.hasNextLine()) {
            Kalkulator k1 = new Kalkulator();
            String ukaz = v.nextLine();
            Scanner s = new Scanner(ukaz);
            while (s.hasNext()){
                String niz = s.next();
                if (!Objects.equals(niz, " ")){
                    if (niz.charAt(0) != '?' || k1.pogoj){
                        String novi_niz = niz.replace("?","");
                        if (novi_niz.equals("fun")){
                            k1.Fun(s);
                        }
                        else {
                            k1.Izpis(novi_niz);
                        }
                    }
                }
            }
        }
    }
}
