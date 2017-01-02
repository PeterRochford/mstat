# mstat
Display concurrent Matlab licenses in use on a computer platform in an easily human readable form.

The mstat application allows users to easily learn how many concurrent licenses are being used out of the total number available. This is particularly helpful for users when all concurrent licenses are being used and they wish to know who is occupying the licenses.
Mstat provides information on both the floating and toolbox licenses, alphabetically lists the users occupying the licenses, the start date, as well as the hours elapsed. Users may also be displayed by longest elapse time using the -t option. There is also a basic help (-h) option.

Shown below is sample output from mstat.

MATLAB Users (3 used out of 3 issued floating licenses):
jsmith : start Fri Jan 30 08:44:00 EST 2015 (1371.7 hours)
kelliott : start Wed Jan 28 10:11:00 EST 2015 (1418.3 hours)
zyang : start Fri Jan 30 08:52:00 EST 2015 (1371.6 hours)

Signal_Toolbox Users (1 used out of 2 issued floating license):
zyang : start Mon Feb 02 08:33:00 EST 2015 (1299.9 hours)

The mstat application parses output provided by the Flexible License Manager (lmstat) that is provided as part of MathWork's Matlab installation. The license manager must be called using "lmstat -a". (Note: lmstat is Copyright © 1989-2013 Flexera Software LLC. All Rights Reserved.)

The application is written in Java and requires access to a java compiler on the computer platform in which it is installed. The Java jar file (mstat.jar) should be portable across any operating system that has a Java Virtual Machine (JVM). The Java source code is provided in the event you need to compile the code or wish to add new features. Pease email the author any modified code if bugs are fixed, new features implemented, enhancements made, etc.

Mstat has only been tested on platforms running a linux operating system. The application should run on any platform for which the Java code can be built and executed. Please notify the author if you successfully build and run mstat on another platform.
