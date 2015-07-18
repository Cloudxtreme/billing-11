package com.elstele.bill.dao.common;

import com.elstele.bill.dao.InventoryListDAO;
import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.InventoryList;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

/**
 * Created by оо on 17.07.2015.
 */
@Service
public class InventoryListDAOImpl extends CommonDAOImpl<InventoryList> implements InventoryListDAO {

    @Override
    public InventoryList getInventoryListFromDB() {
        String rd = "SELECT * FROM InventoryList";
        Query query = getSessionFactory().getCurrentSession().createQuery(rd);

        return null;
    }
}
