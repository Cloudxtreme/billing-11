package com.elstele.bill.datasrv;

import com.elstele.bill.utils.ResponseToAjax;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface CSVParserDataService {
    public ResponseToAjax parse(MultipartHttpServletRequest fileFromServlet);
}
