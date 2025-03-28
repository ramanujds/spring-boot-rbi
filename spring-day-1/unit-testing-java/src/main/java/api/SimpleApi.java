package api;

import service.SimpleService;

public class SimpleApi {

    private SimpleService service;

    public void setService(SimpleService service) {
        this.service = service;
    }

    public String getUpperCaseStringResponse(String str){
        return service.getStringInUpperCase(str);
    }

}
