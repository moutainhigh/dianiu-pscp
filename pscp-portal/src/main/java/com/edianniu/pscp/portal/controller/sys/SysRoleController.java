package com.edianniu.pscp.portal.controller.sys;

import com.edianniu.pscp.portal.controller.AbstractController;
import com.edianniu.pscp.portal.entity.SysMenuEntity;
import com.edianniu.pscp.portal.entity.SysRoleEntity;
import com.edianniu.pscp.portal.service.SysMenuService;
import com.edianniu.pscp.portal.service.SysRoleMenuService;
import com.edianniu.pscp.portal.service.SysRoleService;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员角色管理
 * 会员管理自己的角色
 *
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月12日 下午17:21:33
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 角色列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public R list(String name, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);
        Long companyId = 0L;
        if (this.getUser().getCompanyId() != null) {
            companyId = this.getUser().getCompanyId();
        }
        map.put("companyId", companyId);
        //查询列表数据
        if (companyId > 0) {
            List<SysRoleEntity> list = sysRoleService.queryList(map);
            int total = sysRoleService.queryTotal(map);

            PageUtils pageUtil = new PageUtils(list, total, limit, page);

            return R.ok().put("page", pageUtil);
        } else {
            return R.ok();
        }

    }

    /**
     * 角色列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public R select() {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", this.getUser().getCompanyId());
        //查询列表数据
        List<SysRoleEntity> list = sysRoleService.queryList(map);

        return R.ok().put("list", list);
    }

    /**
     * 角色信息
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") Long roleId) {
        SysRoleEntity role = sysRoleService.queryObject(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        return R.ok().put("role", role);
    }

    /**
     * 保存角色
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public R save(@RequestBody SysRoleEntity role) {
        if (StringUtils.isBlank(role.getName())) {
            return R.error("角色名称不能为空");
        }
        role.setCompanyId(getUser().getCompanyId());
        addCommonMenuIdList(role);
        sysRoleService.save(role);

        return R.ok();
    }

    /**
     * 修改角色
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public R update(@RequestBody SysRoleEntity role) {
        if (StringUtils.isBlank(role.getName())) {
            return R.error("角色名称不能为空");
        }
        if (!sysRoleService.isExist(role.getId(), this.getUser().getCompanyId())) {
            return R.error("抱歉，您非法操作");
        }
        role.setCompanyId(getUser().getCompanyId());
        addCommonMenuIdList(role);
        sysRoleService.update(role);

        return R.ok();
    }

    /**
     * 设置默认菜单权限
     *
     * @param role
     */
    private void addCommonMenuIdList(SysRoleEntity role) {
        List<Long> getMenuIdList = role.getMenuIdList();
        if (CollectionUtils.isEmpty(getMenuIdList)) {
            getMenuIdList = new ArrayList<>();
        }

        if (StringUtils.isNotBlank(defaultSysMenu)) {
            String[] strArray = defaultSysMenu.split(",");
            if (strArray.length < 1) {
                return;
            }
            for (String menuId : strArray) {
                Long parentMenuId = Long.valueOf(menuId);
                List<SysMenuEntity> sysMenuEntityList = sysMenuService.queryListParentId(parentMenuId, null);
                if (CollectionUtils.isEmpty(sysMenuEntityList)) {
                    continue;
                }
                for (SysMenuEntity menuEntity : sysMenuEntityList) {
                    if (!getMenuIdList.contains(menuEntity.getId())) {
                        getMenuIdList.add(menuEntity.getId());
                    }
                }

                if (!getMenuIdList.contains(parentMenuId)) {
                    getMenuIdList.add(parentMenuId);
                }
            }
        }

        if (!CollectionUtils.isEmpty(getMenuIdList)) {
            role.setMenuIdList(getMenuIdList);
        }
    }

    /**
     * 删除角色
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public R delete(@RequestBody Long[] roleIds) {
        if (roleIds == null) {
            return R.error("请选中进行操作");
        }
        for (Long id : roleIds) {
            if (!sysRoleService.isExist(id, this.getUser().getCompanyId())) {
                return R.error("抱歉，您非法操作");
            }
        }
        sysRoleService.deleteBatch(roleIds);

        return R.ok();
    }

    /**
     * 系统默认权限
     */
    private String defaultSysMenu;

    @Value(value = "${sys.menu.default}")
    public void setDefaultSysMenu(String defaultSysMenu) {
        this.defaultSysMenu = defaultSysMenu;
    }
}
