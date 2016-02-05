package com.elstele.bill.usersDataStorage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Service
public class UserStateStorage {
    private static HashMap<String, UserStateData> userStateMapStorage = new HashMap<>();
    final static Logger LOGGER = LogManager.getLogger(UserStateStorage.class);

    public static void setElementToMap(HttpSession session) {
        userStateMapStorage.put(session.getId(), new UserStateData());
        LOGGER.info("Session id " + session.getId() + " added to storage");
    }

    public static void removeElementFromMap(HttpSession session) {
        userStateMapStorage.remove(session.getId());
        LOGGER.info("Session id " + session.getId() + " removed from storage");
    }

    public static float getProgressBySession(HttpSession session) {
        return getUserStateDataFromMap(session).getProgress();
    }

    public static void setProgressToObjectInMap(HttpSession session, float val) {
        UserStateData data = userStateMapStorage.get(session.getId());
        data = validateObjectState(session, data);
        data.setProgress(val);
    }

    public static void setBusyToObjectInMap(HttpSession session, boolean val) {
        UserStateData data = userStateMapStorage.get(session.getId());
        data = validateObjectState(session, data);
        data.setBusy(val);
    }

    public static boolean checkForBusy() {
        for (UserStateData data : userStateMapStorage.values()) {
            if (data.isBusy()) return true;
        }
        return false;
    }


    private static UserStateData validateObjectState(HttpSession session, UserStateData data) {
        if (data == null) {
            setElementToMap(session);
            return new UserStateData();
        } else {
            return data;
        }
    }

    private static UserStateData getUserStateDataFromMap(HttpSession session) {
        UserStateData data = userStateMapStorage.get(session.getId());
        return validateObjectState(session, data);
    }




}
