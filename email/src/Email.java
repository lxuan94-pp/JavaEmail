/**
 * Created by Shinelon on 2016/12/10.
 */


import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;

public class Email{
    private static final String host = "smtp.sina.com";
    private static final String username="yrlx_1314@sina.com";
    private static final String password="wolovelx1314yr**";
    public static void sendMail(String target_address) throws IOException{
        Properties props = System.getProperties();
        props.put("mail.smtp.host",host);
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(username, password);
            }

        });
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    target_address));
            message.setSubject("Java");
			/*message.setText("Do you like me?");*/
            MimeMultipart partList = new MimeMultipart();
            message.setContent(partList);// 把邮件的内容设置为多部件的集合对象
            MimeBodyPart part1 = new MimeBodyPart();
            // 给部件指定内容
            part1.setContent("Xuanbi,do you love me ?<img src='cid:a'><img src='cid:b'>",
                    "text/html;charset=utf-8");//发正文带图
            // 部件添加到集合中
            partList.addBodyPart(part1);
            MimeBodyPart img1 = new MimeBodyPart();
            DataHandler dh = new DataHandler(new FileDataSource("src/lxyr.jpg"));//图片路径
            img1.setDataHandler(dh);
            img1.setContentID("a");

            MimeBodyPart img2 = new MimeBodyPart();
            DataHandler dh2 = new DataHandler(new FileDataSource("src/lxyr_1314.jpg"));//第二张图片路径
            img2.setDataHandler(dh2);
            img2.setContentID("b");

            MimeMultipart mm = new MimeMultipart();
            mm.addBodyPart(part1);
            mm.addBodyPart(img1);
            mm.setSubType("related");// 设置正文与图片之间的关系

            // 图班与正文的 body
            MimeBodyPart all = new MimeBodyPart();
            all.setContent(mm);
            // 附件与正文（text 和 img）的关系
            MimeMultipart mm2 = new MimeMultipart();  //发附件（TXT，JPG之类）
            mm2.addBodyPart(all);
            mm2.addBodyPart(img2);
            mm2.setSubType("mixed");// 设置正文与附件之间的关系

            message.setContent(mm2);
            message.saveChanges(); // 保存修改

            MimeBodyPart part2 = new MimeBodyPart();
            // 为部件指定附件
            part2.attachFile(new File("src/Xuanbi.txt"));
            // 指定附件文件的名字
            // 使用MimeUtility.encodeText()对中文进行编码
            part2.setFileName(MimeUtility.encodeText("Xuanbi.txt"));
            partList.addBodyPart(part2);
            MimeBodyPart part3 = new MimeBodyPart();
            // 为部件指定附件
            part3.attachFile(new File("src/Xbb.jpg"));
            // 指定附件文件的名字
            // 使用MimeUtility.encodeText()对中文进行编码
            part3.setFileName(MimeUtility.encodeText("Xbb.jpg"));
            partList.addBodyPart(part3);



            Transport.send(message);
        }catch (MessagingException e){
            e.printStackTrace();
        }

    }
}