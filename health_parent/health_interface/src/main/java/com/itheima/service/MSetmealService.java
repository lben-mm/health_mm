package com.itheima.service;

import com.itheima.domain.Setmeal;

import java.util.List;

public interface MSetmealService {

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    Setmeal findAllById_ccs(Integer id);


}
