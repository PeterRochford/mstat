/**
 * mstat : Report Matlab license status
 *  
 * Program to display concurrent Matlab licenses in use on a computer platform
 * in a easily human readable form.
 * 
 * The program parses output provided by the Flexible License Manager (lmstat, 
 * Copyright (c) 1989-2013 Flexera Software LLC. All Rights Reserved.) that is 
 * provided as part of MathWork's Matlab installation. The license manager 
 * must be called using "lmstat -a".
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class mstat {

	/**
	 * Driver program to display running Matlab licenses.
	 * 
	 * If no command line arguments are provided, the program will sort the users
	 * alphabetically by user name.
	 * 
	 * If a "-t" command line argument is provided, the program will sort the users
	 * by decreasing elapsed time.
	 */

	public static void main(String[] args)
	{
		/* Set scanner input stream
		 *   standardInput = false : lmstat output read from file given by variable "filename"
		 *   standardInput = true  : lmstat output read from standard input, i.e. screen
		 */
		boolean standardInput = true;
		String folder = "/Users/Peter/Documents/workspace/mstat/src/mstat/";
		String filename = folder + "mstat2.txt"; // name of file containing lmstat output

		ArrayList<MatlabUser> user = new ArrayList<MatlabUser>(10);
		String licenseType = "", record = "";
		
		// Check for command-line arguments
		boolean timeSort = false;
		if(args.length > 0){
			switch (args[0]){
			case "-t":
				// Report users by longest elapse time
				timeSort = true;
				break;
			}
		}
		
		/* Set scanner input stream
		 *   false - lmstat output read from file given by variable "filename"
		 *   true  - lmstat output read from standard input, i.e. screen
		 */
		Scanner inputStream = setInputStream(standardInput, filename);

		// Process while there are input records
		boolean noInput = true;
		while (inputStream.hasNextLine()) {

			// Skip records until license information starts
			record = findUsers(inputStream);
			if(record.indexOf("Users of ") == -1){
				break; // exit while loop because no users found 
			} else{
				noInput = false; // input was provided
			}

			// Get license information 
			licenseInfo license = new licenseInfo(record);

			// Get license type
			if(license.getNumUsed() != 0) {
				for (int i = 0; i < 3; i++) {
					licenseType = inputStream.nextLine().trim();
				}
				license.setType(licenseType);
			}
			record = inputStream.nextLine(); // skip blank line

			// Report license summary
			System.out.println();
			System.out.println(license.getToolbox() + " Users (" + license + "):");

			if(license.getNumUsed() > 0){
				// Build list of users of Matlab licenses
				buildUserList(inputStream, user);
				
				/* Find maximum length of username strings so all user names
				 * can be right justified.
				 */
				int maxLength = maxLength(user);
				
				if(timeSort){
					// Sort user by elapsed time
					Collections.sort(user, new TimeComparator());
				} else{
					// Sort users alphabetically
					Collections.sort(user);
				}

				// Report users alphabetically
				for(MatlabUser one: user){
					one.setStringLength(maxLength); // to nicely format names
					System.out.println(one);
				}

				// Empty the user list
				user.clear();		
			}
		}
		
		// Report if input is empty
		if(noInput){
			System.out.println("No license information provided!");
			System.exit(0);
		}

		inputStream.close();
		System.exit(0);

	} // end main

    // Returns the input stream
    public static Scanner setInputStream(boolean standardInput, String filename) {
		Scanner inputStream = null;
		if(standardInput){
			// Input is from standard input
			inputStream = new Scanner(System.in);
		} else{
			// Input is from text file
			try {
				inputStream = new Scanner(new FileInputStream(filename));
			} catch (FileNotFoundException e) {
				System.out.println("File not found: " + filename);
				System.exit(0);
			}
		}

		return inputStream;
    }

    // Builds the list of users occupying licenses
    public static void buildUserList(Scanner inputStream, 
    		ArrayList<MatlabUser> user){
		int index = 0;
		while (inputStream.hasNextLine() && index != -1) {
			String record = inputStream.nextLine();
			index = record.indexOf("start");
			if(index != -1){
				MatlabUser one = new MatlabUser(record);
				user.add(one);
			}
		}
    }

    // Finds starting point where users are listed
    public static String findUsers(Scanner inputStream){
		int index = -1;
		String record = "";
		while (inputStream.hasNextLine() && index == -1) {
			record = inputStream.nextLine();
			index = record.indexOf("Users of ");
		}
		return record;
    }

	// Returns the length of the longest user name in the list
    public static int maxLength(ArrayList<MatlabUser> list) {
        int max = 0;
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i).getUsername();
            if (s.length() > max) {
                max = s.length();
            }
        }
        return max;
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

