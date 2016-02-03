package com.elstele.bill.filesWorkers;

import com.elstele.bill.form.FileDirTreeGeneraterForm;
import com.elstele.bill.form.FileInDirTreeGeneraterForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileTreeGenerater {
    final static Logger LOGGER = LogManager.getLogger(FileTreeGenerater.class);

    public FileDirTreeGeneraterForm[] getFileTreeArray(String path) {
        File fileDir = new File(path);
        File[] filesArr = fileDir.listFiles();
        List<File> filesList = new ArrayList<File>();
        for (File aFilesArr : filesArr) {
            if (aFilesArr.isFile()) {
                try {
                    filesList.add(aFilesArr);
                    LOGGER.info("File " + aFilesArr.getName() + " is added to Array");
                } catch (SecurityException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            } else if (aFilesArr.isDirectory()) {
                filesList.add(aFilesArr);
                File[] fileArrWithChildDirectory = aFilesArr.listFiles();
                LOGGER.info(aFilesArr.getName() + " is Directory");
                assert fileArrWithChildDirectory != null;
                for (File aFileArrWithChildDirectory : fileArrWithChildDirectory) {
                    if (aFileArrWithChildDirectory.isFile()) {
                        try {
                            filesList.add(aFileArrWithChildDirectory);
                            LOGGER.info(aFileArrWithChildDirectory.getName() + " is added to Array");
                        } catch (SecurityException e) {
                            LOGGER.error(e.getMessage(), e);
                        }
                    }

                }

            }
        }


        List<FileDirTreeGeneraterForm> dirList = new ArrayList<FileDirTreeGeneraterForm>();
        List<FileInDirTreeGeneraterForm> fileList = new ArrayList<FileInDirTreeGeneraterForm>();
        for (File file : filesList) {
            if (file.isDirectory()) {
                FileDirTreeGeneraterForm fileForm = new FileDirTreeGeneraterForm();
                fileForm.setUrl(file.getAbsolutePath());
                fileForm.setType("dir");
                fileForm.setName(file.getName());
                fileForm.setId(file.getName());
                dirList.add(fileForm);
            }
            if (file.isFile()) {
                FileInDirTreeGeneraterForm dirForm = new FileInDirTreeGeneraterForm();
                dirForm.setId(file.getName());
                dirForm.setName(file.getName());
                dirForm.setType("txt");
                dirForm.setUrl(file.getAbsolutePath());
                fileList.add(dirForm);
            }
        }
        for (FileDirTreeGeneraterForm fileDirTreeGeneraterForm : dirList) {
            for (FileInDirTreeGeneraterForm fileInDirTreeGeneraterForm : fileList) {
                int indexOfLast = fileInDirTreeGeneraterForm.getUrl().lastIndexOf("\\");
                String newStr = "";

                if (indexOfLast >= 0) {
                    newStr = fileInDirTreeGeneraterForm.getUrl().substring(0, indexOfLast);
                }

                if (fileDirTreeGeneraterForm.getUrl().equalsIgnoreCase(newStr)) {
                    fileDirTreeGeneraterForm.setChildren(fileInDirTreeGeneraterForm);
                }

            }
            fileDirTreeGeneraterForm.getDataAsArray();
        }
        return dirList.toArray(new FileDirTreeGeneraterForm[dirList.size()]);
    }

}
