#!/bin/sh
##

jarsigner -keystore bisis2015.jks -storepass bisis2015 -keypass bisis2015 -tsa http://timestamp.digicert.com bisis-client.jar bisis

jarsigner -keystore bisis2015.jks -storepass bisis2015 -keypass bisis2015 -tsa http://timestamp.digicert.com bisis-textsrv.jar bisis

jarsigner -keystore bisis2015.jks -storepass bisis2015 -keypass bisis2015 -tsa http://timestamp.digicert.com bisis-config.jar bisis

jarsigner -keystore bisis2015.jks -storepass bisis2015 -keypass bisis2015 -tsa http://timestamp.digicert.com bisis-records.jar bisis

jarsigner -keystore bisis2015.jks -storepass bisis2015 -keypass bisis2015 -tsa http://timestamp.digicert.com bisis-utils.jar bisis

jarsigner -keystore bisis2015.jks -storepass bisis2015 -keypass bisis2015 -tsa http://timestamp.digicert.com bisis-reports.jar bisis

