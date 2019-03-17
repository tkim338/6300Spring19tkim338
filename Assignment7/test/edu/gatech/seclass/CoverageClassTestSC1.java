package edu.gatech.seclass;

import org.junit.*;

public class CoverageClassTestSC1 {
	CoverageClass cc;

	@Test
	public void testStatement1() {
		cc = new CoverageClass();
		cc.coverageMethod1(true);
	}
}