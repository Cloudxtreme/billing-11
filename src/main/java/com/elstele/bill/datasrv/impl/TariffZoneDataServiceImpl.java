package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.TariffZoneAssembler;
import com.elstele.bill.dao.interfaces.TariffZoneDAO;
import com.elstele.bill.datasrv.interfaces.TariffZoneDataService;
import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.form.TariffZoneForm;
import com.elstele.bill.reportCreators.dateparser.DateReportParser;
import com.elstele.bill.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
        return shiftListContent(tariffZoneList);
    }

    @Override
    @Transactional
    public List<TariffZoneForm> getOnlyActualTariffZoneList() {
        List<TariffZone> tariffZoneList = tariffZoneDAO.getOnlyActualTariffZoneList();
        return shiftListContent(tariffZoneList);
    }

    private List<TariffZoneForm> shiftListContent(List<TariffZone> tariffZoneList){
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
    public int create(TariffZone tariffZone) {
        int id = tariffZoneDAO.create(tariffZone);
        return tariffZoneDAO.getById(id).getZoneId();
    }

    @Override
    @Transactional
    public String deleteZone(int id) {
        try {
            tariffZoneDAO.setStatusDelete(id);
            LOGGER.info("Zone " + id + " deleted successfully");
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
    public TariffZone getUniqueZoneByZoneId(int zoneId){
        return tariffZoneDAO.getUniqueTariffZoneByZoneID(zoneId);
    }

    @Override
    @Transactional
    public TariffZone getZoneByNameAndValidFrom(String zoneName, Date validFrom) {
        return tariffZoneDAO.getZoneByNameAndValidFrom(zoneName, validFrom);
    }

    @Override
    @Transactional
    public Integer setValidToDateForZones(Date newDateFromFile) {
        return tariffZoneDAO.setValidToDateForZones(newDateFromFile, DateReportParser.getPrevDayDate(newDateFromFile));
    }

    @Override
    @Transactional
    public HashMap<String, TariffZone> getZoneMapFromDBByDate(Date validFrom) {
        List<TariffZone> tariffZoneList = tariffZoneDAO.getTariffZoneByValidFromDate(validFrom);
        HashMap<String, TariffZone> resultMap = new HashMap<>();
        for(TariffZone zone : tariffZoneList){
            resultMap.put(zone.getZoneName(), zone);
        }
        return resultMap;
    }
}
