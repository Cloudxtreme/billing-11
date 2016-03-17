package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.PreferenceRule;

import java.util.List;

public interface PreferenceRuleDAO extends CommonDAO<PreferenceRule>{
    public List getRuleList();
    public PreferenceRule getByProfileAndPriority(int profileId, int rulePriority);
    public List<PreferenceRule> getRuleListByProfileId(int profileId);
}
