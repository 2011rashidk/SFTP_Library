package org.example;

import com.jcraft.jsch.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SFTPUtil {

    public String readFileFromSftp(String sftpHost, int sftpPort, String sftpUsername, String sftpPassword, String sftpDirectory, String fileName) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(sftpUsername, sftpHost, sftpPort);
            ChannelSftp channel = connectSftpServer(sftpPassword, session);
            channel.cd(sftpDirectory);
            channel.get(fileName);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            channel.get(fileName, outputStream);
            String fileContent = outputStream.toString();
            channel.disconnect();
            session.disconnect();
            return fileContent;
        } catch (JSchException | SftpException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAllFilesInDir(String sftpHost, int sftpPort, String sftpUsername, String sftpPassword, String sftpDirectory) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(sftpUsername, sftpHost, sftpPort);
            ChannelSftp channel = connectSftpServer(sftpPassword, session);
            Vector<ChannelSftp.LsEntry> listOfFiles = channel.ls(sftpDirectory);
            List<String> files = new ArrayList<>();
            for (ChannelSftp.LsEntry lsEntry : listOfFiles) {
                files.add(lsEntry.getFilename());
            }
            return files;
        } catch (JSchException | SftpException e) {
            throw new RuntimeException(e);
        }
    }

    private static ChannelSftp connectSftpServer(String sftpPassword, Session session) throws JSchException, SftpException {
        try {
            session.setPassword(sftpPassword);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            channel.getHome();
            return channel;
        } catch (JSchException | SftpException e) {
            throw new RuntimeException(e);
        }
    }

}
