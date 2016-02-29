package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.TariffZoneAssembler;
import com.elstele.bill.dao.interfaces.TariffZoneDAO;
import com.elstele.bill.datasrv.interfaces.TariffZoneDataService;
import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.form.TariffZoneForm;
import com.elstele.bill.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TariffZoneDataServiceImpl implements TariffZoneDataService {

    @Autowired
    TariffZoneDAO tariffZoneDAO;
    private TariffZoneAssembler assembler = new TariffZoneAssembler();
    final static Logger LOGGER = LogManager.getLogger(TariffZoneDataServiceImpl.class);

    @Override
    @Transactional
    public List<TariffZoneForm> getTariffZonesList() {
        List<TariffZone> tariffZoneList = tariffZoneDAO.getTariffZoneList();
        List<TariffZoneForm> formList = new ArrayList<>();
        for(TariffZone tariffZone : tariffZoneList){
            formList.add(assembler.fromBeanToForm(tariffZone));
        }
        return formList;
    }

    @Override
    @Transactional
    public void create(TariffZoneForm tariffZoneForm) {
        TariffZone tariffZone = assembler.fromFormToBean(tariffZoneForm);
        tariffZoneDAO.create(tariffZone);
    }

    @Override
    @Transactional
    public String deleteZone(int id) {
        try {
            tariffZoneDAO.setStatusDelete(id);
            LOGGER.info("Zone deleted successfully");
            return Constants.ZONE_DELETED_SUCCESS;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Constants.ZONE_DELETED_ERROR;
        }
    }

    @Override
    @Transactional
    public TariffZoneForm getZoneById(int id) {
        TariffZone tariffZone = tariffZoneDAO.getById(id);
        return assembler.fromBeanToForm(tariffZone);
    }

    @Override
    @Transactional
    public void updateZone(TariffZoneForm tariffZoneForm) {
        TariffZone tariffZone = assembler.fromFormToBean(tariffZoneForm);
        tariffZoneDAO.update(tariffZone);
    }

    @Override
    @Transactional
    public void changeSoftBlockStatus(int zoneId) {
        TariffZone zone = tariffZoneDAO.getById(zoneId);
        zone.setDollar(!zone.isDollar());
        tariffZoneDAO.updateAndMerge(zone);
    }

    @Override
    @Transactional
    public List<Integer> getPrefProfileIdList() {
        return tariffZoneDAO.getPrefProfileIdList();
    }
}
