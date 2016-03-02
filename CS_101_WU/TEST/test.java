public class test {
     public static void main(String[] args) {
     A a = new A("s");
     a.print();
   }
}

class A {
   String s;

   A(String s) {
     this.s = s;
   }

   void print() {
     System.out.println(s);
   }
}