package com.elstele.bill.utils.reportCreaters;

import antlr.StringUtils;
import com.elstele.bill.form.FileDirTreeGeneraterForm;
import com.elstele.bill.form.FileInDirTreeGeneraterForm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileTreeGenerater {

    public FileDirTreeGeneraterForm[] getFileTreeArray(String path) {
        File fileDir = new File(path);
        File[] filesArr = fileDir.listFiles();
        List<File> filesList = new ArrayList<File>();
        for (int i = 0; i < filesArr.length; i++) {
            if (filesArr[i].isFile()) {
                try {
                    filesList.add(filesArr[i]);
                    System.out.println("File " + filesArr[i].getName() + " is added to Array");
                } catch (SecurityException e) {
                    System.out.println(e.toString());
                }
            } else if (filesArr[i].isDirectory()) {
                filesList.add(filesArr[i]);
                File[] fileArrWithChildDirectory = filesArr[i].listFiles();
                System.out.println(filesArr[i].getName() + " is Directory");
                assert fileArrWithChildDirectory != null;
                for (int j = 0; j < fileArrWithChildDirectory.length; j++) {
                    if (fileArrWithChildDirectory[j].isFile()) {
                        try {
                            filesList.add(fileArrWithChildDirectory[j]);
                            System.out.println(fileArrWithChildDirectory[j].getName() + " is added to Array");
                        } catch (SecurityException e) {
                            System.out.println(e.toString());
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
            if(file.isFile()){
                FileInDirTreeGeneraterForm dirForm = new FileInDirTreeGeneraterForm();
                dirForm.setId(file.getName());
                dirForm.setName(file.getName());
                dirForm.setType("txt");
                dirForm.setUrl(file.getAbsolutePath());
                fileList.add(dirForm);
            }
        }
        for(FileDirTreeGeneraterForm fileDirTreeGeneraterForm : dirList){
            for(FileInDirTreeGeneraterForm fileInDirTreeGeneraterForm : fileList){
                int indexOfLast = fileInDirTreeGeneraterForm.getUrl().lastIndexOf("\\");
                String newStr = "";

                if(indexOfLast >= 0) {
                    newStr = fileInDirTreeGeneraterForm.getUrl().substring(0, indexOfLast);
                }

                if(fileDirTreeGeneraterForm.getUrl().equalsIgnoreCase(newStr)){
                    fileDirTreeGeneraterForm.setChildren(fileInDirTreeGeneraterForm);
                }

            }
            fileDirTreeGeneraterForm.getDataAsArray();
        }

       FileDirTreeGeneraterForm[] fileDirTreeGeneraterForms = dirList.toArray(new FileDirTreeGeneraterForm[dirList.size()]);
       return fileDirTreeGeneraterForms;
    }

}
