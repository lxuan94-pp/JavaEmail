/**
 * Created by Shinelon on 2016/12/10.
 */


        import java.util.Properties;
        import javax.mail.Authenticator;
        import javax.mail.Folder;
        import javax.mail.PasswordAuthentication;
        import javax.mail.Session;
        import javax.mail.Store;

public class Monitor extends Thread{
    private static final String host1="pop3.sina.com.cn";
    private static final String username="yrlx_1314@sina.com";
    private static final String password="wolovelx1314yr**";
    public void run(){
        try{
            int n=5;//初始邮件量
            while(true){



                Properties props = System.getProperties();
                Session session = Session.getDefaultInstance(props, new Authenticator(){
                    public PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(username, password);
                    }

                });
                Store store=session.getStore("pop3");
                store.connect(host1,username,password);
                Folder folder=store.getFolder("INBOX");
                while(folder.getMessageCount()!=n){

                    System.out.println("New message comes!!!Come and look at it!~");
                    n=folder.getMessageCount();
                }

				/*if( folder.getMessageCount() != 4)
				{
					System.out.println("New message comes!!!Come and look at it!~");
				}*/

            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}



