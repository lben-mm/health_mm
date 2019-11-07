package com.itheima.service;

import com.itheima.domain.Emp;

import java.util.List;

public interface Empservice {

    public List<Emp> findAll();

    public void saveOne(Emp emp);


    public Emp finaById(int id);


    public void updateEmp(Emp emp);


    public void deleteById(int id);
}
