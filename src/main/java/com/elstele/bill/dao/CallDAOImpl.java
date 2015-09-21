package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Call;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallDAOImpl extends CommonDAOImpl<Call> implements CallDAO {

    @Override
    public List<Call> getCalls() {
        return null;
    }

}
