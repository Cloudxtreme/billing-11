package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.form.PreferenceRuleForm;
import com.elstele.bill.utils.Enums.ResponseToAjax;

import java.util.List;

public interface PreferenceRuleDataService {
    List<PreferenceRuleForm> getRuleList();
    String deleteRule(int id);
    void updateRule(PreferenceRuleForm form);
    int createRule(PreferenceRuleForm form);
    PreferenceRuleForm getRuleById(int id);
    ResponseToAjax checkForFree(int id, int profileId, int rulePriority);
}
