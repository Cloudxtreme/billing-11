package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@FilterDef(name="showActive", parameters={
        @ParamDef( name="exclude", type="string" )
})
@Table(name="UserRole")
public class UserRole extends CommonDomainBean{

    @Column(unique = true)
    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ROLE_ACTIVITY",
            joinColumns = @JoinColumn(name = "USERROLE_ID", unique=false),
            inverseJoinColumns = @JoinColumn(name = "ACTIVITY_ID", unique=false))
    @Filter(name="showActive", condition="status != :exclude")
    private List<Activity> activities = new ArrayList<Activity>();

    public UserRole(){
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public void addActivity(Activity activity){
        activities.add(activity);
    }

    public void delActivity(Activity activity){
        activities.remove(activity);
    }

}
