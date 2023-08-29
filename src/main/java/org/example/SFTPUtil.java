package org.example;

import com.jcraft.jsch.*;

import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SFTPUtil {

    public static final int PORT = 22;

    public void test() {
        System.out.println("Hello from SFTP library");
    }

    public String readFileFromSftp(String sftpUsername, String sftpPassword, String sftpDirectory, String fileName) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(sftpUsername, InetAddress.getLocalHost().getHostAddress(), PORT);
            ChannelSftp channel = connectSftpServer(sftpPassword, session);
            channel.cd(sftpDirectory);
            channel.get(fileName);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            channel.get(fileName, outputStream);
            String fileContent = outputStream.toString();
            channel.disconnect();
            session.disconnect();
            return fileContent;
        } catch (JSchException | SftpException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAllFilesInDir(String sftpUsername, String sftpPassword, String sftpDirectory) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(sftpUsername, InetAddress.getLocalHost().getHostAddress(), PORT);
            ChannelSftp channel = connectSftpServer(sftpPassword, session);
            Vector<ChannelSftp.LsEntry> listOfFiles = channel.ls(sftpDirectory);
            List<String> files = new ArrayList<>();
            for (ChannelSftp.LsEntry lsEntry : listOfFiles) {
                files.add(lsEntry.getFilename());
            }
            return files;
        } catch (JSchException | SftpException | UnknownHostException e) {
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
