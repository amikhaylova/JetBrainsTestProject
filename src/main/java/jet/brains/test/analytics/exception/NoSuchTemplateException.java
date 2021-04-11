package jet.brains.test.analytics.exception;

public class NoSuchTemplateException extends RuntimeException{
    public NoSuchTemplateException(String id){
        super(String.format("Template with id %s does not exist", id));
    }
}
