package com.home.user.mobileguard.bean;

/**
 * Created by user on 2016-06-26.
 */
public class ServerJson {
    private String version_name;
    private int version_code;
    private String description;
    private String update_url;

    public ServerJson() {
    }

    public ServerJson(String version_name, int version_code, String description, String update_url) {
        this.version_name = version_name;
        this.version_code = version_code;
        this.description = description;
        this.update_url = update_url;
    }

    @Override
    public String toString() {
        return "ServerJson{" +
                "version_name='" + version_name + '\'' +
                ", version_code=" + version_code +
                ", description='" + description + '\'' +
                ", update_url='" + update_url + '\'' +
                '}';
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }
}
