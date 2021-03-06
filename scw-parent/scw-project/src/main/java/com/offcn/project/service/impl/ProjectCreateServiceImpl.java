package com.offcn.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.offcn.dycommon.enums.ProjectStatusEnume;
import com.offcn.project.contants.ProjectContent;
import com.offcn.project.enums.ProjectImageTypeEnume;
import com.offcn.project.mapper.*;
import com.offcn.project.po.*;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.vo.req.ProjectRedisStorageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
public class ProjectCreateServiceImpl implements ProjectCreateService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private TProjectMapper projectMapper;
    @Autowired
    private TProjectImagesMapper projectImagesMapper;
    @Autowired
    private TProjectTagMapper projectTagMapper;
    @Autowired
    private TProjectTypeMapper projectTypeMapper;
    @Autowired
    private TReturnMapper returnMapper;
    @Override
    public String initCreateProject(Integer memberId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        ProjectRedisStorageVo projectRedisStorageVo = new ProjectRedisStorageVo();
        //存令牌
        //存memberId
        projectRedisStorageVo.setMemberid(memberId);
        projectRedisStorageVo.setProjectToken(token);
        String jsonString = JSON.toJSONString(projectRedisStorageVo);
        stringRedisTemplate.opsForValue().set(ProjectContent.TEMP_PROJECT_PREFIX+token,jsonString);
         return token;
    }

    @Override
    public void saveProjectInfo(ProjectStatusEnume auth, ProjectRedisStorageVo projectVo) {
        //创建项目,并赋值
        TProject project = new TProject();
        BeanUtils.copyProperties(projectVo,project);
        //设置时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        project.setCreatedate(format);
        //设置项目状态
        project.setStatus(auth.getCode()+"");
        //保存到数据库
        projectMapper.insertSelective(project);
        //拿到项目的id
        Integer projectId = project.getId();
        //保存图片--头图
        String headerImage = projectVo.getHeaderImage();
        TProjectImages tProjectImages = new TProjectImages(null, projectId, headerImage, ProjectImageTypeEnume.HEADER.getCode());
        projectImagesMapper.insertSelective(tProjectImages);
        //保存图片--详图
        List<String> detailsImage = projectVo.getDetailsImage();
        if (CollectionUtils.isEmpty(detailsImage)){
            for (String s : detailsImage) {
                TProjectImages tProjectImages1 = new TProjectImages(null, projectId, s, ProjectImageTypeEnume.DETAILS.getCode());
                projectImagesMapper.insertSelective(tProjectImages1);
            }
        }
        //4.标签
        List<Integer> tagids = projectVo.getTagids();
        if (CollectionUtils.isEmpty(detailsImage)){
            for (Integer tagid : tagids) {
                TProjectTag tProjectTag = new TProjectTag(null, projectId, tagid);
                projectTagMapper.insertSelective(tProjectTag);
            }
        }
        //保存分类
        List<Integer> typeids = projectVo.getTypeids();
        if (CollectionUtils.isEmpty(typeids)){
            for (Integer typeid : typeids) {
                TProjectType tProjectType = new TProjectType(null, projectId, typeid);
                projectTypeMapper.insertSelective(tProjectType);
            }
        }
        //回报信息
        List<TReturn> projectReturns = projectVo.getProjectReturns();
        if (CollectionUtils.isEmpty(projectReturns)){
            for (TReturn tReturn : projectReturns) {
                tReturn.setProjectid(projectId);
                returnMapper.insertSelective(tReturn);
            }
        }
        //7.清空redis
        stringRedisTemplate.delete(ProjectContent.TEMP_PROJECT_PREFIX+projectVo.getProjectToken());
    }




























}
