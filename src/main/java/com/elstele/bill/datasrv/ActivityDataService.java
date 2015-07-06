package com.elstele.bill.datasrv;

import com.elstele.bill.domain.Activity;

public interface ActivityDataService {

    public boolean isCredentialValid(String name, String description);
    public Integer saveActivity(Activity activity);

}
