package ru.javawebinar.topjava;

/**
 * GKislin
 * 06.03.2015.
 */
public class LoggedUser {

    private static int id = 1;

    public static int id() {
        return id;
    }

    // not thread-safe
    public static void setId(int currentId) {
        id = currentId;
    }
}
