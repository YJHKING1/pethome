package org.yjhking.pethome.basic.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.FastDfsUtils;

import java.io.IOException;

/**
 * 文件上传
 *
 * @author YJH
 */
@RestController
@RequestMapping("/fastDfs")
public class FastDfsController {
    @PostMapping
    public AjaxResult upload(@RequestPart(required = true, value = "file") MultipartFile file) {
        AjaxResult result = AjaxResult.me();
        try {
            // 拿到文件名
            String originalFilename = file.getOriginalFilename();
            // 拿到文件扩展名
            String substring = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            // 拿到文件上传路径
            String upload = FastDfsUtils.upload(file.getBytes(), substring);
            System.out.println("图片上传路径：" + upload);
            // 返回上传路径
            result.setResultObj(upload);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("上传失败");
            return result;
        }
    }
    
    @DeleteMapping
    public AjaxResult del(@RequestParam(required = true, value = "path") String path) {
        AjaxResult result = AjaxResult.me();
        try {
            // 去除第一个斜杠
            String substring = path.substring(1);
            // 拿到组名
            String substring1 = substring.substring(0, substring.indexOf("/"));
            // 拿到文件名
            String substring2 = substring.substring(substring.indexOf("/") + 1);
            // 删除文件
            FastDfsUtils.delete(substring1, substring2);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("删除失败");
            return result;
        }
    }
}
