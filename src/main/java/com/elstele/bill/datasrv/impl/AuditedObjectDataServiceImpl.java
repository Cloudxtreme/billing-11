package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.AuditedObjectAssembler;
import com.elstele.bill.builder.bean.AuditedObjectBuilder;
import com.elstele.bill.dao.interfaces.AuditedObjectDAO;
import com.elstele.bill.datasrv.interfaces.AuditedObjectDataService;
import com.elstele.bill.domain.AuditedObject;
import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.form.AuditedObjectForm;
import com.elstele.bill.form.DifferenceForm;
import com.elstele.bill.utils.Enums.ObjectOperationType;
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
    private AuditedObjectBuilder builder = new AuditedObjectBuilder();

    final static Logger LOGGER = LogManager.getLogger(AuditedObjectDataServiceImpl.class);

    @Override
    @Transactional
    public void changeObserver(Object object, ObjectOperationType type, String changerName) {
        AuditedObject auditedObject = builder.build()
                .withChangedObject(object)
                .withOperationType(type)
                .withChangerName(changerName)
                .getRes();
        LOGGER.info("Object: "+ auditedObject + " was changed");
        auditedObjectDAO.create(auditedObject);
    }

    @Override
    @Transactional
    public List<AuditedObjectForm> getAuditedObject(int id, String objClassName) {
        List<AuditedObjectForm> resultList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        List<AuditedObject> auditedObjectList = auditedObjectDAO.getAuditedObjectList(id , objClassName);

        List<CommonDomainBean> listOfDeserializedBeans = new ArrayList<>();

        try {
            for (AuditedObject curBean : auditedObjectList){
                String objAsString = curBean.getChangedObject();
                Class<?> clazz = Class.forName(curBean.getClassRerence());
                CommonDomainBean deserializedBean = (CommonDomainBean) mapper.readValue(objAsString, clazz);
                listOfDeserializedBeans.add(deserializedBean);
            }


            AuditedObjectAssembler assembler = new AuditedObjectAssembler();
            int i = 0;
            for(AuditedObject auditedObject : auditedObjectList) {
                AuditedObjectForm auditedObjectForm = assembler.fromBeanToForm(auditedObject);

                if (listOfDeserializedBeans.size() > 1 && i < listOfDeserializedBeans.size() && i != 0 ){ //no way to find difference for only one snapshot
                    Javers javers = JaversBuilder.javers().build();

                    CommonDomainBean curBean = listOfDeserializedBeans.get(i);
                    CommonDomainBean prevBean = listOfDeserializedBeans.get(i-1);
                    Diff snapshotDiff = javers.compare(prevBean, curBean);

                    LOGGER.info(snapshotDiff);
                    List<DifferenceForm> differenceForms = new ArrayList<>();
                    if (snapshotDiff.getChanges().size() > 0) {
                        //TODO now changes get only for Values changes
                        for (ValueChange curChange : snapshotDiff.getChangesByType(ValueChange.class)) {
                            DifferenceForm diffForm = new DifferenceForm();
                            diffForm.setFieldName(curChange.getPropertyName());
                            diffForm.setOldValue(curChange.getLeft().toString());
                            diffForm.setNewValue(curChange.getRight().toString());
                            differenceForms.add(diffForm);
                        }
                    }
                    auditedObjectForm.setChangedObject(differenceForms.toString());
                } else {
                    auditedObjectForm.setChangedObject("Initial state, first snapshot in database");
                }
                i = i + 1;

                resultList.add(auditedObjectForm);
            }

        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        LOGGER.info("AuditedObject size is " + resultList.size());
        return resultList;
    }

    @Override
    public String getCreatedBy(List<AuditedObjectForm> auditedObjectFormList){
        for(AuditedObjectForm form : auditedObjectFormList){
            if(form.getChangesType().equals(ObjectOperationType.CREATE)){
                return form.getChangedBy();
            }
        }
        return "";
    }

    @Override
    public Date getCreatedDate(List<AuditedObjectForm> auditedObjectFormList){
        for(AuditedObjectForm form : auditedObjectFormList){
            if(form.getChangesType().equals(ObjectOperationType.CREATE)){
                return form.getChangesDate();
            }
        }
        return null;
    }
}
