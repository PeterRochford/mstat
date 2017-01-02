#!/bin/sh
#
# mstat : Shell script to report Matlab license status
#
# Displays concurrent Matlab licenses in use on a computer platform
# in a easily human readable form.
#
# The program parses output provided by the Flexible License Manager (lmstat, 
# Copyright (c) 1989-2013 Flexera Software LLC. All Rights Reserved.) that is 
# provided as part of MathWork's Matlab installation. The license manager 
# must be called using "lmstat -a".
#
# Note that the MATLABHOME variable must contain the path to the Matlab home
# directory and the MSTATHOME variable must contain the path to the directory
# containing the mstat.jar file.
# 
# Author:
# Peter A. Rochford
# Symplectic, LLC
# prochford@thesymplectic.com
# www.thesymplectic.com
#
# Version 1.0, 2/25/2015
# 
#MATLABHOME=/usr/local/MATLAB/R2014a
MSTATHOME=~peter.rochford.lx/utils/mstat
if [ $# -eq 0 ]; then
  # Run in default configuration
  $MATLABHOME/etc/lmstat -a | java -jar $MSTATHOME/mstat.jar
else
  # Use supplied command line argument
  $MATLABHOME/etc/lmstat -a | java -jar $MSTATHOME/mstat.jar $1
fi
