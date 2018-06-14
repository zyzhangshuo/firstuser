package com.qfedu.ssh.controller;

import com.qfedu.ssh.bean.User;
import com.qfedu.ssh.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

import static com.qfedu.ssh.utils.PatternUtils.*;


@Controller
@RequestMapping("/user")
public class UserController {

    @Resource(name="userService")
    private UserService userService;

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/doreg", method = RequestMethod.POST)
    public String doreg(
            @RequestParam("username")
                    String username,
            @RequestParam("password")
                    String password,
            @RequestParam("email")
                    String email,
            @RequestParam("telephone")
                    String telephone,
            @RequestParam("gender")
                    String gender,
            @RequestParam("checkcode")
                    String checkcode,
            Map<String,Object> map,
            HttpServletRequest request
    ) {
        String msg="";
        //后端校验
        if(!Pattern.matches(uPattern, username)){
            msg="用户名格式非法";
            map.put("msg",msg);
            return "error";
        }
        if(!Pattern.matches(ePattern, email)){
            msg="邮箱格式格式非法";
            map.put("msg",msg);
            return "error";
        }
        if(!Pattern.matches(mPattern, telephone)){
            msg="手机号格式非法";
            map.put("msg",msg);
            return "error";
        }
        if(!Pattern.matches(pPattern, password)){
            msg="密码格式非法";
            map.put("msg",msg);
            return "error";
        }
        if(!("男".equals(gender)||"女".equals(gender))){
            msg="性别不对";
            map.put("msg",msg);
            return "error";
        }
        if(!Objects.equals(checkcode,request.getSession().getAttribute("word"))){
            msg="不要耍小聪明，验证码必须老老实实写正确";
            map.put("msg",msg);
            return "error";
        }


        User user = new User(username, password, gender, email, telephone);
        userService.addUser(user);

        return "login";
    }

    ///图片验证码///
    //保存所有的成语
    private List<String> words=new ArrayList<String>();

    private void initWords(HttpServletRequest request) {
        if(words.size()==0){
            String path=request.getSession().getServletContext().getRealPath("/WEB-INF/new_words.txt");
            BufferedReader reader=null;
            try {
                reader=new BufferedReader(new InputStreamReader(new FileInputStream(path),"utf-8"));
                String line;
                for(;(line=reader.readLine())!=null;){
                    words.add(line);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(reader!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
    //前端还要请求username是否已经存在
    @RequestMapping("/findusername")
    public void findUsername(HttpServletRequest request,
                             HttpServletResponse response,@RequestParam("username") String username) throws IOException {
        boolean bool=userService.findUsername(username);
        response.getWriter().write(bool+"");
    }
    //前端还要请求email是否已经存在
    @RequestMapping("/findemail")
    public void findEmail(HttpServletRequest request,
                          HttpServletResponse response,@RequestParam("email") String email) throws IOException {
        boolean bool=userService.findEmail(email);
        response.getWriter().write(bool+"");
    }
    //前端还要请求username是否已经存在
    @RequestMapping("/findtelephone")
    public void findTelephone(HttpServletRequest request,
                              HttpServletResponse response,@RequestParam("telephone") String telephone) throws IOException {
        boolean bool=userService.findTelephone(telephone);
        response.getWriter().write(bool+"");
    }



    //前端ajax请求验证码的内容
    @RequestMapping("/textcheckcode")
    public void textcheckcode(HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        String code= (String) request.getSession().getAttribute("word");
        response.getWriter().write(code);

    }

    @RequestMapping("/imagecheckcode")
    public void imagecheckcode(HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
        initWords(request);
        //设置http响应内容的mime类型
        response.setContentType("image/jpeg");
        //随机对象
        Random random=new Random();
        int index=random.nextInt(words.size());
        //随机的那一个成语
        String word=words.get(index);
        request.getSession().setAttribute("word",word);
        //定义验证码
        int width=120;
        int height=30;


        //用java生成一个图片
        BufferedImage bufferedImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        //获取图片的绘制对象
        Graphics graphics=bufferedImage.getGraphics();
        //验证码分为三个部分
        //底色、验证码的文字颜色、上方的噪点颜色

        //首先画底色
        //底色比较浅
        Color color=getRandomColor(200,249);
        //设置底色
        graphics.setColor(color);
        //fill是填充绘制
        graphics.fillRect(0,0,width,height);

        //设置白色
        graphics.setColor(Color.WHITE);
        //描边绘制
        graphics.drawRect(0,0,width,height);

        //绘制随机的验证码
        Graphics2D graphics2D= (Graphics2D) graphics;

        //设置即将绘制出来的文字的样式
        graphics2D.setFont(new Font("宋体",Font.BOLD,18));
        int x=10;
        for (int i = 0; i < word.length(); i++) {
            //设置颜色比较深
            graphics2D.setColor(getRandomColor(20,110));
            //设置一个旋转角度
            int angle=random.nextInt(60)-30;
            //转化成弧度制
            double theta=Math.PI/180*angle;

            //获取到某个单个字
            char c=word.charAt(i);
            //把字画到图片上
            //graphics2D.rotate();
            graphics2D.rotate(theta,x,20);
            graphics2D.drawString(c+"",x,20);
            graphics2D.rotate(-theta,x,20);
            x+=30;
        }


        //噪点
        graphics.setColor(getRandomColor(160,200));
        int x1;
        int x2;
        int y1;
        int y2;
        for (int i = 0; i < 30; i++) {
            //每一个线的长度要有限制
            //每一条线的长度不会超过sqrt(288)
            x1=random.nextInt(width);
            y1=random.nextInt(height);
            x2=random.nextInt(12)+x1;
            y2=random.nextInt(12)+y1;
            graphics.drawLine(x1,y1,x2,y2);
        }
        //释放资源
        graphics.dispose();
        //输出到网页上
        ImageIO.write(bufferedImage,"jpg",response.getOutputStream());
    }
    private Color getRandomColor(int fc,int ec){
        Random random=new Random();
        int r=fc+random.nextInt(ec-fc);
        int g=fc+random.nextInt(ec-fc);
        int b=fc+random.nextInt(ec-fc);
        return new Color(r,g,b);
    }


    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/dologin")
    public void dologin(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam("logininfo")
                                String loginInfo,
                        @RequestParam("password")
                                String password
    ) throws IOException {
        User user=userService.findUser(loginInfo,password);
        if(user!=null){
            request.getSession().setAttribute("user",user);
            response.getWriter().write("success");
        }else{
            request.getSession().setAttribute("user",null);
            response.getWriter().write("error");
        }
    }

    @RequestMapping("/edituser")
    public String edituser(){
        return "edituser";
    }

    @RequestMapping("/doedit")
    public String doedit(
            @RequestParam("password")
                    String password,
            @RequestParam("gender")
                    String gender,
            @RequestParam("icon")
                    String icon,
            HttpServletRequest request

    ){
        //更新当前登录的那个用户
        User user=(User)request.getSession().getAttribute("user");
        user.setGender(gender);
        user.setIcon(icon);
        user.setPassword(password);
        userService.editUser(user);

        return "showproduct";
    }


    //文件上传//
    //显示上传文件页面的方法
    //C:\Tomcat 8.5\ideaImages
    public static final String IMAGE_PATH="C:"+File.separator+"Tomcat 8.5"+File.separator+"ideaImages";
    //测试文件上传
    @RequestMapping("/upload")
    public String upload(){
        return "upload";
    }



    //接收文件的方法中
    @ResponseBody
    @RequestMapping("/fileupload")
    public String fileupload(
            HttpServletRequest request,
            @RequestParam("icon") MultipartFile file) throws Exception {
        System.out.println("接收到文件了"+file.toString());
        if(file!=null&&file.getOriginalFilename()!=null&&file.getOriginalFilename().length()>0){
            //保存文件的逻辑
            String origName=file.getOriginalFilename();

            //取后缀名
            String extendsName=origName.substring(origName.lastIndexOf("."));
            if(".png".equalsIgnoreCase(extendsName)||
                    ".bmp".equalsIgnoreCase(extendsName)||
                    ".jpg".equalsIgnoreCase(extendsName)||
                    ".jpeg".equalsIgnoreCase(extendsName)){
                //文件合法
                //防止文件重名
                //username+time
                //uuid

                String newFilename=UUID.randomUUID().toString()+extendsName;

                //创建一个文件
                File uploadFile=new File(IMAGE_PATH+File.separator+newFilename);
                //把文件保存进去
                file.transferTo(uploadFile);

                //生成水印文件
                //saveWaterMarkFile(request,uploadFile,newFilename);

                //返回文件名
                return "/images/"+newFilename;

            }else{
                //文件类型不正确
                return "error";
            }
        }


        return "error";
    }

    //保存水印文件的方法
    private String saveWaterMarkFile(HttpServletRequest request,File file,String fileName) throws Exception {
        File waterMark=new File(request.getSession().getServletContext().getRealPath("WEB-INF"+File.separator+"watermark.png"));
        BufferedImage bufferedImageWM=ImageIO.read(new FileInputStream(waterMark));
        BufferedImage bufferedImage=ImageIO.read(new FileInputStream(file));

        Graphics graphics=bufferedImage.getGraphics();
        graphics.drawImage(bufferedImageWM,
                bufferedImage.getWidth()/2-bufferedImageWM.getWidth()/2,
                bufferedImage.getHeight()-bufferedImageWM.getHeight(),
                null);

        File outFile=new File(IMAGE_PATH+File.separator+fileName+"_wm");
        if(!outFile.exists()){
            outFile.createNewFile();
        }
        ImageIO.write(bufferedImage,"jpg",outFile);
        return outFile.getAbsolutePath();
    }

}
