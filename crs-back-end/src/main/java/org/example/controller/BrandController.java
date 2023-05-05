package org.example.controller;

import org.example.common.response.ResponseData;
import org.example.mapper.BrandMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Resource
    BrandMapper brandMapper;
    @GetMapping("/list")
    public ResponseData list() {
        return ResponseData.success(brandMapper.selectList(null));
    }
}
