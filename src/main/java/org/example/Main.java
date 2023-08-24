package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SFTPUtil sftpUtil = new SFTPUtil();
        String fileContent = sftpUtil.readFileFromSftp("10.16.32.225", 22, "sftpappuser", "Test@123",
                "/C:/Users/sftpappuser/EmployeeRecord", "HappiestMinds.json");
        System.out.println("fileContent: " + fileContent);
        List<String> listOfFiles = sftpUtil.getAllFilesInDir("10.16.32.225", 22, "sftpappuser", "Test@123",
                "/C:/Users/sftpappuser/EmployeeRecord");
        System.out.println("listOfFiles = " + listOfFiles);
    }
}