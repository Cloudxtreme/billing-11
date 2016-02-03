package com.elstele.bill.datasrv.impl;

import com.elstele.bill.datasrv.interfaces.CSVFileDataService;
import com.elstele.bill.datasrv.interfaces.ReportDataService;
import com.elstele.bill.reportCreators.factory.ReportCreatorFactory;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.exceptions.IncorrectReportNameException;
import com.elstele.bill.utils.LocalDirPathProvider;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.Logger;

@Service
public class ReportDataServiceImpl implements ReportDataService {
    @Autowired
    ReportCreatorFactory factory;
    @Autowired
    LocalDirPathProvider pathProvider;
    @Autowired
    CSVFileDataService csvFileDataService;

    final static Logger LOGGER = LogManager.getLogger(ReportDataServiceImpl.class);
    private double progress;


    @Override
    @Async
    public synchronized ResponseToAjax createReport(String[] reportParametersArray) throws IncorrectReportNameException {
        try {
            csvFileDataService.setCsvFileHandlingFree(false);
            ReportDetails reportDetails = new ReportDetails();
            reportDetails.setYear(reportParametersArray[0]);
            reportDetails.setMonth(reportParametersArray[1]);
            reportDetails.setPath(pathProvider.getCSVDirectoryPath());

            double reportsCount = reportParametersArray.length - 2;
            double timeForOne = 100/reportsCount;
            progress = 0;

            for (int i = 2; i < reportParametersArray.length; i++) {
                reportDetails.setReportName(reportParametersArray[i]);
                ReportCreator reportCreator = factory.getCreator(reportParametersArray[i]);
                reportCreator.create(reportDetails);
                //TODO think how to get this value from this method
                progress +=  timeForOne;
            }

            csvFileDataService.setCsvFileHandlingFree(true);
            LOGGER.info("All files is generated successful");
            return ResponseToAjax.SUCCESS;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseToAjax.ERROR;
        }
    }

    @Override
    public double gettingProgressValue(){
        return Math.round(progress);
    }

    @Override
    public void setProgress(double progress) {
        this.progress = progress;
    }
}
