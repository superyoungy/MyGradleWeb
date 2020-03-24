package com.yc.testServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 功能：采用文件的方式记录用户签到的信息
 */
@WebServlet(urlPatterns = "/StudentLoginServlet" )
public class StudentLoginServlet extends HttpServlet {//直接继承Servlet类的话，this.getServletConfig.getServletContext.getRealPath()似乎有点问题
    PrintWriter fout=null;//全局变量，供整个对象使用
    ArrayList<String> idCheck=new ArrayList<>();//记录历史签到id
    ArrayList<String> ipCheck=new ArrayList<>();//记录历史签到ip
    @Override
    public void init() throws ServletException {
        /*
        除了重启tomcat还要记得save all
         1、试验发现，用相对路径（String path="/login.txt"）可以找到文件，但是写不进去内容;
            用绝对路径String path=this.getServletContext().getRealPath("/login.txt")，可以写入，要注意内容是记录在部署目录中
         2、String path="src/login.txt;报错：找不到文件（纯java项目可以找到）
         3、用绝对路径时，部署路径out/artifacts/testServlet_war_exploded/WEB-INF/classes/login.txt会与之同步
         4、tomcat重启时会重新部署目录，因此之前的记录信息在tomcat重启后会不复存在
         */
        String path="/login.txt";
        File file=new File(this.getServletContext().getRealPath(path));//部署目录中
        try {
            fout=new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决乱码问题
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        //获取表单数据
        String stuId=req.getParameter("stuId");
        String stuName=req.getParameter("stuName");
        String stuAge=req.getParameter("stuAge");
        //获取客户端的IP地址、签到时间
        String IP=req.getRemoteAddr();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String loginTime=format.format(new Date());
        //将登陆信息记录到日志文件中
        int index1=0;//判定id是否重复，0代表不重复，1代表重复
        int index2=0;//判定ip是否重复，0代表不重复，1代表重复
        if(fout!=null){
            synchronized (this){
                if(!idCheck.contains(stuId)&&!ipCheck.contains(IP)){
                    idCheck.add(stuId);
                    ipCheck.add(IP);
                    fout.println("学号为："+stuId+"，姓名是："+stuName+"，年龄是："+stuAge+"，IP地址为："+IP+"，签到时间为："+loginTime);
                    fout.flush();
                }else if(idCheck.contains((stuId))){
                    index1=1;
                }else if(ipCheck.contains(IP)){
                    index2=1;
                }
            }
        }
        PrintWriter out=resp.getWriter();
        if(index1==0&&index2==0){
            out.println("签到成功！");
        }else if(index1==1){
            out.println("请勿重复签到！");
        }else{
            out.println("同一台机器只能签到一名同学！");
        }
    }

    @Override
    public void destroy() {
        if(fout!=null){
            fout.close();
        }
    }
}
