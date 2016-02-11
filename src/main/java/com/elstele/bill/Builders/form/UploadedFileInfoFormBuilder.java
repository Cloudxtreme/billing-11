package com.elstele.bill.Builders.form;

import com.elstele.bill.form.UploadedFileInfoForm;
import com.elstele.bill.utils.Enums.FileStatus;

public class UploadedFileInfoFormBuilder {
    private UploadedFileInfoForm uploadedFileInfoForm;

    public UploadedFileInfoFormBuilder build() {
        uploadedFileInfoForm = new UploadedFileInfoForm();
        uploadedFileInfoForm.setFileStatus(FileStatus.NEW);
        return this;
    }

    public UploadedFileInfoFormBuilder withId(Integer id) {
        uploadedFileInfoForm.setId(id);
        return this;
    }

    public UploadedFileInfoFormBuilder withFileName(String fileName) {
        uploadedFileInfoForm.setFileName(fileName);
        return this;
    }

    public UploadedFileInfoFormBuilder withFileSize(Long fileSize) {
        uploadedFileInfoForm.setFileSize(fileSize);
        return this;
    }

    public UploadedFileInfoFormBuilder withPath(String path) {
        uploadedFileInfoForm.setPath(path);
        return this;
    }


    public UploadedFileInfoForm getRes() {
        if (uploadedFileInfoForm == null) {
            build();
        }
        return uploadedFileInfoForm;
    }
}
