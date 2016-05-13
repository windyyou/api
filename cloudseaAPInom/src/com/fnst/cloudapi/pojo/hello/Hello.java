package com.fnst.cloudapi.pojo.hello;

public class Hello {
	
    private long id;
    private String content;
    private String type;
    
    public Hello(){
        this.id = 100;
        this.content = "null";
        this.type="null";
    	
    }

    public Hello(long id, String content,String type) {
        this.id = id;
        this.content = content;
        this.type=type;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

	public String getType() {
		return type;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setType(String type) {
		this.type = type;
	}
    
}
