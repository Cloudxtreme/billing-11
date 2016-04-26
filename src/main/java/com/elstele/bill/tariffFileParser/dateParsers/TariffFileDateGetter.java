package com.elstele.bill.tariffFileParser.dateParsers;

import com.elstele.bill.datasrv.interfaces.DirectionDataService;
import com.elstele.bill.domain.Direction;
import com.elstele.bill.reportCreators.dateparser.DateReportParser;

import java.util.Date;

public class TariffFileDateGetter {
    DirectionDataService directionDataService;

    protected static final String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])[/|.](0?[1-9]|1[012])[/|.]((19|20)\\d\\d)";

    public TariffFileDateGetter(DirectionDataService directionDataService) {
        this.directionDataService = directionDataService;
    }

    public TariffFileDateGetter() {
    }

    public Date determineValidToDate(Date validFrom) {
        Direction withLatestDate = directionDataService.getDirectionWithLatestDate(validFrom);
        if (withLatestDate != null) {
            return DateReportParser.getPrevDayDate(withLatestDate.getValidFrom());
        } else {
            return null;
        }
    }
}
