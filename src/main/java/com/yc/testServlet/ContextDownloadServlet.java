package com.yc.testServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/download")
public class ContextDownloadServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置相应头信息
        String filename=req.getParameter("filename");//接收文件名
        resp.setHeader("Content-Disposition","attachment;filename="+filename);
        //获取绝对路径
        String realpath=this.getServletContext().getRealPath("/downloads/" +filename);
        System.out.println(realpath);
        //发送文件
        InputStream fin=this.getServletContext().getResourceAsStream("/downloads/" +filename);//成功！
//        InputStream fin=new FileInputStream("/downloads/"+filename);//不起作用！
        OutputStream out=resp.getOutputStream();
        byte[] buff=new byte[1024];
        int len=-1;
        while ((len=fin.read(buff))!=-1){
            out.write(buff,0,len);
        }
        fin.close();
        out.close();
    }
}
