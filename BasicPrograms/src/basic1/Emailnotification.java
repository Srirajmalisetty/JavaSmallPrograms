package basic1;
interface Notification{
	void send(String message);
}
public class Emailnotification implements Notification{
	public void send(String message)
	{
		System.out.println("Email Sent: "+message);
	}

}
class Sms implements Notification{
	@Override
	public void send(String message) {
		System.out.println("Sms Sent: "+message);
	}
}
class Notificationservice{
	private Notification notification;
	public Notificationservice(Notification notification) {
		this.notification=notification;	
	}
	void notify(String message) {
		notification.send(message);
	}
	public static void main(String[] args) {
		Notification n1=new Emailnotification();
		Notificationservice s1=new Notificationservice(n1);
		s1.notify("Welcome via Email");
		Notification n2=new Sms();
		Notificationservice s2=new Notificationservice(n2);
		s2.notify("OTP Via SMS");
		
	}
}
