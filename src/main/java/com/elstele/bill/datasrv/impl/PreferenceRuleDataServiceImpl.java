package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.PreferenceRuleAssembler;
import com.elstele.bill.dao.interfaces.PreferenceRuleDAO;
import com.elstele.bill.datasrv.interfaces.PreferenceRuleDataService;
import com.elstele.bill.domain.PreferenceRule;
import com.elstele.bill.form.PreferenceRuleForm;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public PreferenceRuleForm getRuleById(int id) {
        PreferenceRule rule = ruleDAO.getById(id);
        return assembler.fromBeanToForm(rule);
    }

    @Override
    @Transactional
    public ResponseToAjax checkForFree(int id, int profileId, int rulePriority) {
        PreferenceRule preferenceRule = ruleDAO.getByProfileAndPriority(profileId, rulePriority);
        if(preferenceRule != null){
            if(id > 0){
                if(preferenceRule.getId() == id){
                    return ResponseToAjax.FREE;
                }
                return ResponseToAjax.BUSY;
            }
            return ResponseToAjax.BUSY;
        }
        return ResponseToAjax.FREE;
    }
}
