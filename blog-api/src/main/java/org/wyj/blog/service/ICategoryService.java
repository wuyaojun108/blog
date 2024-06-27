package org.wyj.blog.service;

import org.wyj.blog.entity.dos.Category;
import org.wyj.blog.entity.vo.CategoryVO;

import java.util.List;

public interface ICategoryService {

    List<CategoryVO> list();

    List<Category> detail();
}
