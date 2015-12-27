package com.elstele.bill.assembler;

import com.elstele.bill.domain.Activity;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.ActivityForm;
import com.elstele.bill.form.UserRoleForm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.springframework.beans.BeanUtils.copyProperties;

public class UserRoleAssembler {
    String[] propsToSkip = {"activities"};

    public UserRoleForm fromBeanToForm(UserRole bean){
        UserRoleForm form = new UserRoleForm();
        copyProperties(bean, form, propsToSkip);
        form = fillActivityFields(bean.getActivities(),form);
        return form;
    }

    public UserRole fromFormToBean(UserRoleForm form){
        UserRole bean = new UserRole();
        copyProperties(form, bean, propsToSkip);
        if(form.getActivityId()!=null) {
            for (int roleId : form.getActivityId()) {
                Activity activity = new Activity();
                activity.setId(roleId);
                bean.addActivity(activity);
            }
        }
        return bean;
    }

    private UserRoleForm fillActivityFields(List<Activity> beans, UserRoleForm form) {
        if(beans!=null) {
            List<Integer> activityIdList = new ArrayList<>();
            List<String> activityNameList = new ArrayList<>();
            Iterator iterator = beans.iterator();
            while (iterator.hasNext()) {
                Activity bean = (Activity) iterator.next();
                activityIdList.add(bean.getId());
                activityNameList.add(bean.getName());
            }
            form.setActivityId(activityIdList);
            form.setActivityName(activityNameList);
        }
        return form;
    }
}
