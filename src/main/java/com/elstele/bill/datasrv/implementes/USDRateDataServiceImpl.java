package com.elstele.bill.datasrv.implementes;

import com.elstele.bill.dao.interfaces.USDRateDAO;
import com.elstele.bill.datasrv.interfaces.USDRateDataService;
import com.elstele.bill.utils.USDRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class USDRateDataServiceImpl implements USDRateDataService {

    @Autowired
    USDRateDAO usdRateDAO;

    @Transactional
    public void setRate() {
        USDRate usdRate = new USDRate(usdRateDAO);
        usdRate.sendLiveRequest();
    }
}
