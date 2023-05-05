package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.example.common.response.ResponseData;
import org.example.common.util.UUIDUtil;
import org.example.entity.Car;
import org.example.entity.CarPicture;
import org.example.entity.query.CarParam;
import org.example.mapper.CarMapper;
import org.example.mapper.CarPictureMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/car")
public class CarController {
    @Resource
    CarMapper carMapper;

    @Resource
    CarPictureMapper carPictureMapper;

    @Value("${car.image.save.path}")
    private String carImageSavePath;

    @GetMapping("/query")
    public ResponseData query (CarParam param) {
        Wrapper qw = new QueryWrapper()
                .select("id", "car_name", "car_type", "seat_num", "acce_capa", "oil_wear", "daily_rent","state")
                .like(StringUtils.isNotEmpty(param.getCarName()), "car_name", param.getCarName())
                .eq(param.getBrandId()!= null, "brand_id", param.getBrandId())
                .ge(param.getLowDailyRent()!=null && param.getHighDailyRent()==null, "daily_rent", param.getLowDailyRent())
                .le(param.getLowDailyRent()==null && param.getHighDailyRent()!=null, "daily_rent", param.getHighDailyRent())
                .between(param.getLowDailyRent()!=null && param.getHighDailyRent()!= null
                        , "daily_rent", param.getLowDailyRent(),param.getHighDailyRent());
        carMapper.selectPage(param, qw);
        return ResponseData.success(param);
    }
    @GetMapping("/exists/{carName}")
    public ResponseData exists(@PathVariable("carName") String carName) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("car_name", carName);
        return ResponseData.success(carMapper.exists(qw)? "已存在": "不存在");
    }

    @PostMapping("/save")
    public ResponseData save(Car car) {
        // 前端传过来的car里面没有id
        carMapper.insert(car); // 插入成功后 car里面有id了
        // 再把car返回给前端, 前端就可以拿到car的id了
        return ResponseData.success(car);
    }

    @GetMapping("/del/{id}")
    public ResponseData del(@PathVariable("id") Integer id) {
        carMapper.deleteById(id);
        return ResponseData.success();
    }

    @GetMapping("/picture/del/{id}")
    public ResponseData deleteImage(@PathVariable("id") Integer id) {
        carPictureMapper.deleteById(id);
        return ResponseData.success();
    }

    @PostMapping("/picture/upload")
    public ResponseData saveImage(Integer id, MultipartFile file) throws IOException {
        File folder = new File(carImageSavePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = UUIDUtil.uniqueFileName(file.getOriginalFilename());
        File saveFile = new File(carImageSavePath, fileName);
        file.transferTo(saveFile);
        CarPicture carPicture = new CarPicture();
        carPicture.setCarId(id);
        carPicture.setImageUrl(fileName);
        carPictureMapper.insert(carPicture);
        return ResponseData.success(carPicture);
    }
    @GetMapping("/picture/dowload")
    public void download(String fileName, HttpServletResponse response) throws IOException {
        // 根据文件名下载文件
        OutputStream os = response.getOutputStream();
        File file = new File(carImageSavePath, fileName);
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
