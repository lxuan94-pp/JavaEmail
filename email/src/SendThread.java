/**
 * Created by Shinelon on 2016/12/10.
 */




public class SendThread extends Thread{
    public void run(){
        try{
		/*JDBC.insert(new JDBC.Mail("729022080@qq.com"));//插入数据，只需一次就好
	    JDBC.insert(new JDBC.Mail("602891622@qq.com"));
	    JDBC.insert(new JDBC.Mail("313388148@qq.com"));
	    JDBC.insert(new JDBC.Mail("462998337@qq.com"));*/
            JDBC.getEmailAddress(0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
