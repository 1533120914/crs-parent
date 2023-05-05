package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.example.common.response.ResponseData;
import org.example.entity.query.CarParam;
import org.example.mapper.CarMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/car")
public class CarController {
    @Resource
    CarMapper carMapper;
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
}
