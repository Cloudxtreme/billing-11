package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.utils.ResponseToAjax;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface CSVParserDataService {
    public ResponseToAjax parse(MultipartHttpServletRequest fileFromServlet);
}
