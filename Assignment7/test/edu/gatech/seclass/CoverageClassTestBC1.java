package edu.gatech.seclass;

import org.junit.*;

public class CoverageClassTestBC1 {
	CoverageClass cc;

	@Test
	public void testBranch1() {
		cc = new CoverageClass();
		cc.coverageMethod1(true);
	}

	@Test
	public void testBranch2() {
		cc = new CoverageClass();
		cc.coverageMethod1(false);
	}
}