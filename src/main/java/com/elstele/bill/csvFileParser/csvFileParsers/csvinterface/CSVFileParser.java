package com.elstele.bill.csvFileParser.csvFileParsers.csvinterface;

import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.LocalDirPathProvider;
import org.springframework.web.multipart.MultipartFile;

public interface CSVFileParser {
    public ResponseToAjax parse(MultipartFile multipartFile, String path);
}
