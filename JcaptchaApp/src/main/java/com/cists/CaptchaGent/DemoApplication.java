/**
 *<p>Copyright ® 中国证监会中央监管信息平台版权所有。</p>
 *类名:LoginAction
 *创建人:技术总体组    创建时间:2016-11-01
 */
package com.cists.CaptchaGent;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户登录控制器 <br/>
 * 控制器，负责登录模块业务逻辑功能的调用，与前端页面的跳转 *
 * 
 * @author 马小刚
 */
@SpringBootApplication
@Controller
public class DemoApplication {

    public static void main(String[] args) {
//        for(int i=0;i<10000;i++){
//            checkCode();
//        }
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * 长度为4
     */
    private static final int SIZE4 = 4;
    /**
     * 图片宽
     */
    private static final int WIDTH = 120;
    /**
     * 图片高
     */
    private static final int HEIGHT = 30;
    /**
     * 字体大小
     */
    private static final int FONT_SIZE = 26;
    /**
     * 常数20
     */
    private static final int NUMBER20 = 20;
    /**
     * 常数60
     */
    private static final int NUMBER60 = 60;
    /**
     * 常数30
     */
    private static final int NUMBER30 = 30;
    /**
     * 常数180
     */
    private static final int NUMBER180 = 180;

    /**
     * 验证码 TODO <功能简述> <br/>
     * TODO <功能详细描述>
     * 
     * @param request
     * @param response
     * @throws Exception 
     */
    @RequestMapping("/checkCode")
    public void checkCode(@RequestParam("code")String code,HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(code.length()!=SIZE4){
            throw new Exception("字符串长度不够 error1");
        }
        String words = "[a-zA-Z0-9]{4}";
        Pattern p = Pattern.compile(words);
        if(!p.matcher(code).find()){
            throw new Exception("字符串类型合适 error2" );
        }
        System.out.println("get Code"+code);
        int width = WIDTH;
        int height = HEIGHT;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRect(0, 0, width - 1, height - 1);
//        String words = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
//        StringBuffer sb = new StringBuffer();
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(new Font("隶书", Font.BOLD, FONT_SIZE));
        Random random = new Random();
        int x = NUMBER20;
        int y = NUMBER20;
        for (int i = 0; i < SIZE4; i++) {
            int jiaodu = random.nextInt(NUMBER60) - NUMBER30;
            double hudu = jiaodu * Math.PI / NUMBER180;
            graphics2D.rotate(hudu, x, y);
//            int index = random.nextInt(words.length());
//            char ch = words.charAt(index);
//            sb.append(ch);
            graphics2D.drawString("" + code.charAt(i), x, y);
            graphics2D.rotate(-hudu, x, y);
            x += NUMBER20;
        }
        request.getSession().setAttribute("sessCode", code.toString());
        graphics2D.setColor(Color.BLUE);
        int x1;
        int x2;
        int y1;
        int y2;
        for (int i = 0; i < SIZE4; i++) {
            x1 = random.nextInt(width);
            y1 = random.nextInt(height);
            x2 = random.nextInt(width);
            y2 = random.nextInt(height);
            graphics2D.drawLine(x1, y1, x2, y2);
        }
        try {
            ImageIO.write(image, "jpg", response.getOutputStream());
            System.out.println("get Code end"+code);
        } catch (IOException e) {
            throw new Exception("图像产生失败：error3");
        }
    }

    public static void checkCode() {
        int width = WIDTH;
        int height = HEIGHT;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRect(0, 0, width - 1, height - 1);
        String words = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuffer sb = new StringBuffer();
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(new Font("隶书", Font.BOLD, FONT_SIZE));
        Random random = new Random();
        int x = NUMBER20;
        int y = NUMBER20;
        for (int i = 0; i < SIZE4; i++) {
            int jiaodu = random.nextInt(NUMBER60) - NUMBER30;
            double hudu = jiaodu * Math.PI / NUMBER180;
            graphics2D.rotate(hudu, x, y);
            int index = random.nextInt(words.length());
            char ch = words.charAt(index);
            sb.append(ch);
            graphics2D.drawString("" + ch, x, y);
            graphics2D.rotate(-hudu, x, y);
            x += NUMBER20;
        }
        graphics2D.setColor(Color.BLUE);
        int x1;
        int x2;
        int y1;
        int y2;
        for (int i = 0; i < SIZE4; i++) {
            x1 = random.nextInt(width);
            y1 = random.nextInt(height);
            x2 = random.nextInt(width);
            y2 = random.nextInt(height);
            graphics2D.drawLine(x1, y1, x2, y2);
        }
        try {
            File f = new File("D:\\imgout\\" + sb.toString() + ".jpg");
            FileOutputStream of = new FileOutputStream(f);
            ImageIO.write(image, "jpg", of);
            
        } catch (Exception e) {
        }
    }

    /**
     * 验证码验证 TODO <功能简述> <br/>
     * TODO <功能详细描述>
     * 
     * @return 返回验证是否成功
     */
    @RequestMapping("/checkCodeDeal")
    public @ResponseBody boolean checkCodeDeal(String code, HttpServletRequest request) {
        String sessCode = (String) request.getSession().getAttribute("sessCode");
        if (sessCode != null && sessCode.length() == SIZE4) {
            if (sessCode.equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }
}
