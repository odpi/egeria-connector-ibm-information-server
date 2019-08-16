/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleRepeat {

    @JacksonXmlProperty(isAttribute = true) private String second;
    @JacksonXmlProperty(isAttribute = true) private String minute;
    @JacksonXmlProperty(isAttribute = true) private String hour;
    @JacksonXmlProperty(isAttribute = true) private String dayOfMonth;
    @JacksonXmlProperty(isAttribute = true) private String month;
    @JacksonXmlProperty(isAttribute = true) private String dayOfWeek;
    @JacksonXmlProperty(isAttribute = true) private String year;

    public String getSecond() { return second; }
    public void setSecond(String second) { this.second = second; }

    public String getMinute() { return minute; }
    public void setMinute(String minute) { this.minute = minute; }

    public String getHour() { return hour; }
    public void setHour(String hour) { this.hour = hour; }

    public String getDayOfMonth() { return dayOfMonth; }
    public void setDayOfMonth(String dayOfMonth) { this.dayOfMonth = dayOfMonth; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    @Override
    public String toString() {
        return "{ \"second\": \"" + second
                + "\", \"minute\": \"" + minute
                + "\", \"hour\": \"" + hour
                + "\", \"dayOfMonth\": \"" + dayOfMonth
                + "\", \"month\": \"" + month
                + "\", \"dayOfWeek\": \"" + dayOfWeek
                + "\", \"year\": \"" + year
                + "\" }";
    }

}
