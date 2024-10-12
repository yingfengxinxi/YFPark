package cn.sdqingyun.smartpark.framework.common.util.sms;


/**
 * @Author lvzy
 * @Date 2024/3/1 10:08
 */

public class SendMsgRequest{

    //阿里云短信key
    private String accessKeyId;
    //	阿里云短信密钥
    private String accessKeySecret;
    //阿里云短信签名
    private String signName;
    //模板code
    private String templateCode;

    //发送人手机号
    private String  phoneNumber;

    //发送参数 {"":"","":""}
    private String templateParam;
    //地域
    private String region;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }
}
