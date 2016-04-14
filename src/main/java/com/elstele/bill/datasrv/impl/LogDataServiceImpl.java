package com.elstele.bill.datasrv.impl;

import com.elstele.bill.datasrv.interfaces.LogDataService;
import com.elstele.bill.utils.PropertiesHelper;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogDataServiceImpl implements LogDataService {

    final static Logger LOGGER = LogManager.getLogger(LogDataServiceImpl.class);
    private static final CharSequence TEST_USER_AUTH = "[testuser]";

    @Autowired
    private PropertiesHelper propertiesHelper;

    @Override
    public List<String> getAuthLogLastLines(int lineCount) {
        List<String> result = new ArrayList<>();
        String logFilePath = propertiesHelper.getPathToRadiusLog();
        if (logFilePath != null) {
            try {
                ReversedLinesFileReader rlfr = new ReversedLinesFileReader(new File(logFilePath));
                for (int i = 0; i < lineCount; i++) {
                    String line = rlfr.readLine();
                    if (line != null) {
                        if (!isFiltered(line)) {
                            result.add(line);
                        }
                    } else {
                        break;
                    }
                }
                rlfr.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }

        } else {
            LOGGER.error("No Radius log file found!");
        }
        return result;
    }

    private boolean isFiltered(String line) {
        if (line.contains(TEST_USER_AUTH)) {
            return true;
        }
        return false;
    }
}
