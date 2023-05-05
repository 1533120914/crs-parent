package org.example.controller;

import org.example.common.response.ResponseData;
import org.example.common.util.UUIDUtil;
import org.example.entity.Account;
import org.example.entity.Admin;
import org.example.entity.Salesman;
import org.example.entity.User;
import org.example.mapper.AdminMapper;
import org.example.mapper.SalesmanMapper;
import org.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${user.avatar.save.path}")
    private String userAvatarSavePath;

    @Resource
    UserMapper userMapper;
    @Resource
    AdminMapper adminMapper;
    @Resource
    SalesmanMapper salesmanMapper;

    @PostMapping("/upload/avatar")
    public ResponseData avatar(Account account, MultipartFile file) throws IOException {
        // 生成唯一文件名
        String fileName = UUIDUtil.uniqueFileName(file.getOriginalFilename());
        // 创建磁盘文件对象
        File saveFile = new File(userAvatarSavePath,fileName);
        // 文件落地
        file.transferTo(saveFile);
        // 判断更新哪一张表
        switch (account.getIdentity()) {
            case 1:
                Admin admin = new Admin();
                admin.setId(account.getId());
                admin.setAvatar(fileName);
                adminMapper.updateById(admin); // 更新管理员的头像
                break;
            case 2:
                Salesman salesman = new Salesman();
                salesman.setId(account.getId());
                salesman.setAvatar(fileName);
                salesmanMapper.updateById(salesman);  // 更新业务员的头像
                break;
            case 3:
                User user = new User();
                user.setId(account.getId());
                user.setAvatar(fileName);
                userMapper.updateById(user);  // 更新用户的头像
                break;
        }
        return ResponseData.success(fileName); // 返回文件名给前端
    }
    @GetMapping("/download/avatar")
    public void download(String fileName, HttpServletResponse response) throws IOException {
        // 根据文件名下载文件
        OutputStream os = response.getOutputStream();
        File file = new File(userAvatarSavePath, fileName);
        InputStream is = new FileInputStream(file);
        byte[] data = new byte[1024 * 1024];
        int len = -1;
        while ((len = is.read(data)) != -1) {
            os.write(data, 0, len);
        }
        os.flush();
        is.close();
        os.close();
    }

}
