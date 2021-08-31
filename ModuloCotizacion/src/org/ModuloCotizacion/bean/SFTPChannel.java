package org.ModuloCotizacion.bean;


import java.util.Map;
import java.util.Properties;
 
import org.apache.log4j.Logger;
 
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.ModuloCotizacion.bean.SFTPConstants;
 
public class SFTPChannel {
    Session session = null;
    Channel channel = null;
 
    private static final Logger LOG = Logger.getLogger(SFTPChannel.class.getName());
 
    public ChannelSftp getChannel(Map<String, String> sftpDetails, int timeout) throws JSchException {
 
        String ftpHost = sftpDetails.get(SFTPConstants.SFTP_REQ_HOST);
        String port = sftpDetails.get(SFTPConstants.SFTP_REQ_PORT);
        String ftpUserName = sftpDetails.get(SFTPConstants.SFTP_REQ_USERNAME);
        String ftpPassword = sftpDetails.get(SFTPConstants.SFTP_REQ_PASSWORD);
 
        int ftpPort = SFTPConstants.SFTP_DEFAULT_PORT;
        if (port != null && !port.equals("")) {
            ftpPort = Integer.valueOf(port);
        }
 
        JSch jsch = new JSch (); // Crear objeto JSch
                 session = jsch.getSession (ftpUserName, ftpHost, ftpPort); // Obtenga un objeto Session de acuerdo con el nombre de usuario, la IP del host y el puerto
        LOG.debug("Session created.");
        if (ftpPassword != null) {
                         session.setPassword (ftpPassword); // establecer contraseña
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
                 session.setConfig (config); // Establecer propiedades para el objeto Session
                 session.setTimeout (timeout); // establecer el tiempo de espera
                 session.connect (); // Establecer un enlace a través de Session
        LOG.debug("Session connected.");
 
        LOG.debug("Opening Channel.");
                 channel = session.openChannel ("sftp"); // Abre el canal SFTP
                 channel.connect (); // Establecer una conexión al canal SFTP
        LOG.debug("Connected successfully to ftpHost = " + ftpHost + ",as ftpUserName = " + ftpUserName
                + ", returning: " + channel);
        return (ChannelSftp) channel;
    }
 
    public void closeChannel() throws Exception {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }
}