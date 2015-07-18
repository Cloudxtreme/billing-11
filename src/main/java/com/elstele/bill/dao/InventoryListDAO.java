package com.elstele.bill.dao;

        import com.elstele.bill.dao.common.CommonDAO;
        import com.elstele.bill.domain.InventoryList;

        import java.util.List;


public interface InventoryListDAO extends CommonDAO<InventoryList>{

    public InventoryList getInventoryListFromDB();

}
