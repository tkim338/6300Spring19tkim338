package edu.gatech.seclass;

import org.junit.Test;

public class CoverageClassTestSC3B {
	CoverageClass cc;

	@Test
	public void testStatement1() {
		cc = new CoverageClass();
		cc.coverageMethod1(true);
	}
}