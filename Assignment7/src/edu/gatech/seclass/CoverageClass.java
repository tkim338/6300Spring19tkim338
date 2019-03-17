package edu.gatech.seclass;

public class CoverageClass {
	public int coverageMethod1(boolean b) {
		int x = 20, y = 0;
		if (b) y = 4;
		return x / y;
	}
	public void coverageMethod2() {
		/* It is not possible to create a method that contains a fault that is revealed by a test
		suite that achieves less than 100% statement coverage but not revealed by a test suite that
		achieves 100% path coverage because path coverage subsumes branch coverage, which subsumes
		statement coverage. Any fault revealed by full statement coverage will be revealed by full
		path coverage. */
	}
	public int coverageMethod3(boolean b) {
		int x = 20, y = 0;
		if (b) y = 4;
		return x / y;
	}
	public void coverageMethod4() {
		/* It is not possible to create a method that contains a fault that is revealed by a test
		suite that achieves 100% statement coverage but not revealed by a test suite that
		achieves 100% branch coverage because branch coverage subsumes statement coverage. Any
		fault revealed by full statement coverage will be revealed by full branch coverage. */
	}
	public boolean coverageMethod5 (boolean a, boolean b) {
		int x = 1;
		int y = 1;
		if(a)
			y -= x ;
		else
			x += y;
		if(b)
			y = x/y;
		else
			y = x*y;
		return (y == 0);
	}


	// ================
	//
	// Fill in column “output” with T, F, or E:
	//
	// | a | b |output|
	// ================
	// | T | T |   E  |
	// | T | F |   T  |
	// | F | T |   F  |
	// | F | F |   F  |
	// ================
	//
	// Fill in the blanks in the following sentences with
	// “NEVER”, “SOMETIMES” or “ALWAYS”:
	//
	// Test suites with 100% statement coverage SOMETIMES reveal the fault in this method.
	// Test suites with 100% branch coverage SOMETIMES reveal the fault in this method.
	// Test suites with 100% path coverage ALWAYS reveal the fault in this method.
	// ================

}