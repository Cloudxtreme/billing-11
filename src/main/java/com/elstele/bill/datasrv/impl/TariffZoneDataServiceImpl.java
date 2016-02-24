package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.TariffZoneAssembler;
import com.elstele.bill.dao.interfaces.TariffZoneDAO;
import com.elstele.bill.datasrv.interfaces.TariffZoneDataService;
import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.form.TariffZoneForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TariffZoneDataServiceImpl implements TariffZoneDataService {

    @Autowired
    TariffZoneDAO tariffZoneDAO;

    @Override
    @Transactional
    public List<TariffZoneForm> getTariffZonesList() {
        List<TariffZone> tariffZoneList = tariffZoneDAO.getTariffZoneList();
        TariffZoneAssembler assembler = new TariffZoneAssembler();
        List<TariffZoneForm> formList = new ArrayList<>();
        for(TariffZone tariffZone : tariffZoneList){
            formList.add(assembler.fromBeanToForm(tariffZone));
        }
        return formList;
    }
}
