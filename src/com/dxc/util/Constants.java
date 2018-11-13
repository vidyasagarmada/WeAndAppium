package com.dxc.util;

public interface Constants {
	public String dateType1 = "MMM_dd_yyyy_HH_mm_ss_a_z";
	public String dateType2 = "MM/dd/yyyy HH:mm:ss";
	public String dateType3 = "dd_MMM_yyyy_hh_mm_ss_SSS_aa";
	public String dateType4 = "dd_MMM_yyyy_HH_mm_ss";
	String filenameappender = "TESTREPORT_" + CustomReporter.getDateFormat(dateType3);
}