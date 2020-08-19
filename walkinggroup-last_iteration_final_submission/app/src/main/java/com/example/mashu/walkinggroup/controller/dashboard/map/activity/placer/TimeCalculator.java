package com.example.mashu.walkinggroup.controller.dashboard.map.activity.placer;

/**
 * The TimeCalculator class is used to extract time in
 * terms of years, weeks, days, hours, minutes and seconds
 * from milliseconds.
 */

public class TimeCalculator {

    private static final long NUM_MILLISECONDS_IN_A_SECOND = 1000;
    private static final long NUM_SECONDS_IN_A_MINUTE = 60;
    private static final long NUM_SECONDS_IN_AN_HOUR = NUM_SECONDS_IN_A_MINUTE * 60;
    private static final long NUM_SECONDS_IN_A_DAY = NUM_SECONDS_IN_AN_HOUR * 24;
    private static final long NUM_SECONDS_IN_A_WEEK = NUM_SECONDS_IN_A_DAY * 7;
    private static final long NUM_SECONDS_IN_A_MONTH = NUM_SECONDS_IN_A_WEEK * 4;
    private static final long NUM_SECONDS_IN_A_YEAR = NUM_SECONDS_IN_A_MONTH * 12;

    private long totalTime;


    public TimeCalculator(long totalTime) {
        this.totalTime = totalTime;
    }

    public String getTimeDescription(){

        long numSeconds = totalTime / NUM_MILLISECONDS_IN_A_SECOND;

        long numYears = numSeconds / NUM_SECONDS_IN_A_YEAR;
        numSeconds = updateNumSeconds(numSeconds, numYears, NUM_SECONDS_IN_A_YEAR);

        long numMonths = numSeconds / NUM_SECONDS_IN_A_MONTH;
        numSeconds = updateNumSeconds(numSeconds, numMonths, NUM_SECONDS_IN_A_MONTH);

        long numWeeks = numSeconds / NUM_SECONDS_IN_A_WEEK;
        numSeconds = updateNumSeconds(numSeconds, numWeeks, NUM_SECONDS_IN_A_WEEK);

        long numDays = numSeconds / NUM_SECONDS_IN_A_DAY;
        numSeconds = updateNumSeconds(numSeconds, numDays, NUM_SECONDS_IN_A_DAY);

        long numHours = numSeconds / NUM_SECONDS_IN_AN_HOUR;
        numSeconds = updateNumSeconds(numSeconds, numHours, NUM_SECONDS_IN_AN_HOUR);

        long numMinutes = numSeconds / NUM_SECONDS_IN_A_MINUTE;
        numSeconds = updateNumSeconds(numSeconds, numMinutes, NUM_SECONDS_IN_A_MINUTE);

        return buildTimeDescription(numSeconds, numMinutes, numHours, numDays, numWeeks, numMonths, numYears);
    }

    private long updateNumSeconds(long numSeconds, long toRemove, long divisor){
        return numSeconds - (toRemove * divisor);
    }

    private String buildTimeDescription(long numSeconds, long numMinutes, long numHours, long numDays,
                                        long numWeeks, long numMonths, long numYears) {

        String timeTaken = "";

        if(isMoreThanOne(numYears)){
            timeTaken = timeTaken + numYears + "year(s),";
        }

        timeTaken = addToTimeTaken(numMonths, timeTaken, " month(s)");
        timeTaken = addToTimeTaken(numWeeks, timeTaken, " week(s)");
        timeTaken = addToTimeTaken(numDays, timeTaken, " day(s)");
        timeTaken = addToTimeTaken(numHours, timeTaken, " hour(s)");
        timeTaken = addToTimeTaken(numMinutes, timeTaken, " minute(s)");
        timeTaken = addToTimeTaken(numSeconds, timeTaken, " second(s)");

        return timeTaken;

    }

    private boolean isMoreThanOne(long value){
        return value > 0;
    }

    private String addToTimeTaken(long valueToAdd, String timeTaken, String ending) {

        if(isMoreThanOne(valueToAdd)){

            if(lastCharacterIsAClosingParentheses(timeTaken)) {
                timeTaken = timeTaken + ", " + valueToAdd + ending;
            } else {
                timeTaken = timeTaken + valueToAdd + ending;
            }
        }

        return timeTaken;
    }

    private boolean lastCharacterIsAClosingParentheses(String timeTaken) {

        if(timeTaken.isEmpty()){
            return false;
        }

        return timeTaken.substring(timeTaken.length() - 1).equals(")");
    }

}