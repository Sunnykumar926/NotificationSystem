import java.util.*;
import java.time.LocalTime;

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
