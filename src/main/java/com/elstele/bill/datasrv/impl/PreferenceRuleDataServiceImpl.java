package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.PreferenceRuleAssembler;
import com.elstele.bill.dao.interfaces.PreferenceRuleDAO;
import com.elstele.bill.datasrv.interfaces.PreferenceRuleDataService;
import com.elstele.bill.domain.PreferenceRule;
import com.elstele.bill.form.PreferenceRuleForm;
import com.elstele.bill.reportCreators.dateparser.DateReportParser;
import com.elstele.bill.tariffFileParser.fileTemplates.TariffFileTemplateData;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class PreferenceRuleDataServiceImpl implements PreferenceRuleDataService {
    @Autowired
    PreferenceRuleDAO ruleDAO;

    private PreferenceRuleAssembler assembler = new PreferenceRuleAssembler();
    final static Logger LOGGER = LogManager.getLogger(PreferenceRuleDataServiceImpl.class);

    @Override
    @Transactional
    public List<PreferenceRuleForm> getRuleList() {
        List<PreferenceRule> ruleList = ruleDAO.getRuleList();
        List<PreferenceRuleForm> result = new ArrayList<>();
        for (PreferenceRule rule : ruleList) {
            result.add(assembler.fromBeanToForm(rule));
        }
        return result;
    }

    @Override
    @Transactional
    public String deleteRule(int id) {
        try {
            ruleDAO.setStatusDelete(id);
            LOGGER.info("Rule " + id + " deleted");
            return Constants.RULE_DELETED_SUCCESS;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Constants.RULE_DELETED_ERROR;
        }
    }

    @Override
    @Transactional
    public void updateRule(PreferenceRuleForm form) {
        PreferenceRule rule = assembler.fromFormToBean(form);
        ruleDAO.update(rule);
    }

    @Override
    @Transactional
    public int createRule(PreferenceRuleForm form) {
        return ruleDAO.create(assembler.fromFormToBean(form));
    }

    @Override
    @Transactional
    public int createRule(PreferenceRule rule) throws PSQLException{
        return ruleDAO.create(rule);
    }

    @Override
    @Transactional
    public int getProfileIdMaxValue() {
        return ruleDAO.getProfileIdMaxValue();
    }

    @Override
    @Transactional
    public PreferenceRuleForm getRuleById(int id) {
        PreferenceRule rule = ruleDAO.getById(id);
        return assembler.fromBeanToForm(rule);
    }

    @Override
    @Transactional
    public List<PreferenceRule> getRuleListByProfileId(int profileId) {
        return ruleDAO.getRuleListByProfileId(profileId);
    }

    @Override
    @Transactional
    public ResponseToAjax checkForFree(int id, int profileId, int rulePriority) {
        PreferenceRule preferenceRule = getByProfileIdAndPriority(profileId, rulePriority);
        if (preferenceRule != null) {
            if (id > 0) {
                if (preferenceRule.getId() == id) {
                    return ResponseToAjax.FREE;
                }
                return ResponseToAjax.BUSY;
            }
            return ResponseToAjax.BUSY;
        }
        return ResponseToAjax.FREE;
    }

    @Override
    @Transactional
    public PreferenceRule getByProfileIdAndPriority(int profileId, int rulePriority) {
        return ruleDAO.getByProfileAndPriority(profileId, rulePriority);
    }

    @Override
    @Transactional
    public PreferenceRule getByTariffAndValidDate(Float tariff, Date validFrom) {
        return ruleDAO.getByTariffAndValidDate(tariff, validFrom);
    }

    @Override
    @Transactional
    public Integer setValidToDateForRules(Date newDateFromFile) {
        return ruleDAO.setValidToDateForRules(newDateFromFile, DateReportParser.getPrevDayDate(newDateFromFile));
    }

    @Override
    @Transactional
    public HashMap<Float ,PreferenceRule> getTariffMapFRomDBByDate(Date validFrom) {
        List<PreferenceRule> preferenceRuleList = ruleDAO.getRuleListByDate(validFrom);
        HashMap<Float, PreferenceRule> resultMap = new HashMap<>();
        for(PreferenceRule rule : preferenceRuleList){
            resultMap.put(rule.getTarif(), rule);
        }
        return resultMap;
    }

    @Override
    @Transactional
    public int handleRuleFromTariffFile(TariffFileTemplateData transTemplate, HashMap<Float, PreferenceRule> preferenceRuleHashMap) throws PSQLException {
        PreferenceRule rule = new PreferenceRule(transTemplate);
        return getExistedProfileIdOrCreateNew(rule, preferenceRuleHashMap);
    }

    @Transactional
    private int getExistedProfileIdOrCreateNew(PreferenceRule rule, HashMap<Float, PreferenceRule> existedRulesHashMap) throws PSQLException {
        PreferenceRule ruleFromDB = existedRulesHashMap.get(rule.getTarif());
        if (ruleFromDB == null) {
            int profileId = getProfileIdMaxValue();
            rule.setProfileId(profileId + 1);
            createRule(rule);
            existedRulesHashMap.put(rule.getTarif(), rule);
            return rule.getProfileId();
        } else {
            LOGGER.info("This rule with Tariff = " + String.format("%f", rule.getTarif()) + " exists in DB");
            return ruleFromDB.getProfileId();
        }
    }
}
