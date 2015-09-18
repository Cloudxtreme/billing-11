package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Calls;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CallsDAOImpl extends CommonDAOImpl<Calls> implements CallsDAO {

    @Override
    public List<Calls> getCalls() {
        return null;
    }

}
