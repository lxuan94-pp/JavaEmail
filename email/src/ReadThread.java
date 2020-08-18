/**
 * Created by Shinelon on 2016/12/10.
 */


import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

public class ReadThread extends Thread{
    private static final String host1="pop3.sina.com.cn";
    private static final String username="yrlx_1314@sina.com";
    private static final String password="wolovelx1314yr**";
    public void run(){
        try{
            //Properties props=new Properties();
            Properties props = System.getProperties();
            Session session = Session.getDefaultInstance(props, new Authenticator(){
                public PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(username, password);
                }

            });
            Store store=session.getStore("pop3");
            store.connect(host1,username,password);
            Folder folder=store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            Message message[]=folder.getMessages();

            Read pmm=null;
            for (int i = 0; i < message.length; i++) {
                MimeMessage msg = (MimeMessage) message[i];
                System.out.println("****************************************第"+(i+1)+"封邮件**********************************");
                pmm=new Read((MimeMessage)message[i]);
                System.out.println("主题 :"+pmm.getSubject());
                System.out.println("发件人 :"+pmm.getFrom1());
                System.out.println("收件人 :"+pmm.getMailAddress("TO"));
                System.out.println("邮件总数" + folder.getMessageCount());
                pmm.getMailContent((Part)message[i]);  //根据内容的不同解析邮件
                System.out.println("邮件正文 :"+pmm.getBodyText());
                System.out.println("她喜欢我吗："+Read.isContain(pmm.getBodyText(),"yes"));
                System.out.println("*********************************第"+(i+1)+"封邮件结束*************************************");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}

