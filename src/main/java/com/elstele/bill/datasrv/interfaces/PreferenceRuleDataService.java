package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.domain.PreferenceRule;
import com.elstele.bill.form.PreferenceRuleForm;
import com.elstele.bill.utils.Enums.ResponseToAjax;

import java.util.Date;
import java.util.List;

public interface PreferenceRuleDataService {
    public List<PreferenceRuleForm> getRuleList();
    public String deleteRule(int id);
    public void updateRule(PreferenceRuleForm form);
    public int createRule(PreferenceRuleForm form);
    public int createRule(PreferenceRule rule);
    public PreferenceRuleForm getRuleById(int id);
    public ResponseToAjax checkForFree(int id, int profileId, int rulePriority);
    public List<PreferenceRule> getRuleListByProfileId(int profileId);
    public int getProfileIdMaxValue();
    public PreferenceRule getByProfileIdAndPriority(int profileId, int rulePriority);
    public PreferenceRule getByTariffAndValidDate(Float tariff, Date validFrom);
}
