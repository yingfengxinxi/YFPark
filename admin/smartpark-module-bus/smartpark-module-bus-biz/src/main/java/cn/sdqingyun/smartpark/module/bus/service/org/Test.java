package cn.sdqingyun.smartpark.module.bus.service.org;

import cn.sdqingyun.smartpark.framework.common.util.bill.Tools;
import cn.sdqingyun.smartpark.framework.common.util.pdf.MoneyConverter;
import cn.sdqingyun.smartpark.framework.common.util.pdf.WordToPdfUtil;
import cn.sdqingyun.smartpark.framework.dict.core.DictFrameworkUtils;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.contract.ContractOrderBillDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillCostTypeDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.org.OrgBillReceiptTemplateDO;
import cn.sdqingyun.smartpark.module.bus.dal.dataobject.village.BuildDO;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.deepoove.poi.XWPFTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @Author lvzy
 * @Date 2024/7/24 10:45
 */
public class Test {

    public static void main(String[] args) throws IOException {

        String path = "/usr/uploads/receopt";
        File filepath = new File(path);
        if (!filepath.exists()) {//如果文件夹不存在
            /*filepath.setWritable(true, false);*/    //设置写权限，windows下不用此语句
            filepath.mkdirs();//创建文件夹
        }
        String uuid = UuidUtils.generateUuid();
        String docPath = path + "/" + uuid + ".docx";
        //开据人
        //String templatePath = "C:\\Users\\Administrator\\Desktop\\receipt_example.docx";
        String templatePath = "http://39.91.167.3:81/admin-api/infra/file/24/get/4223610f0147bea50474122b4732428dd543ef2bdb00e4bd4e080bcc73de5d9f.docx";
        // 创建URL对象
        URL url = new URL(templatePath); // 替换为你要读取的URL
        // 打开连接
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        XWPFTemplate template = XWPFTemplate.compile(inputStream).render(
                new HashMap<String, Object>() {{
                    //收据信息
                    put("开据人", "我是开据人");
                    put("开据时间", "2024-07-24");
                    put("开据金额D", MoneyConverter.convert(Double.valueOf(Tools.DecimalFormat("1000.01"))));
                    put("开据金额X", "1000.01");
                    put("费用名称", "我是费用名称");
                    put("费用类型", "我是费用类型");
                    put("汇款方式", "我是汇款方式");
                    put("收据编号", "10000001");

                    //交收款方
                    put("收款方地址", "我是收款方地址");
                    put("收款方电话", "我是收款方电话");
                    put("收款人", "我是收款人");
                    put("收款单位", "我是收款单位");
                    put("交款方地址", "我是交款方地址");
                    put("交款方电话", "我是交款方电话");
                    put("交款人", "我是交款人");
                    put("交款单位", "我是交款单位");
                    //房源信息
                    put("楼宇名称", "8号楼");
                    put("楼层房号", "1-1,2-3");
                    //账单信息
                    put("账单编号", "12123213213213213213321");
                    put("楼宇名称", "8号楼");

                }});

        template.write(new FileOutputStream(docPath));
        String pdfPath = path + "/" + uuid + ".pdf";
        // 将word转换为pdf
        WordToPdfUtil.word2Pdf(docPath, pdfPath);// E:\\test\\test.docx为word文档路径
        FileInputStream is = new FileInputStream(pdfPath);
        is.close();
    }
}
