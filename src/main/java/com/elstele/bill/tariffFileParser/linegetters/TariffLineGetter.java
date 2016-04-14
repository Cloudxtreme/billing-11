package com.elstele.bill.tariffFileParser.linegetters;

import java.io.File;
import java.util.List;

public interface TariffLineGetter {
    public List<String> getDataLinesFromDOCXFile(File file);
}
