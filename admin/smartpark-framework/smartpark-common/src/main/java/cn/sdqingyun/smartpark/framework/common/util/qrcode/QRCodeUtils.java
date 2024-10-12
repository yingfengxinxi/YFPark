package cn.sdqingyun.smartpark.framework.common.util.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author lvzy
 * @Date 2024/6/18 15:51
 */
public class QRCodeUtils {


    public static String generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        filePath = coverPath(filePath);
        File file = new File(filePath);
        if (!file.exists()) {//如果文件夹不存在
            //file.mkdir();创建单个文件夹
            file.mkdirs();//mkdirs创建多级文件夹
        }


        System.out.println("************" + filePath);
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, String> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            //这个java.nio.file.FileSystems必须在1.7以上才可以
            Path path = Paths.get(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        } catch (WriterException e) {
            System.out.println("未能正常生成二维码，WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("未能正常生成二维码, IOException :: " + e.getMessage());
        }
        return filePath;
    }

    public static String generateQRCodeBase64Image(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        String formatName="PNG";
        MatrixToImageWriter.writeToStream(bitMatrix, formatName, pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        String base64String ="data:image/" + formatName + ";base64," + Base64.getEncoder().encodeToString(pngData);
        return base64String;
    }

    public static void main(String[] args) {
        try {
            UUID uuid = UUID.randomUUID();
            String guid = uuid.toString();
            String filePath = "D:\\";
            String QR_CODE_IMAGE_PATH = "d:\\xxx";
            generateQRCodeImage("This is my first QR Code", 350, 350, filePath);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }

    }

    // 生成QR码的方法,返回文件数组
    public static byte[] generateQRCode(String data, int width, int height) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.MARGIN, 1);

            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height, hints);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }

            ImageIO.write(image, "png", bos);
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate QR code", e);
        }
    }

    // 生成条形码的方法
    public static String generateBarcode(String data, int width, int height, String filePath) {
        filePath = coverPath(filePath);
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 设置字符编码

            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.CODE_128, width, height, hints);

            // 创建BufferedImage对象来表示条形码
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0 : 0xFFFFFF); // 生成黑色条和白色背景的条形码
                }
            }

            // 将条形码保存到文件
            File barcodeFile = new File(filePath);
            ImageIO.write(image, "png", barcodeFile);

            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String coverPath(String path) {
        UUID uuid = UUID.randomUUID();
        String guid = uuid.toString();
        path = path + guid + ".png";
        return path;
    }

}
