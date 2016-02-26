package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.PreferenceRuleDAO;
import com.elstele.bill.domain.PreferenceRule;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferenceRuleDAOImpl extends CommonDAOImpl<PreferenceRule> implements PreferenceRuleDAO {
    @Override
    public List<PreferenceRule> getRuleList() {
        Query query = getSessionFactory().getCurrentSession().createQuery("from PreferenceRule pr where pr.status is null or pr.status <> 'DELETED' order by pr.id DESC ");
        return (List<PreferenceRule>)query.list();
    }

    @Override
    public PreferenceRule getByProfileAndPriority(int profileId, int rulePriority) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from PreferenceRule pr where pr.profileId = :profileId AND pr.rulePriority= :rulePriority")
                .setParameter("profileId", profileId)
                .setParameter("rulePriority", rulePriority);
        return (PreferenceRule) query.uniqueResult();
    }
}
