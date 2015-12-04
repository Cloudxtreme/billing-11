package com.elstele.bill.form;

import com.elstele.bill.utils.Enums.FileStatus;

public class UploadedFileInfoForm {
    private Integer id;
    private String fileName;
    private Long fileSize;
    private String path;
    public FileStatus fileStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
        if (!(o instanceof UploadedFileInfoForm)) return false;

        UploadedFileInfoForm that = (UploadedFileInfoForm) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (fileSize != null ? !fileSize.equals(that.fileSize) : that.fileSize != null) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        return fileStatus == that.fileStatus;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (fileSize != null ? fileSize.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (fileStatus != null ? fileStatus.hashCode() : 0);
        return result;
    }
}
