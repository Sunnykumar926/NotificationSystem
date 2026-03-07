import java.util.*;
import java.time.LocalTime;

/*///////////////////////////////////////////////////////
        NOTIFICATION AND DECORATOR 
//////////////////////////////////////////////////////// */
interface INotification{
    public String getContent();
}

class SimpleNotification implements INotification{
    private String content;
    SimpleNotification(String content){
        this.content=content;
    }
    @Override
    public String getContent(){
        return content;
    }
}

abstract class INotificationDecorator implements INotification{
    protected INotification notification;
    public INotificationDecorator(INotification n){
        this.notification = n;
    }
}

class TimeStampDecorator extends INotificationDecorator{
    public TimeStampDecorator(INotification n){
        super(n);
    }
    @Override
    public String getContent(){
        LocalTime currTime = LocalTime.now();
        return currTime+" " + notification.getContent();
    }
}

class SignatureDecorator extends INotificationDecorator{
    private String signature;
    public SignatureDecorator(INotification n, String sign){
        super(n);
        this.signature = sign;
    }
    @Override
    public String getContent(){
        return notification.getContent() + "\n-- "+signature+"\n\n";
    }
}



/* ===================================================
        Observer Pattern Component
   ===================================================*/

interface IObserver{
    public void update();
}

interface IObservable{
    public void addObservers(IObserver observer);
    public void removeObservers(IObserver observer);
    public void notifyObservers();
}

class NotificationObservable implements IObservable{
    private INotification currentNotification;
    private List<IObserver> observers = new ArrayList<>();
    @Override
    public void addObservers(IObserver observer){
        if(!observers.contains(observer)){
            observers.add(observer);
        }
    }
    @Override 
    public void removeObservers(IObserver observer){
        observers.remove(observer);
    }
    @Override
    public void notifyObservers(){
        for(IObserver observer: observers){
            observer.update();
        }
    } 

    public String getNotificationContent(){
        return currentNotification.getContent()
    }
}

class Logger implements IObserver{
    private NotificationObservable notificationObservable;

    public Logger(NotificationObservable notificationObservable){
        this.notificationObservable = notificationObservable;
    }

    public void update(){
        System.out.println("Logging new notification : \n" + notificationObservable.getNotificationContent());
    }
}


/* /////////////////////////////////////////////////////////////////////
                Strategy Design Pattern Components
  ///////////////////////////////////////////////////////////////////// */

interface INotificationStrategy{
    void sendNotification(String content);
}

class EmailStrategy implements INotificationStrategy{
    private String emailId;

    public EmailStrategy(String emailId){
        this.emailId = emailId;
    }
    public void sendNotification(String content){
        System.out.println("Sending email notification to: " + emailId + "\n" + content);
    }
}

class SMSStrategy implements INotificationStrategy{
    private String mobileNumber;
    public SMSStrategy(String mobileNumber){
        this.mobileNumber = mobileNumber;
    }
    public void sendNotification(String content){
        System.out.println("Sending SMS Notification to: "+ mobileNumber + "\n" + content);
    }
}

class PopUpStrategy implements INotificationStrategy{
    public void sendNotification(String content){
        System.out.println("Sending Popup Notification: \n" + content);
    }
}

class NotificationEngine implements IObserver{
    private NotificationObservable notificationObservable;
    List<INotificationStrategy> notificationStrategies = new ArrayList<>();
    public NotificationEngine(NotificationObservable notificationObservable){
        this.notificationObservable = notificationObservable;
    }

    public void addNotificationStrategies(INotificationStrategy ns){
        this.notificationStrategies.add(ns);
    }

    public void update(){
        String notificationContent = notificationObservable.getNotificationContent();
        for(INotificationStrategy strategy: notificationStrategies){
            strategy.sendNotification(notificationContent);
        }
    }
}


/*  Notification System */

public class notificationSystem{
    public static void main(String[] args) {
        
    }
}
