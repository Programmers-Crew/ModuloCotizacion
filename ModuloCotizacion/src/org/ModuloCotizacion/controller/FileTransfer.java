package org.ModuloCotizacion.controller;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ModuloCotizacion.bean.SFTPChannel;
import org.ModuloCotizacion.bean.SFTPConstants;
import static org.apache.log4j.AsyncAppender.DEFAULT_BUFFER_SIZE;



public class FileTransfer {
        String server = "alcongt.com";
        String user = "alcongt";
        String pass = "Z3UxyCb3-2021";
 
        
        
    public void putResource(File file) {
        SFTPChannel channel = new SFTPChannel();
        Map<String, String> sftpDetails = new HashMap<String, String>();
        // Establecer la ip del host, puerto, nombre de usuario, contraseña
        sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, "alcongt.com");
        sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, "alcongt");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, "Z3UxyCb3-2021");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, "22");
        ChannelSftp chSftp = null;
        try {
            chSftp = channel.getChannel(sftpDetails, 60000);            
            String path = "public_html/files/resource";
            chSftp.cd(path);
            chSftp.put(file.getAbsolutePath(), file.getName(), ChannelSftp.OVERWRITE);
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            chSftp.quit();
            try {
                channel.closeChannel();
            } catch (Exception e) {
            }
        }
    }
    
    public File getResource(String name){
        SFTPChannel channel = new SFTPChannel();
        Map<String, String> sftpDetails = new HashMap<String, String>();
        // Establecer la ip del host, puerto, nombre de usuario, contraseña
        sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, "alcongt.com");
        sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, "alcongt");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, "Z3UxyCb3-2021");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, "22");
        ChannelSftp chSftp = null;
        File file = null;
        File targetFile = new File("");
        try {
            chSftp = channel.getChannel(sftpDetails, 60000);       
            String path = "public_html/files/resource";
             chSftp.get(path + "/" + name, "C:/Program Files (x86)/ModuloCotizacion/ModuloCotizacion/img/");

            
        }catch(JSchException e){
            e.printStackTrace();
        }   catch (SftpException ex) {
                Logger.getLogger(FileTransfer.class.getName()).log(Level.SEVERE, null, ex);
            }  
        file = targetFile;
        return file;
    }
    
    private static void copyInputStreamToFile(InputStream inputStream, File file)throws IOException {

        // append = false
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

    }
}
