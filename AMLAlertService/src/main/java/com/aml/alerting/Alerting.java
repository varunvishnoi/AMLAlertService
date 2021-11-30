package com.aml.alerting;

/*Alerting interface can be implemented to different alerting systems:
Few Options can be:
Splunk,
Geneos,
Email,
SMS,
Push Notification,
Writing to log File,
Printing to Console etc*/
public interface Alerting {
    public void alert();
}
