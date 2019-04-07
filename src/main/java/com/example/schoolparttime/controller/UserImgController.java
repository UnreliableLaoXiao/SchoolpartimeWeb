package com.example.schoolparttime.controller;

import com.example.schoolparttime.entity.UserImg;
import com.example.schoolparttime.entity.base.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;

@Controller
public class UserImgController {

    private String path = "D:/uploadPic/";

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    /**
//     * 测试
//     * @param args
//     * @throws IOException
//     */
//    public static void main(String[] args) throws IOException {
//        listFilesTest();
//    }
//
//    public static void listFilesTest() {
//        File file = new File("D:/uploadPic/");
//        for (File f : file.listFiles()) {
//            System.out.println("fileName : " + f.getName());
//        }
//    }


    @ResponseBody
    @RequestMapping("/geturl")
    public ResultModel getImgUrl(long id,int type) {
        System.out.println("id = " + id + " , type = " + type);
        ArrayList<UserImg> imgArrayList = (ArrayList<UserImg>) jdbcTemplate.query("select * from t_user_img where userid = ? and type = ?",new Object[]{id,type},
                new BeanPropertyRowMapper(UserImg.class));
        if (imgArrayList.size() > 0){
            UserImg userImg = imgArrayList.get(0);
            return new ResultModel<String>("得到url", userImg.getUrl(), "url", 200);
        }
        return new ResultModel<String>("未查到url", null, "url", 300);

    }

    @RequestMapping("/imgs/upload")
    @ResponseBody
    public ResultModel uploadImg(@RequestParam("file") MultipartFile multipartFile,long id,int type)  {
        System.out.println("id = "+ id+ " type = " + type);
        if (multipartFile.isEmpty() || "".equals(multipartFile.getOriginalFilename())) {
            System.out.println("上传文件异常--->名字异常");
            return new ResultModel<String>("上传文件异常", null, "url", 400);
        }
        String contentType = multipartFile.getContentType();
        if (!contentType.contains("")) {
            System.out.println("上传文件异常--->格式异常");
            return new ResultModel<String>("上传文件异常", null, "url", 400);
        }
        String root_fileName = multipartFile.getOriginalFilename();
        System.out.println("上传图片:name=" + root_fileName + " type =  " + contentType);
        //处理图片
        //获取路径
        System.out.println("图片保存路径="+path);
        String file_url = null;
        try {
            file_url = saveImg(multipartFile, path);
//            int size = jdbcTemplate.query("select * from into t_user_img where userid = ? and type = ?",
//                    new Object[]{id, type}, new BeanPropertyRowMapper(UserImg.class)).size();
            int update = jdbcTemplate.update("insert into t_user_img values (0,?,?,?)", id, type, file_url);
            System.out.println("更新文件服务器--->新增");

            if (type == 2){
                int update1 = jdbcTemplate.update("update t_user set type = ? where id = ?", 4, id);
                System.out.println("更新用户身份状态--->新增");
            } else if(type == 1)
            {
                System.out.println("更新用户头像信息--->新增");
            }
            return new ResultModel<String>("上传文件成功", file_url, "url", 200);
        } catch (IOException e) {
            System.out.println("图片上传失败");
        }
        return new ResultModel<String>("上传文件失败", null, "url", 300);
    }

    /**
     * 保存文件，直接以multipartFile形式
     * @param multipartFile
     * @param path 文件保存绝对路径
     * @return 返回文件名
     * @throws IOException
     */
    public static String saveImg(MultipartFile multipartFile,String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
        String fileName = System.currentTimeMillis() + ".png";
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + File.separator + fileName));
        byte[] bs = new byte[1024];
        int len;
        while ((len = fileInputStream.read(bs)) != -1) {
            bos.write(bs, 0, len);
        }
        bos.flush();
        bos.close();
        return fileName;
    }

}
