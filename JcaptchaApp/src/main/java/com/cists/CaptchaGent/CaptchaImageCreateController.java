package com.cists.CaptchaGent;

import java.awt.image.BufferedImage;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.octo.captcha.service.CaptchaServiceException;

@Controller
public class CaptchaImageCreateController {

    public static final ThreadLocal<String> CODETOCREATE = new ThreadLocal<>();

    public void afterPropertiesSet() throws Exception {

    }

    @SuppressWarnings("restriction")
    @RequestMapping("/captcha")
    public Model Captcha(@RequestParam("code") String code, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {

        if (code.length() != 4) {
            throw new Exception("字符串长度不够 error1");
        }
        String words = "[a-zA-Z0-9]{4}";
        Pattern p = Pattern.compile(words);
        if (!p.matcher(code).find()) {
            throw new Exception("字符串类型合适 error2");
        }
        CODETOCREATE.set(code);
        System.out.println("get Code" + code);
        // ValidatorCode codeUtil = ValidatorCodeUtil.getCode();
        // System.out.println("code=" + codeUtil.getCode());
        //
        // arg0.getSession().setAttribute("code", codeUtil.getCode());
        // // 禁止图像缓存。
        // response.setHeader("Pragma", "no-cache");
        // response.setHeader("Cache-Control", "no-cache");
        // response.setDateHeader("Expires", 0);
        // response.setContentType("image/jpeg");
        //
        // ServletOutputStream sos = null;
        // try {
        // // 将图像输出到Servlet输出流中。
        // sos = response.getOutputStream();
        // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(sos);
        // encoder.encode(codeUtil.getImage());
        // sos.flush();
        // sos.close();
        // } catch (Exception e) {
        // } finally {
        // if (null != sos) {
        // try {
        // sos.close();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // }
        // return null;

        // byte[] captchaChallengeAsJpeg = null;
        // // the output stream to render the captcha image as jpeg into
        // ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        // try {
        // // get the session id that will identify the generated captcha.
        // // the same id must be used to validate the response, the session id
        // // is a good candidate!
        // String captchaId = httpServletRequest.getSession().getId();
        // // call the ImageCaptchaService getChallenge method
        // BufferedImage challenge = CaptchaServiceSingleton.getInstance()
        // .getImageChallengeForID(captchaId,
        // httpServletRequest.getLocale());
        //
        // // a jpeg encoder
        // JPEGImageEncoder jpegEncoder = JPEGCodec
        // .createJPEGEncoder(jpegOutputStream);
        // jpegEncoder.encode(challenge);
        // } catch (IllegalArgumentException e) {
        // httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
        // return null;
        // } catch (CaptchaServiceException e) {
        // httpServletResponse
        // .sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        // return null;
        // }
        // captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        // // flush it in the response
        // httpServletResponse.setHeader("Cache-Control", "no-store");
        // httpServletResponse.setHeader("Pragma", "no-cache");
        // httpServletResponse.setDateHeader("Expires", 0);
        // httpServletResponse.setContentType("image/jpeg");
        // ServletOutputStream responseOutputStream = httpServletResponse
        // .getOutputStream();
        // responseOutputStream.write(captchaChallengeAsJpeg);
        //
        // responseOutputStream.flush();
        // responseOutputStream.close();

        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream out = httpServletResponse.getOutputStream();
        try {
            String captchaId = httpServletRequest.getSession(true).getId();
            BufferedImage challenge = (BufferedImage) CaptchaServiceSingleton.getInstance().getChallengeForID(captchaId,
                    httpServletRequest.getLocale());
            CaptchaServiceSingleton.getInstance().getChallengeForID(captchaId);

            System.out.println(CaptchaServiceSingleton.getInstance().getQuestionForID(captchaId));
            ImageIO.write(challenge, "jpg", out);
            out.flush();
        } catch (CaptchaServiceException e) {

        } finally {
            out.close();
            CODETOCREATE.remove();
        }
        CODETOCREATE.remove();
        return null;
    }
    /**
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws Exception
     */
    @SuppressWarnings("restriction")
    @RequestMapping("/captcha2")
    public Model Captcha2( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        CODETOCREATE.remove();
        // ValidatorCode codeUtil = ValidatorCodeUtil.getCode();
        // System.out.println("code=" + codeUtil.getCode());
        //
        // arg0.getSession().setAttribute("code", codeUtil.getCode());
        // // 禁止图像缓存。
        // response.setHeader("Pragma", "no-cache");
        // response.setHeader("Cache-Control", "no-cache");
        // response.setDateHeader("Expires", 0);
        // response.setContentType("image/jpeg");
        //
        // ServletOutputStream sos = null;
        // try {
        // // 将图像输出到Servlet输出流中。
        // sos = response.getOutputStream();
        // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(sos);
        // encoder.encode(codeUtil.getImage());
        // sos.flush();
        // sos.close();
        // } catch (Exception e) {
        // } finally {
        // if (null != sos) {
        // try {
        // sos.close();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // }
        // return null;
        
        // byte[] captchaChallengeAsJpeg = null;
        // // the output stream to render the captcha image as jpeg into
        // ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        // try {
        // // get the session id that will identify the generated captcha.
        // // the same id must be used to validate the response, the session id
        // // is a good candidate!
        // String captchaId = httpServletRequest.getSession().getId();
        // // call the ImageCaptchaService getChallenge method
        // BufferedImage challenge = CaptchaServiceSingleton.getInstance()
        // .getImageChallengeForID(captchaId,
        // httpServletRequest.getLocale());
        //
        // // a jpeg encoder
        // JPEGImageEncoder jpegEncoder = JPEGCodec
        // .createJPEGEncoder(jpegOutputStream);
        // jpegEncoder.encode(challenge);
        // } catch (IllegalArgumentException e) {
        // httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
        // return null;
        // } catch (CaptchaServiceException e) {
        // httpServletResponse
        // .sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        // return null;
        // }
        // captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        // // flush it in the response
        // httpServletResponse.setHeader("Cache-Control", "no-store");
        // httpServletResponse.setHeader("Pragma", "no-cache");
        // httpServletResponse.setDateHeader("Expires", 0);
        // httpServletResponse.setContentType("image/jpeg");
        // ServletOutputStream responseOutputStream = httpServletResponse
        // .getOutputStream();
        // responseOutputStream.write(captchaChallengeAsJpeg);
        //
        // responseOutputStream.flush();
        // responseOutputStream.close();
        
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream out = httpServletResponse.getOutputStream();
        try {
            String captchaId = httpServletRequest.getSession(true).getId();
            BufferedImage challenge = (BufferedImage) CaptchaServiceSingleton.getInstance().getChallengeForID(captchaId,
                    httpServletRequest.getLocale());
            CaptchaServiceSingleton.getInstance().getChallengeForID(captchaId);
            
//            System.out.println(CaptchaServiceSingleton.getInstance().getQuestionForID(captchaId));
            ImageIO.write(challenge, "jpg", out);
            out.flush();
        } catch (CaptchaServiceException e) {
            
        } finally {
            out.close();
            CODETOCREATE.remove();
        }
        CODETOCREATE.remove();
        return null;
    }
}
