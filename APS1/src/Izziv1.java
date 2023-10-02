public class Izziv1 {
    public static void main(String[] args) throws CollectionException {
        Stack<String> s = new ArrayDeque<>();
        Sequence<String> z = new ArrayDeque<>();

        System.out.println("STACK:\n");
        System.out.println("Push A,B,C:");
        s.push("A");
        s.push("B");
        s.push("C");
        System.out.println("s = " + s + "\n");
        System.out.println("Top");
        System.out.println(s.top() + "\n");
        System.out.println("Pop");
        s.pop();
        System.out.println("s = " + s + "\n");
        System.out.println("--------------------------------");
        System.out.println("SEQUENCE\n");
        System.out.println("Add: A,B,C");
        z.add("A");
        z.add("B");
        z.add("C");
        System.out.println("z = " + z + "\n");
        System.out.println("Get(2)");
        System.out.println(z.get(2));

    }
}
