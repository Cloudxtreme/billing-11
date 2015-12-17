package com.elstele.bill.test.builder.bean;


import com.elstele.bill.domain.UploadedFileInfo;
import com.elstele.bill.utils.Enums.FileStatus;

public class UploadedFileInfoBuilder implements TestObjectCreator<UploadedFileInfoBuilder, UploadedFileInfo> {
    private UploadedFileInfo uploadedFileInfo;

    @Override
    public UploadedFileInfoBuilder build() {
        uploadedFileInfo = new UploadedFileInfo();
        uploadedFileInfo.setFileStatus(FileStatus.NEW);
        return this;
    }

    public UploadedFileInfoBuilder withFileName(String fileName){
        uploadedFileInfo.setFileName(fileName);
        return this;
    }

    public UploadedFileInfoBuilder withFileSize(Long fileSize){
        uploadedFileInfo.setFileSize(fileSize);
        return this;
    }

    public UploadedFileInfoBuilder withPath(String path){
        uploadedFileInfo.setPath(path);
        return this;
    }

    @Override
    public UploadedFileInfo getRes() {
        if(uploadedFileInfo ==null){
            build();
        }
        return uploadedFileInfo;
    }
}
