package org.example;

import java.util.List;

public class Main {

    public static final String SFTP_USERNAME = "sftpappuser";
    public static final String SFTP_PASSWORD = "Test@123";
    public static final String SFTP_DIRECTORY = "/C:/Users/sftpappuser/EmployeeRecord";
    public static final String FILE_NAME = "HappiestMinds.json";

    public static void main(String[] args) {
        SFTPUtil sftpUtil = new SFTPUtil();
        String fileContent = sftpUtil.readFileFromSftp(SFTP_USERNAME, SFTP_PASSWORD, SFTP_DIRECTORY, FILE_NAME);
        System.out.println("fileContent: " + fileContent);
        List<String> listOfFiles = sftpUtil.getAllFilesInDir(SFTP_USERNAME, SFTP_PASSWORD, SFTP_DIRECTORY);
        System.out.println("listOfFiles = " + listOfFiles);
    }

}