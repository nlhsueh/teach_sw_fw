package demo;

public class BiDirectionalAdapter implements A1, B1 {
    private A1 a;
    private B1 b;

    // Adapting B to A
    public BiDirectionalAdapter(B1 b) {
        this.b = b;
    }

    // Adapting A to B
    public BiDirectionalAdapter(A1 a) {
        this.a = a;
    }

    @Override
    public void m1() {
        if (a != null) {
            a.m1();
        } else if (b != null) {
            b.op1(); // Convert op1 to m1
        }
    }

    @Override
    public void op1() {
        if (b != null) {
            b.op1();
        } else if (a != null) {
            a.m1(); // Convert m1 to op1
        }
    }

    public static void main(String[] args) {
        A1 a = new BiDirectionalAdapter(new BB());
        a.m1();
        
        A1 x = new AA();
        x.m1();
        
        B1 b = new BiDirectionalAdapter(new AA());
        b.op1();
        
        B1 y = new BB();
        y.op1();
    }
}

interface A1 {
	public void m1();
}

interface B1 {
	public void op1(); 
}

class BB implements B1 {
	public void op1() {
		System.out.println("This is op1 by B1");
	}
}

class AA implements A1 {
	public void m1() {
		System.out.println("This is m1 by A1");
	}
}
