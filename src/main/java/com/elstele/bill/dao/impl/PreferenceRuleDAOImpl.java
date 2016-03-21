package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.PreferenceRuleDAO;
import com.elstele.bill.domain.PreferenceRule;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PreferenceRuleDAOImpl extends CommonDAOImpl<PreferenceRule> implements PreferenceRuleDAO {
    @Override
    public List<PreferenceRule> getRuleList() {
        Query query = getSessionFactory().getCurrentSession().createQuery("from PreferenceRule pr where pr.status is null or pr.status <> 'DELETED' order by pr.profileId, pr.rulePriority ");
        return (List<PreferenceRule>)query.list();
    }

    @Override
    public PreferenceRule getByProfileAndPriority(int profileId, int rulePriority) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from PreferenceRule pr where pr.profileId = :profileId AND pr.rulePriority= :rulePriority")
                .setParameter("profileId", profileId)
                .setParameter("rulePriority", rulePriority);
        return (PreferenceRule) query.uniqueResult();
    }

    @Override
    public PreferenceRule getByTariffAndValidDate(Float tariff, Date validFrom) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from PreferenceRule pr where pr.tarif = :tariff AND pr.validFrom = :validFrom")
                .setParameter("tariff", tariff)
                .setParameter("validFrom", validFrom);
        return (PreferenceRule) query.uniqueResult();
    }

    @Override
    public List<PreferenceRule> getRuleListByProfileId(int profileId) {
        Query query = getSessionFactory().getCurrentSession().
                createQuery("from PreferenceRule pr where pr.profileId = :profileId")
                .setParameter("profileId", profileId);
        return (List<PreferenceRule>)query.list();
    }

    @Override
    public int getProfileIdMaxValue() {
        Criteria criteria = getSessionFactory().getCurrentSession()
                .createCriteria(PreferenceRule.class)
                .setProjection(Projections.max("profileId"));
        return (int) criteria.uniqueResult();
    }


}
