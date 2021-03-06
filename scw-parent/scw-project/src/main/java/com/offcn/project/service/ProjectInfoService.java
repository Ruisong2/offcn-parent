package com.offcn.project.service;

import com.offcn.project.po.*;

import java.util.List;

public interface ProjectInfoService {
    //根据项目id查询回报列表
    List<TReturn> getReturnList(Integer projectId);

    List<TProject> findAllProject();
    List<TProjectImages> getProjectImages(Integer id);
    /* 获取项目信息
 * @param projectId
 * @return
         */
    TProject findProjectInfo(Integer projectId);
    /**
     * 获得项目标签
     * @return
     */
    List<TTag> findAllTag();
    /**
     * 获取项目分类
     * @return
     */
    List<TType> findAllType();
    /**
     * 获取回报信息
     * @param returnId
     * @return
     */
    TReturn findReturnInfo (Integer returnId);
}
