package com.elstele.bill.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="UserRoleForm")
public class UserRole {

    @Id
    @Column(name="id")
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    @OneToMany
    private Set<Activity> activities;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public void addActivity(Activity activity){
        activities.add(activity);
    }

    public void delActivity(Activity activity){
        activities.remove(activity);
    }
}
