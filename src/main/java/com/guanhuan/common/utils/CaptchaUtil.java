package com.guanhuan.common.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * ͼ����֤�빤����
 */
@Component
public class CaptchaUtil implements ICaptcha {
    private int width = 120;
    private int height = 38;
    private int codeLength = 4;
    private String randomString = "ABCDEFGHIJKLMNPQRSTUVWXYZ1234567890abcdefghijkmnpqrstuvwxyz";
    private String sessionKey = "SESSIONCODE";
    private String fontName = "Times New Roman";
    private int fontStyle = 0;
    private int fontSize = 18;

    public BufferedImage getImage(HttpServletRequest request) {
        // ���ڴ��д���ͼ��
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // ��ȡͼ��������
        Graphics g = image.getGraphics();
        // ���������
        Random random = new Random();
        // �趨����ɫ
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        // �趨����
        g.setFont(new Font(fontName, fontStyle, fontSize));
        g.setColor(getRandColor(160, 200));
        drawLine(g, width, height);
        String sRand = randomRand(codeLength);// ȡ�����������֤��
        int strWidth = width / 2 - g.getFontMetrics().stringWidth(sRand) / codeLength - 24;
        int strHeight = height / 2 + 12;
        for (int i = 0; i < codeLength; i++) {
            String rand = sRand.substring(i, i + 1);
            // ����֤����ʾ��ͼ����
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));// ���ú�����������ɫ��ͬ��
            int zz = new Random().nextInt(8);
            zz = zz % 2 == 0 ? zz - 10 : zz;
            g.drawString(rand, strWidth + (13 + 16 * i), strHeight + zz);
        }
        request.getSession().setAttribute(sessionKey, sRand);
        g.dispose();
        return image;
    }

    public BufferedImage getImage(HttpServletRequest request, int width_, int height_, int fontSize_, int codeLength_) {
        // ���ڴ��д���ͼ��
        BufferedImage image = new BufferedImage(width_, height_, BufferedImage.TYPE_INT_RGB);
        // ��ȡͼ��������
        Graphics g = image.getGraphics();
        // ���������
        Random random = new Random();
        // �趨����ɫ
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width_, height_);
        // �趨����
        Font f = new Font(fontName, fontStyle, fontSize_);
        g.setFont(f);
        g.setColor(getRandColor(160, 200));
        drawLine(g,width_,height_);
        String sRand = randomRand(codeLength_);// ȡ�����������֤��
        int strWidth = width_ / 2 - g.getFontMetrics().stringWidth(sRand) / codeLength_ - fontSize_;
        int strHeight = height_ / 2 + g.getFontMetrics(f).getHeight() / 4;
        for (int i = 0; i < codeLength_; i++) {
            String rand = sRand.substring(i, i + 1);
            // ����֤����ʾ��ͼ����
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));// ���ú�����������ɫ��ͬ��
            g.drawString(rand, 13 * i + 6 + strWidth, strHeight);
        }
        request.getSession().setAttribute(sessionKey, sRand);
        g.dispose();
        return image;
    }

    private void drawLine(Graphics g, int width, int height){
        // ���������
        Random random = new Random();
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
    }

    @Override
    public String getRand(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(sessionKey);
    }

    private Color getRandColor(int fc, int bc) {// ������Χ��������ɫ
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private String randomRand(int n) {
//        String rand = "";
        StringBuilder rand = new StringBuilder();
        int len = randomString.length() - 1;
        double r;
        for (int i = 0; i < n; i++) {
            r = (Math.random()) * len;
            rand.append(randomString.charAt((int) r));
        }
        return rand.toString();
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setCodeLength(int codeLength) {
        this.codeLength = codeLength;
    }

    public void setRandomString(String randomString) {
        this.randomString = randomString;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public void setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getSessionKey() {
        return sessionKey;
    }
}
