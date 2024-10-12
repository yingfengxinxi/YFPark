package cn.sdqingyun.smartpark.framework.common.util.pdf;


import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


public class WordToPdfUtil {
    /**
     * 获取 license 去除水印
     * 若不验证则转化出的pdf文档会有水印产生
     */
    public static void getLicense() {
        try {
            String licenseXml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<License>\n" +
                    "  <Data>\n" +
                    "    <Products>\n" +
                    "      <Product>Aspose.Total for Java</Product>\n" +
                    "      <Product>Aspose.Words for Java</Product>\n" +
                    "    </Products>\n" +
                    "    <EditionType>Enterprise</EditionType>\n" +
                    "    <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
                    "    <LicenseExpiry>20991231</LicenseExpiry>\n" +
                    "    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
                    "  </Data>\n" +
                    "  <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>\n" +
                    "</License>";
            InputStream is = new ByteArrayInputStream(licenseXml.getBytes(StandardCharsets.UTF_8));
            License license = new License();
            license.setLicense(Objects.requireNonNull(is));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * word 转 pdf
     *
     * @param wordFile word 文件路径
     * @param pdfFile  生成的 pdf 文件路径
     */
    public static void word2Pdf(String wordFile, String pdfFile) {
        File file = new File(pdfFile);

        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        getLicense();
        try (FileOutputStream os = new FileOutputStream(new File(pdfFile))) {
            Document doc = new Document(wordFile);
            // 检查文档页数
            if (doc.getPageCount() == 0) {
                throw new RuntimeException("Word文档没有内容或格式不受支持。");
            }
            doc.save(os, SaveFormat.PDF);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("word转换PDF失败");
        }
    }

    /**
     * @param @param  fileName
     * @param @return 参数
     * @return String 返回类型
     * @throws
     * @Title: deleteFile
     * @Description:删除生成的word文档
     */
    public static String deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return "success";
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return "error";
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return "exists";
        }
    }
}
