package com.elstele.bill.form;

import java.util.ArrayList;

public class FileDirTreeGeneraterForm {
    private String id;
    private String name;
    private String type;
    private String url;
    private FileInDirTreeGeneraterForm[] children;
    private ArrayList<FileInDirTreeGeneraterForm> childrenList;

    public FileDirTreeGeneraterForm(){
        childrenList = new ArrayList<FileInDirTreeGeneraterForm>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public FileInDirTreeGeneraterForm[] getChildren() {

        return children;
    }

    public void setChildren(FileInDirTreeGeneraterForm children) {
        childrenList.add(children);
        //this.children = childrenList.toArray(new FileInDirTreeGeneraterForm[childrenList.size()]);
    }

    public FileInDirTreeGeneraterForm[] getDataAsArray() {
        //children = new FileInDirTreeGeneraterForm[childrenList.size()];
        children = childrenList.toArray(new FileInDirTreeGeneraterForm[childrenList.size()]);
        return children;
    }
}
