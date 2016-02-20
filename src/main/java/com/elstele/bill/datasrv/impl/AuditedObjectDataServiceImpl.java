package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.AuditedObjectAssembler;
import com.elstele.bill.dao.interfaces.AuditedObjectDAO;
import com.elstele.bill.datasrv.interfaces.AuditedObjectDataService;
import com.elstele.bill.domain.*;
import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.form.AuditedObjectForm;
import com.elstele.bill.form.DifferenceForm;
import com.elstele.bill.utils.Enums.ObjectOperationType;
import com.elstele.bill.utils.Messagei18nHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.javers.core.diff.changetype.ReferenceChange;
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

    private Javers javers;

    final static Logger LOGGER = LogManager.getLogger(AuditedObjectDataServiceImpl.class);

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

            javers = JaversBuilder.javers().build();
            AuditedObjectAssembler assembler = new AuditedObjectAssembler();
            int i = 0;
            for (AuditedObject auditedObject : auditedObjectList) {
                AuditedObjectForm auditedObjectForm = assembler.fromBeanToForm(auditedObject);
                List<DifferenceForm> differenceForms = new ArrayList<>();
                if (listOfDeserializedBeans.size() > 1 && i < listOfDeserializedBeans.size() && i != 0) { //no way to find difference for only one snapshot
                    CommonDomainBean curBean = listOfDeserializedBeans.get(i);
                    CommonDomainBean prevBean = listOfDeserializedBeans.get(i - 1);

                    Diff snapshotDiff = javers.compare(prevBean, curBean);
                    LOGGER.info(snapshotDiff);

                    if (snapshotDiff.getChanges().size() > 0) {
                        setChangedValueToList(snapshotDiff, differenceForms, curBean);
                        setReferenceChangedObject(snapshotDiff, differenceForms, curBean);
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
            CommonDomainBean affectedObject = (CommonDomainBean) curChange.getAffectedObject().get();
            String propertyName = curChange.getPropertyName() + "." + affectedObject.getClass().getSimpleName();
            propertyName = correctingForAccountAddress(affectedObject, curBean, propertyName);
            DifferenceForm diffForm = new DifferenceForm();
            diffForm.setFieldName(messageHelper.getMessage(propertyName));
            diffForm.setOldValue((curChange.getLeft() == null ? "" : curChange.getLeft().toString()));
            diffForm.setNewValue((curChange.getRight() == null ? "" : curChange.getRight().toString()));
            differenceForms.add(diffForm);
        }
    }

    private void setReferenceChangedObject(Diff snapshotDiff, List<DifferenceForm> differenceForms, CommonDomainBean curBean) {
        for (ReferenceChange refChange : snapshotDiff.getChangesByType(ReferenceChange.class)) {
            CommonDomainBean leftObject = (refChange.getLeftObject().isEmpty() ? null : (CommonDomainBean) refChange.getLeftObject().get());
            CommonDomainBean rightObject = (CommonDomainBean) refChange.getRightObject().get();
            if (leftObject != null && rightObject != null) {
                Diff refDiffSnap = null;
                if (leftObject instanceof Street && rightObject instanceof Street) {
                    refDiffSnap = streetRefDiff((Street) leftObject, (Street) rightObject);
                } else if (leftObject instanceof Device && rightObject instanceof Device) {
                    refDiffSnap = deviceRefDiff((Device) leftObject, (Device) rightObject);
                } else if (leftObject instanceof Ip && rightObject instanceof Ip) {
                    refDiffSnap = ipRefDiff((Ip) leftObject, (Ip) rightObject);
                } else if (leftObject instanceof DeviceTypes && rightObject instanceof DeviceTypes) {
                    refDiffSnap = deviceTypeRefDiff((DeviceTypes) leftObject, (DeviceTypes) rightObject);
                }
                setChangedValueToList(refDiffSnap, differenceForms, curBean);
            }
        }
    }

    private Diff streetRefDiff(Street leftObject, Street rightObject) {
        Street toCompareLeft = new Street();
        toCompareLeft.setName(leftObject.getName());
        toCompareLeft.setId(rightObject.getId());

        Street toCompareRight = new Street();
        toCompareRight.setName(rightObject.getName());
        toCompareRight.setId(rightObject.getId());

        return javers.compare(toCompareLeft, toCompareRight);
    }

    private Diff deviceRefDiff(Device leftObject, Device rightObject) {
        Device toCompareLeft = new Device();
        toCompareLeft.setName(leftObject.getName());
        toCompareLeft.setId(rightObject.getId());

        Device toCompareRight = new Device();
        toCompareRight.setName(rightObject.getName());
        toCompareRight.setId(rightObject.getId());

        return javers.compare(toCompareLeft, toCompareRight);
    }

    private Diff ipRefDiff(Ip leftObject, Ip rightObject) {
        Ip toCompareLeft = new Ip();
        toCompareLeft.setIpName(leftObject.getIpName());
        toCompareLeft.setId(rightObject.getId());

        Ip toCompareRight = new Ip();
        toCompareRight.setIpName(rightObject.getIpName());
        toCompareRight.setId(rightObject.getId());

        return javers.compare(toCompareLeft, toCompareRight);
    }

    private Diff deviceTypeRefDiff(DeviceTypes leftObject, DeviceTypes rightObject) {
        DeviceTypes toCompareLeft = new DeviceTypes();
        toCompareLeft.setDeviceType(leftObject.getDeviceType());
        toCompareLeft.setDescription(leftObject.getDescription());
        toCompareLeft.setPortsNumber(leftObject.getPortsNumber());
        toCompareLeft.setId(rightObject.getId());

        DeviceTypes toCompareRight = new DeviceTypes();
        toCompareRight.setDeviceType(rightObject.getDeviceType());
        toCompareLeft.setDescription(rightObject.getDescription());
        toCompareLeft.setPortsNumber(rightObject.getPortsNumber());
        toCompareRight.setId(rightObject.getId());

        return javers.compare(toCompareLeft, toCompareRight);
    }


    private String correctingForAccountAddress(CommonDomainBean affectedObject, CommonDomainBean curBean, String propertyName) {
        if (curBean instanceof Account) {
            propertyName = streetTypeDetermine(affectedObject, curBean, propertyName);
            propertyName = addressTypeDetermine(affectedObject, curBean, propertyName);
        }
        return propertyName;
    }

    private String streetTypeDetermine(CommonDomainBean affectedObject, CommonDomainBean curBean, String propertyName) {
        if (affectedObject instanceof Street) {
            int id = affectedObject.getId();
            int idLegal = (((Account) curBean).getLegalAddress().getStreet() == null ? 0 : ((Account) curBean).getLegalAddress().getStreet().getId());
            int idPhy = (((Account) curBean).getPhyAddress().getStreet() == null ? 0 : ((Account) curBean).getPhyAddress().getStreet().getId());
            if (id == idLegal) {
                propertyName += "." + "LegalAddress";
            }
            if (id == idPhy) {
                propertyName += "." + "PhyAddress";
            }
        }
        return propertyName;
    }

    private String addressTypeDetermine(CommonDomainBean affectedObject, CommonDomainBean curBean, String propertyName) {
        if (affectedObject instanceof Address) {
            int id = affectedObject.getId();
            int idLegal = ((Account) curBean).getLegalAddress().getId();
            int idPhy = ((Account) curBean).getPhyAddress().getId();
            if (id == idLegal) {
                propertyName += "." + "LegalAddress";
            }
            if (id == idPhy) {
                propertyName += "." + "PhyAddress";
            }
        }
        return propertyName;
    }
}
