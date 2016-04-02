package com.elstele.bill.datasrv.interfaces;


import java.util.List;

public interface LogDataService {
    public List<String> getAuthLogLastLines(int lineCount);
}
