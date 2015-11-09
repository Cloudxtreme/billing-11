package com.elstele.bill.datasrv;

import com.elstele.bill.reportCreators.factory.ReportCreatorFactory;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import com.elstele.bill.reportCreators.reportParent.GeneralReportCreator;
import com.elstele.bill.utils.ResponseToAjax;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.Logger;

@Service
public class ReportDataServiceImpl implements ReportDataService {
    @Autowired
    ServletContext ctx;
    @Autowired
    ReportCreatorFactory factory;

    final static Logger log = LogManager.getLogger(GeneralReportCreator.class);


    @Override
    public ResponseToAjax createReport(String[] reportParametersArray) {
        ReportDetails reportDetails = new ReportDetails();
        reportDetails.setYear(reportParametersArray[0]);
        reportDetails.setMonth(reportParametersArray[1]);
        reportDetails.setPath(ctx.getRealPath("resources\\files\\csvFiles"));

        for (int i = 2; i < reportParametersArray.length; i++) {
            reportDetails.setReportName(reportParametersArray[i]);
            ReportCreator reportCreator = factory.getCreator(reportParametersArray[i]);
            reportCreator.create(reportDetails);
        }
        log.info("All files is generated successful");
        return ResponseToAjax.SUCCESS;

    }

}
