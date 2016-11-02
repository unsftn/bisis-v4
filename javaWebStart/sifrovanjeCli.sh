#!/bin/sh
##

jarsigner -keystore ftninformatika.jks -storepass ftninformatika -keypass ftninformatika -tsa http://timestamp.digicert.com bisis-client.jar ftninformatika

jarsigner -keystore ftninformatika.jks -storepass ftninformatika -keypass ftninformatika -tsa http://timestamp.digicert.com bisis-textsrv.jar ftninformatika

jarsigner -keystore ftninformatika.jks -storepass ftninformatika -keypass ftninformatika -tsa http://timestamp.digicert.com bisis-config.jar ftninformatika

jarsigner -keystore ftninformatika.jks -storepass ftninformatika -keypass ftninformatika -tsa http://timestamp.digicert.com bisis-records.jar ftninformatika

jarsigner -keystore ftninformatika.jks -storepass ftninformatika -keypass ftninformatika -tsa http://timestamp.digicert.com bisis-utils.jar ftninformatika

jarsigner -keystore ftninformatika.jks -storepass ftninformatika -keypass ftninformatika -tsa http://timestamp.digicert.com bisis-reports.jar ftninformatika

