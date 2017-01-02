/**
 * licenseInfo : Class to store Matlab license information
 *  
 * Class written to support mstat main program.
 * 
 * Author:
 * Peter A. Rochford
 * Symplectic, LLC
 * prochford@thesymplectic.com
 * www.thesymplectic.com
 *
 * Version 1.0, 2/15/2015
 */
package mstat;

import java.util.StringTokenizer;

public class licenseInfo {
	private int numIssued; // number of licenses available
    private int numUsed;   // number of licenses in use
	private String type = null; // type of Matlab license available, e.g. "floating license"
	private String toolbox = null; // Matlab or toolbox name
    
    public licenseInfo(){
        numIssued = 0;
        numUsed = 0;
        type = "";
        toolbox = "";
    }

    public licenseInfo(int issued, int used){
        numIssued = issued;
        numUsed = used;
    }
 
    public licenseInfo(String issued, String used){
    	this();
    	numIssued = getValue(issued,"issued");
    	numUsed = getValue(used,"used");
    }
    
    public licenseInfo(String issued, String used, String type){
    	this(issued, used);
    	this.type = type;
    }

    public licenseInfo(String record){
		// Tokenize license information
		StringTokenizer info = new StringTokenizer(record);
		int ntokens = info.countTokens();
		if (ntokens != 14) {
			System.out.println("User license information not available.");
			System.exit(0);
		}

		// Determine toolbox in use
		for (int i = 0; i < 3; i++) {
			toolbox = info.nextToken();
			int index = toolbox.indexOf(":");
			if(index != -1){
				toolbox = toolbox.substring(0,index);
			}
		}

		String issued = "";
		for (int i = 0; i < 3; i++) {
			issued = info.nextToken(); 
		}
		String used = "";
		for (int i = 0; i < 5; i++) {
			used = info.nextToken(); 
		}

		numIssued = getValue(issued,"issued");
    	numUsed = getValue(used,"used");
    	type = "";
    }

    private int getValue(String value, String word){
    	int number = 0;
    	try {
    		number = Integer.parseInt(value);
    	} catch (NumberFormatException e) {
    		System.out.println("Cannot determine number of " + word.trim() + 
    				" licences.");
    		System.exit(1);
    	}
    	return number;
    }

	public int getNumIssued() {
		return numIssued;
	}

	public int getNumUsed() {
		return numUsed;
	}

	public String getToolbox() {
		return toolbox;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String plural(){
		String plural = "";
		if(numUsed > 1){
			plural = "s";
		}
		return plural;
	}

	public String toString() {
		if(type.length() > 0){
			return numUsed + " used out of " + numIssued + " issued " + type + plural();			
		} else{
			return numUsed + " used out of " + numIssued + " issued";						
		}
	}
}

/*
Copyright © 2015, Peter A. Rochford, Symplectic, LLC
prochford@thesymplectic.com, www.thesymplectic.com

All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in
      the documentation and/or other materials provided with the distribution

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
*/
