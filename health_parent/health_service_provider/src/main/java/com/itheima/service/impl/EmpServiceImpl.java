package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.EmpDao;
import com.itheima.service.Empservice;
import com.itheima.domain.Emp;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class EmpServiceImpl implements Empservice {

    @Autowired
    private EmpDao empDao;
    @Override
    public List<Emp> findAll() {
        return empDao.findAll();
    }

    @Override
    public void saveOne(Emp emp) {
            empDao.saveOne(emp);
    }

    @Override
    public Emp finaById(int id) {
        return empDao.finaById(id);
    }

    @Override
    public void updateEmp(Emp emp) {
        empDao.updateEmp(emp);
    }

    @Override
    public void deleteById(int id) {
        empDao.deleteById(id);
    }
}
