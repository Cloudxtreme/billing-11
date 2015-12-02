package com.elstele.bill.domain;


import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.Enums.FileStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name="uploadedfileinfo")
public class UploadedFileInfo extends CommonDomainBean {
    private String fileName;
    private Long fileSize;
    private String path;

    @Enumerated(EnumType.STRING)
    public FileStatus fileStatus;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FileStatus getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(FileStatus fileStatus) {
        this.fileStatus = fileStatus;
    }
}
