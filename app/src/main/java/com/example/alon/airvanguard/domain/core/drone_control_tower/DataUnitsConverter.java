package com.example.alon.airvanguard.domain.core.drone_control_tower;

/**
 * Domain service class for converting various
 * data units between different unit systems.
 */

public class DataUnitsConverter {

    //km/h = m/s x 3.6
    // mph = km/h ÷ 1.609344
    // 3.280839895

    /**
     * Converts meters to feet.
     *
     * @param meters the meters value to convert.
     * @return feet value of converted meters.
     */
    public static double convertMetersToFeet(double meters) {
        return meters * 3.280839895;
    }

    /**
     * Converts meters per second value to kilometer
     * per hour value.
     *
     * @param ms meters per second value.
     * @return the converted kmh value.
     */
    public static double convertMsToKmh(double ms) {
        return ms * 3.6;
    }

    /**
     * Converts meters per second value to Mile per hour value.
     *
     * @param ms meters per second value.
     * @return the converted mph value.
     */
    public static double convertMsToMph(double ms) {
        return convertMsToKmh(ms) / 1.609344;
    }

    /**
     * Converts feet to meters.
     *
     * @param feet feet value.
     * @return the converted meters value.
     */
    public static double convertFeetToMeters(double feet) {
        return 0;
    }

    /**
     * Converts meters per second value to foot
     * per second vale
     *
     * @param ms meters per second
     * @return the converted fps value.
     */
    public static double convertMsToFps(double ms) {
        return ms * 3.280839895;
    }
}
