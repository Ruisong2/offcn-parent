package com.offcn.project.service;

import com.offcn.dycommon.enums.ProjectStatusEnume;
import com.offcn.project.vo.req.ProjectRedisStorageVo;

public interface ProjectCreateService {
    public String initCreateProject(Integer memberId);

    //保存项目信息到数据库
    public void saveProjectInfo(ProjectStatusEnume auth, ProjectRedisStorageVo projectVo);
}
