package com.mpd.app;

import java.io.File;
import java.io.IOException;

public class JaxConverter {
	
    //Date,Open,High,Low,Close,Volume,Adj Close,StocYk
    //2016-02-11,1.35,1.37,1.29,1.31,2639700,1.31,FNMA
    //2016-02-10,1.39,1.42,1.37,1.38,1459400,1.38,FNMA

	public static void main(String[] args) throws IOException{
        File csvfile = new File("csv.file");
        XMLCreators xmlc = new XMLCreators();
        xmlc.convertFile("csv.file", "testfile.xml", ",");
	}
}
