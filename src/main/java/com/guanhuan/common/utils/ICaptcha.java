package com.guanhuan.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;

/**
 * ��֤��ӿ�
 */
public interface ICaptcha {
    public BufferedImage getImage(HttpServletRequest request);
    public BufferedImage getImage(HttpServletRequest request, int width_, int height_, int fontSize_, int codeLength_);
    public String getRand(HttpServletRequest request);
}
