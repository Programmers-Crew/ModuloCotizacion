/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ModuloCotizacion.controller;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.ModuloCotizacion.bean.SFTPChannel;
import org.ModuloCotizacion.bean.SFTPConstants;



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
}
