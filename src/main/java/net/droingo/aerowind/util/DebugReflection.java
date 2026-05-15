package net.droingo.aerowind.util;

import net.droingo.aerowind.AeroWind;

public final class DebugReflection {
    private DebugReflection() {
    }

    public static void printClassInfo(Class<?> clazz) {
        AeroWind.LOGGER.info("========== CLASS INFO ==========");
        AeroWind.LOGGER.info("Class: {}", clazz.getName());

        for (var method : clazz.getMethods()) {
            AeroWind.LOGGER.info("Method: {}", method);
        }

        AeroWind.LOGGER.info("================================");
    }
}