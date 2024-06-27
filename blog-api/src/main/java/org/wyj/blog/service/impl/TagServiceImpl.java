package org.wyj.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wyj.blog.mapper.TagMapper;
import org.wyj.blog.entity.dos.Tag;
import org.wyj.blog.service.ITagService;
import org.wyj.blog.entity.vo.TagVO;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements ITagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> findTagsByArticleId(Long articleId) {
        return tagMapper.findTagsByArticleId(articleId);
    }

    @Override
    public List<TagVO> list() {
        List<Tag> tagList = tagMapper.selectList(new LambdaQueryWrapper<>());

        List<TagVO> tagVOList = new ArrayList<>();
        for (Tag tag : tagList) {
            TagVO tagVO = new TagVO();
            tagVOList.add(tagVO);
            BeanUtils.copyProperties(tag, tagVO);
        }
        return tagVOList;
    }

    @Override
    public List<Tag> detailList() {
        return tagMapper.selectList(new LambdaQueryWrapper<>());
    }
}
