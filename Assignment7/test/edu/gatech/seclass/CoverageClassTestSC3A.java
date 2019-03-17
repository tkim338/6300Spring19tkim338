package edu.gatech.seclass;

import org.junit.Test;

public class CoverageClassTestSC3A {
	CoverageClass cc;

	@Test
	public void testBranch2() {
		cc = new CoverageClass();
		cc.coverageMethod1(false);
	}
}