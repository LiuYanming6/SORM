package com.liuming.sorm.bean;

/**
 * 管理配置信息
 *
 * @author liuming
 */
public class Configuration {
    private String driver;
    private String url;
    private String user;
    private String pwd;
    /**
     * 正在使用的数据库
     */
    private String usingDB;
    /**
     * 项目的源码路径
     */
    private String srcPath;
    /**
     * 扫描生成java类的包(po-persistence object持久化对象)
     */
    private String poPackage;

    public Configuration() {
    }

    public Configuration(String driver, String url, String user, String pwd, String usingDB, String srcPath, String poPackage) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.pwd = pwd;
        this.usingDB = usingDB;
        this.srcPath = srcPath;
        this.poPackage = poPackage;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", pwd='" + pwd + '\'' +
                ", usingDB='" + usingDB + '\'' +
                ", srcPath='" + srcPath + '\'' +
                ", poPackage='" + poPackage + '\'' +
                '}';
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUsingDB() {
        return usingDB;
    }

    public void setUsingDB(String usingDB) {
        this.usingDB = usingDB;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getPoPackage() {
        return poPackage;
    }

    public void setPoPackage(String poPackage) {
        this.poPackage = poPackage;
    }
}
