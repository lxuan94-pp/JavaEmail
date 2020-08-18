/**
 * Created by Shinelon on 2016/12/10.
 */


public class Main {
    public static void main(String[] args) throws Exception {
        ReadThread t1 = new ReadThread();
        t1.start();
        for(int i=0;i<4;i++){
			/*Email send = new Email();*/
            Email.sendMail(JDBC.getEmailAddress(i+1));
			/*System.out.println(JDBC.getEmailAddress(i+1));*/
            Thread.sleep(1000);
        }
        SendThread t2 = new SendThread();
        t2.start();
        Monitor t3 = new Monitor();
        t3.start();
    }
}
