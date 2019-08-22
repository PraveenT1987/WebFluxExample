package com.mkyong.reactive;

interface I {
	default void add() {
		System.out.println("From I interface");
	}
	static void show() {
		System.out.println("Static method From I interface");
	}
}

interface J {
	default void add() {
		System.out.println("From J interface");
	}
}

public class DefaultStaticMethods extends Javaeighttest2 implements I,J{

	public static void main(String[] args) {
		DefaultStaticMethods javaeighttest = new DefaultStaticMethods();
		javaeighttest.add();
		I.show();
	}

	@Override
	public void add() {
		super.add();
	}

}

class Javaeighttest2 {
	void add() {
		System.out.println("From Javaeighttest2 class ");
	}
}
