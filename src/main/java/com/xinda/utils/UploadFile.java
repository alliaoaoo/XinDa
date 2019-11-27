package com.xinda.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UploadFile {
    //路径形式/uploads/用户id/服务名称/年份/月份/服务名称+日期+数字.jpeg
    //上传文件
    public static String  uploadFile(HttpServletRequest request, MultipartFile uploadFile,String user,String servicename) throws IOException {
//        规定格式时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String res = sdf.format(new Date());
        //上传文件夹位置
        String rootPath =request.getSession().getServletContext().getRealPath("/")+"static/uploads/"+user+"/";
//        System.out.println("root:"+rootPath);
        //原始名称
        String originalFilename = uploadFile.getOriginalFilename();
        //新的文件名称
        String newFileName = servicename+res+originalFilename.substring(originalFilename.lastIndexOf("."));
        //当前时间
        Calendar date = Calendar.getInstance();
        //创建年月文件夹
        File dateDirs = new File(servicename+"/"+date.get(Calendar.YEAR)
                + File.separator + (date.get(Calendar.MONTH)+1));
        //新文件
        File newFile = new File(rootPath+File.separator+dateDirs+File.separator+newFileName);
        //判断目标文件所在的目录是否存在
        if(!newFile.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            newFile.getParentFile().mkdirs();
        }
        //将内存中的数据写入磁盘
        uploadFile.transferTo(newFile);
        //完整的url
        String fileUrl =  date.get(Calendar.YEAR)+ "/"+(date.get(Calendar.MONTH)+1)+ "/"+ newFileName;
        System.out.println("##################################################");
        System.out.println("uploads路径"+"/uploads/"+user+"/"+servicename+"/"+fileUrl);
        return "/uploads/"+user+"/"+servicename+"/"+fileUrl;
    }

    public static void delUploadFile(HttpServletRequest request,String pathFile){

        String rootPath = request.getSession().getServletContext().getRealPath("/")+"static";
        String filePath = rootPath+pathFile;
        System.out.println("##################################################");
        System.out.println(filePath);
        try {
            File file = new File(filePath);
            deleteFile(file);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private static void deleteFile(File file) {
        // 判断传递进来的是文件还是文件夹,如果是文件,直接删除,如果是文件夹,则判断文件夹里面有没有东西
        if (file.isDirectory()) {
            // 如果是目录,就删除目录下所有的文件和文件夹
            File[] files = file.listFiles();
            // 遍历目录下的文件和文件夹
            for (File f : files) {
                // 如果是文件,就删除
                if (f.isFile()) {
                    // 删除文件
                    f.delete();
                } else if (file.isDirectory()) {
                    // 如果是文件夹,就递归调用文件夹的方法
                    deleteFile(f);
                }
            }
            // 删除文件夹自己,如果它低下是空的,就会被删除
            file.delete();
        }
        // 如果是文件,就直接删除自己
        file.delete();
    }
}
