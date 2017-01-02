/**
 * MatlabUser : Class to store information on users occupying Matlab licenses
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

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.StringTokenizer;

public class MatlabUser implements Comparable<MatlabUser> {
	private String username; // license user name
	private Calendar dateTime; // calendar date and time of license start
	private double elapseTime; // elapsed time
	private int stringLength;  // length to use writing string

	public MatlabUser(){
		this.username = "";
		this.dateTime = Calendar.getInstance();
		this.elapseTime = 0.0;
		this.stringLength = 0;
	}

	public MatlabUser(String record){
		// Tokenize record of user running license
		StringTokenizer info = new StringTokenizer(record);
		setUsername(info.nextToken());
		
		// Extract date & time
		String calendarMonthDay = "";
		for (int i = 0; i < 8; i++) {
			calendarMonthDay = info.nextToken();
		}
		String calendarTime = info.nextToken();

		// Set date & time
		Calendar cal = Calendar.getInstance();
		getDate(calendarMonthDay,cal);
		getTime(calendarTime,cal);
		this.dateTime = cal;
		
		// Calculate elapse time
		this.elapseTime = calcElapseTime(cal);
	}

	private void getDate(String calendarMonthDay, Calendar cal) {
		int dayOfMonth, month;
		
		StringTokenizer info = new StringTokenizer(calendarMonthDay,"/");
		try {
			month = Integer.parseInt(info.nextToken()) - 1;
			cal.set(Calendar.MONTH,month);
		} catch (NumberFormatException e) {
			System.out.println("Cannot determine month of user start.");
			System.exit(1);
		}
		try {
			dayOfMonth = Integer.parseInt(info.nextToken());
			cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
		} catch (NumberFormatException e) {
			System.out.println("Cannot determine day of user start.");
			System.exit(1);
		}
	}

	private void getTime(String calendarTime, Calendar cal) {
		int hourOfDay, minute;
		
		StringTokenizer info = new StringTokenizer(calendarTime,":");
		try {
			hourOfDay = Integer.parseInt(info.nextToken());
			cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
		} catch (NumberFormatException e) {
			System.out.println("Cannot determine hour of user start.");
			System.exit(1);
		}
		try {
			minute = Integer.parseInt(info.nextToken());
			cal.set(Calendar.MINUTE,minute);
		} catch (NumberFormatException e) {
			System.out.println("Cannot determine day of user start.");
			System.exit(1);
		}
		cal.set(Calendar.SECOND,0);
	}

	private double calcElapseTime(Calendar cal) {
		Date d2 = Calendar.getInstance().getTime();
		Date d1 = cal.getTime();
		
		// time in hours
		double time =((double) (d2.getTime() - d1.getTime())) / (60 * 60 * 1000);
		return time;		
	}
	
	public static String fixedLengthString(String string, int length) {
	    return String.format("%1$"+length+ "s", string);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		this.stringLength = username.length();
	}

	public Calendar getDateTime() {
		return dateTime;
	}

	public void setDateTime(Calendar dateTime) {
		this.dateTime = dateTime;
	}

	public double getElapseTime() {
		return elapseTime;
	}

	public void setElapseTime(double elapseTime) {
		this.elapseTime = elapseTime;
	}

	public int getStringLength() {
		return stringLength;
	}

	public void setStringLength(int stringLength) {
		this.stringLength = stringLength;
	}

	public int compareTo(MatlabUser user) {
        /* For Ascending order */
        return this.username.compareTo(user.getUsername());
    }

	/* Display Matlab user information in a nice format where the
	 * left column with the user name is right justified and the 
	 * remaining columns of time line up as shown below.
	 * 
	 * alexander.kurapov.lx : start Fri Jan 30 08:44:00 EST 2015 (416.3 hours)
	 *           lei.shi.lx : start Wed Jan 28 10:11:00 EST 2015 (462.9 hours)
	 *       zizang.yang.lx : start Fri Jan 30 08:52:00 EST 2015 (416.2 hours)
	 * 
	 */
	public String toString() {
		DecimalFormat elapseFormat = new DecimalFormat("0.0");
		String name = fixedLengthString(username, stringLength);
		return name + " : start " + dateTime.getTime() + " (" + 
		elapseFormat.format(elapseTime) + " hours)";
	}
}

class TimeComparator implements Comparator<MatlabUser>{
	/**
	 * TimeComparator : Class to compare elapsed time of users occupying Matlab licenses by
	 *                  decreasing value
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
    @Override
    public int compare(MatlabUser user1, MatlabUser user2) {
        return (user1.getElapseTime() < user2.getElapseTime() ) ? 1: 
        	(user1.getElapseTime() > user2.getElapseTime()) ? -1:0 ;
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
