import java.util.Collection;
import java.util.Stack;

public class Vaje_ADT {

    public static void main(String[] args) {
        Stack<Integer> s1 = new Stack<>();
        for (int i = 0; i < 10; i++) {
            s1.push(i);
        }
        Obrni(s1, 2, 3);
    }
    static void Obrni(Stack<Integer> s, int n, int m){
        Stack<Integer> s1 = new Stack<>();
        Stack<Integer> s2 = new Stack<>();

        for (int i = 0; i < n; i++) {
            s1.push(s.pop());
        }
        for (int i = 0; i < m; i++) {
            s2.push(s.pop());
        }
        while (!s2.empty()){
            s1.push(s2.pop());
        }
        while (!s1.empty()){
            s.push(s1.pop());
        }
    }


}
