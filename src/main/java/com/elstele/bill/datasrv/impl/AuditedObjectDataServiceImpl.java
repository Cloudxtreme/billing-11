package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.AuditedObjectAssembler;
import com.elstele.bill.dao.interfaces.AuditedObjectDAO;
import com.elstele.bill.datasrv.interfaces.AuditedObjectDataService;
import com.elstele.bill.domain.*;
import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.form.AuditedObjectForm;
import com.elstele.bill.form.DifferenceForm;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.ObjectOperationType;
import com.elstele.bill.utils.Messagei18nHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.javers.core.diff.changetype.ValueChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuditedObjectDataServiceImpl implements AuditedObjectDataService {

    @Autowired
    AuditedObjectDAO auditedObjectDAO;
    @Autowired
    private Messagei18nHelper messageHelper;

    final static Logger LOGGER = LogManager.getLogger(AuditedObjectDataServiceImpl.class);

    @Override
    @Transactional
    public List<AuditedObjectForm> getAuditedObject(int id, String objClassName) {
        List<AuditedObjectForm> resultList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        List<AuditedObject> auditedObjectList = auditedObjectDAO.getAuditedObjectList(id, objClassName);

        List<CommonDomainBean> listOfDeserializedBeans = new ArrayList<>();

        try {
            for (AuditedObject curBean : auditedObjectList) {
                String objAsString = curBean.getChangedObject();
                Class<?> clazz = Class.forName(curBean.getClassRerence());
                CommonDomainBean deserializedBean = (CommonDomainBean) mapper.readValue(objAsString, clazz);
                listOfDeserializedBeans.add(deserializedBean);
            }

            AuditedObjectAssembler assembler = new AuditedObjectAssembler();
            int i = 0;
            for (AuditedObject auditedObject : auditedObjectList) {
                AuditedObjectForm auditedObjectForm = assembler.fromBeanToForm(auditedObject);
                List<DifferenceForm> differenceForms = new ArrayList<>();
                if (listOfDeserializedBeans.size() > 1 && i < listOfDeserializedBeans.size() && i != 0) { //no way to find difference for only one snapshot
                    Javers javers = JaversBuilder.javers().registerValueObjects(
                            Street.class,
                            Ip.class,
                            IpSubnet.class,
                            DeviceTypes.class,
                            ServiceInternet.class).build();

                    CommonDomainBean curBean = listOfDeserializedBeans.get(i);
                    CommonDomainBean prevBean = listOfDeserializedBeans.get(i - 1);
                    Diff snapshotDiff = javers.compare(prevBean, curBean);

                    LOGGER.info(snapshotDiff);

                    if (snapshotDiff.getChanges().size() > 0) {
                        setChangedValueToList(snapshotDiff, differenceForms, curBean);
                    }
                    auditedObjectForm.setChangesList(differenceForms);
                }
                i = i + 1;
                resultList.add(auditedObjectForm);
            }
        } catch (ClassNotFoundException | IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return resultList;
    }

    private void setChangedValueToList(Diff snapshotDiff, List<DifferenceForm> differenceForms, CommonDomainBean curBean) {
        for (ValueChange curChange : snapshotDiff.getChangesByType(ValueChange.class)) {
            Object object = curChange.getAffectedObject().get();
            String propertyName = curChange.getPropertyName() + "." +  object.getClass().getSimpleName();
            propertyName = correctingForAccountAddress(object, curBean , propertyName);
            DifferenceForm diffForm = new DifferenceForm();
            diffForm.setFieldName(messageHelper.getMessage(propertyName));
            diffForm.setOldValue(curChange.getLeft().toString());
            diffForm.setNewValue(curChange.getRight().toString());
            differenceForms.add(diffForm);
        }
    }

    private String correctingForAccountAddress(Object object, CommonDomainBean curBean, String propertyName){
        if(curBean instanceof Account){
            propertyName = streetTypeDetermine(object, curBean , propertyName);
            propertyName = addressTypeDetermine(object, curBean, propertyName);
        }
        return propertyName;
    }

    private String streetTypeDetermine(Object object, CommonDomainBean curBean, String propertyName){
        if(object instanceof Street) {
            int id = ((Street) object).getId();
            int idLegal = ((Account) curBean).getLegalAddress().getStreet().getId();
            int idPhy = ((Account) curBean).getPhyAddress().getStreet().getId();
            if (id == idLegal) {
                propertyName += "." + "LegalAddress";
            }
            if (id == idPhy) {
                propertyName += "." + "PhyAddress";
            }
        }
        return propertyName;
    }

    private String addressTypeDetermine(Object object, CommonDomainBean curBean, String propertyName){
        if(object instanceof Address){
            int id = ((Address) object).getId();
            int idLegal = ((Account) curBean).getLegalAddress().getId();
            int idPhy = ((Account) curBean).getPhyAddress().getId();
            if(id == idLegal){
                propertyName += "." + "LegalAddress";
            }
            if(id == idPhy){
                propertyName += "." + "PhyAddress";
            }
        }
        return propertyName;
    }

    @Override
    public String getCreatedBy(List<AuditedObjectForm> auditedObjectFormList) {
        for (AuditedObjectForm form : auditedObjectFormList) {
            if (form.getChangesType().equals(ObjectOperationType.CREATE)) {
                return form.getChangedBy();
            }
        }
        return "";
    }

    @Override
    public Date getCreatedDate(List<AuditedObjectForm> auditedObjectFormList) {
        for (AuditedObjectForm form : auditedObjectFormList) {
            if (form.getChangesType().equals(ObjectOperationType.CREATE)) {
                return form.getChangesDate();
            }
        }
        return null;
    }
}
