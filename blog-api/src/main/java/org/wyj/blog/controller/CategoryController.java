package org.wyj.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wyj.blog.entity.dos.Category;
import org.wyj.blog.entity.vo.CategoryVO;
import org.wyj.blog.entity.vo.ResultVO;
import org.wyj.blog.service.ICategoryService;

import java.util.List;

/**
 * @author 武耀君
 * @date 2024/3/16
 *
 * 文章分类
 */
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private ICategoryService categoryService;

    // 分类列表
    @GetMapping("/list")
    public String list() {
        List<CategoryVO> categoryVOList;
        try {
            categoryVOList = categoryService.list();
        } catch (Exception e) {
            LOG.error("Getting category list failed.", e);
            return ResultVO.fail("读取分类列表出错.").toJson();
        }
        return ResultVO.success(categoryVOList).toJson();
    }

    // 分类详情列表
    @GetMapping("/detailList")
    public String detail() {
        List<Category> categoryList;
        try {
            categoryList = categoryService.detail();
        } catch (Exception e) {
            LOG.error("Getting category detail failed.", e);
            return ResultVO.fail("读取分类列表出错.").toJson();
        }
        return ResultVO.success(categoryList).toJson();
    }
}
