package com.elstele.bill.datasrv.impl;

import com.elstele.bill.datasrv.interfaces.CSVFileDataService;
import com.elstele.bill.datasrv.interfaces.ReportDataService;
import com.elstele.bill.reportCreators.factory.ReportCreatorFactory;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.exceptions.IncorrectReportNameException;
import com.elstele.bill.utils.LocalDirPathProvider;
import com.elstele.bill.usersDataStorage.UserStateStorage;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

@Service
public class ReportDataServiceImpl implements ReportDataService {
    @Autowired
    ReportCreatorFactory factory;
    @Autowired
    LocalDirPathProvider pathProvider;
    @Autowired
    CSVFileDataService csvFileDataService;

    final static Logger LOGGER = LogManager.getLogger(ReportDataServiceImpl.class);

    @Override
    @Async
    public ResponseToAjax createReport(String[] reportParametersArray, HttpSession session) throws IncorrectReportNameException {
        try {
            UserStateStorage.setBusyToObjectInMap(session, true);
            ReportDetails reportDetails = new ReportDetails();
            reportDetails.setYear(reportParametersArray[0]);
            reportDetails.setMonth(reportParametersArray[1]);
            reportDetails.setPath(pathProvider.getCSVDirectoryPath());

            double reportsCount = reportParametersArray.length - 2;
            double timeForOne = 100 / reportsCount;
            float progress = 0;

            for (int i = 2; i < reportParametersArray.length; i++) {
                reportDetails.setReportName(reportParametersArray[i]);
                ReportCreator reportCreator = factory.getCreator(reportParametersArray[i]);
                reportCreator.create(reportDetails);

                progress += timeForOne;
                UserStateStorage.setProgressToObjectInMap(session, progress);
            }
            UserStateStorage.setProgressToObjectInMap(session, Constants.PROGRESS_DONE);

            LOGGER.info("All files is generated successful");
            return ResponseToAjax.SUCCESS;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseToAjax.ERROR;
        } finally {
            UserStateStorage.setBusyToObjectInMap(session, false);
        }
    }

}
