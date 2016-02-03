package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.utils.Enums.ResponseToAjax;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;

public interface CSVFileDataService {
    public ResponseToAjax handle(MultipartHttpServletRequest fileFromServlet, String selectedFileType);
}
