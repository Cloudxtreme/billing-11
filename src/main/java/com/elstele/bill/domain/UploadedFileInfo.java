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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UploadedFileInfo)) return false;

        UploadedFileInfo that = (UploadedFileInfo) o;

        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (fileSize != null ? !fileSize.equals(that.fileSize) : that.fileSize != null) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        return fileStatus == that.fileStatus;

    }

    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + (fileSize != null ? fileSize.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (fileStatus != null ? fileStatus.hashCode() : 0);
        return result;
    }
}
