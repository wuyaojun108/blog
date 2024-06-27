package org.wyj.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wyj.blog.mapper.CategoryMapper;
import org.wyj.blog.entity.dos.Category;
import org.wyj.blog.entity.vo.CategoryVO;
import org.wyj.blog.service.ICategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryVO> list() {
        List<Category> categoryList = categoryMapper.selectList(new LambdaQueryWrapper<>());

        ArrayList<CategoryVO> categoryVOList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryVO categoryVO = new CategoryVO();
            categoryVOList.add(categoryVO);
            BeanUtils.copyProperties(category, categoryVO);
            categoryVO.setId(category.getId().toString());
        }
        return categoryVOList;
    }

    @Override
    public List<Category> detail() {
        return categoryMapper.selectList(new LambdaQueryWrapper<>());
    }
}
