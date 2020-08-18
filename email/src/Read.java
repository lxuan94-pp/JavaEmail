/**
 * Created by Shinelon on 2016/12/10.
 */


import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class Read {
    private MimeMessage mimeMessage=null;
    private  StringBuffer bodytext=new StringBuffer();
    public Read() {
    }
    public Read(MimeMessage mimeMessage1) {
        this.mimeMessage=mimeMessage1;
    }
    public void setMimeMessage(MimeMessage mimeMessage){
        this.mimeMessage=mimeMessage;
    }
    /**
     　*　获得发件人的地址和姓名
     　*/
    public String getFrom1()throws Exception{
        InternetAddress address[]=(InternetAddress[])mimeMessage.getFrom();
        String from=address[0].getAddress();
        if(from==null){
            from="";
        }
        String personal=address[0].getPersonal();
        if(personal==null){
            personal="";
        }
        String fromaddr=personal+"<"+from+">";
        return fromaddr;
    }
    /**
     　*　获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同
     　*　"to"----收件人　"cc"---抄送人地址　"bcc"---密送人地址 　
     * @throws Exception */
    public String getMailAddress(String type){
        String mailaddr="";
        try {
            String addtype=type.toUpperCase();
            InternetAddress []address=null;
            if(addtype.equals("TO")||addtype.equals("CC")||addtype.equals("BBC")){
                if(addtype.equals("TO")){
                    address=(InternetAddress[])mimeMessage.getRecipients(Message.RecipientType.TO);
                }
                else if(addtype.equals("CC")){
                    address=(InternetAddress[])mimeMessage.getRecipients(Message.RecipientType.CC);
                }
                else{
                    address=(InternetAddress[])mimeMessage.getRecipients(Message.RecipientType.BCC);
                }
                if(address!=null){
                    for (int i = 0; i < address.length; i++) {
                        String email=address[i].getAddress();
                        if(email==null)email="";
                        else{
                            email=MimeUtility.decodeText(email);
                        }
                        String personal=address[i].getPersonal();
                        if(personal==null)personal="";
                        else{
                            personal=MimeUtility.decodeText(personal);
                        }
                        String compositeto=personal+"<"+email+">";
                        mailaddr+=","+compositeto;
                    }
                    mailaddr=mailaddr.substring(1);
                }
            }
            else{
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return mailaddr;
    }
    /**
     　　*　获得邮件主题 　　
     */
    public String getSubject()
    {
        String subject="";
        try {
            subject=MimeUtility.decodeText(mimeMessage.getSubject());
            if(subject==null)subject="";
        } catch (Exception e) {
            // TODO: handle exception
        }
        return subject;
    }
    /**
     　　*　解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件
     　　*　主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
     　　*/
    public void getMailContent(Part part)throws Exception{
        String contenttype=part.getContentType();
        int nameindex=contenttype.indexOf("name");
        boolean conname=false;
        if(nameindex!=-1)conname=true;
        if(part.isMimeType("text/plain")&&!conname){
            bodytext.append((String)part.getContent());
        }else if(part.isMimeType("text/html")&&!conname){
            bodytext.append((String)part.getContent());
        }
        else if(part.isMimeType("multipart/*")){
            Multipart multipart=(Multipart)part.getContent();
            int counts=multipart.getCount();
            for(int i=0;i<counts;i++){
                getMailContent(multipart.getBodyPart(i));
            }
        }else if(part.isMimeType("message/rfc822")){
            getMailContent((Part)part.getContent());
        }
        else{}
    }

    public static boolean isContain(String s1, String s2){
        return s1.contains(s2);
    }

    public String getBodyText(){
        return bodytext.toString();
    }


}

