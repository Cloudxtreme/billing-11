package com.elstele.bill.filesWorkers;

import com.elstele.bill.utils.Enums.ResponseToAjax;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;

@Service
public class DOCXFileParser {

    public ResponseToAjax parse(MultipartHttpServletRequest multiPartHTTPServletRequestFiles, HttpSession session){
        return ResponseToAjax.SUCCESS;
    }
}
